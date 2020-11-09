package com.example.uploadingfiles;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import com.example.uploadingfiles.db.SolarDataRepository;
import com.example.uploadingfiles.model.BatVoltData;
import com.example.uploadingfiles.model.SolarData;
import com.example.uploadingfiles.parse.CsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	SolarDataRepository solarDataRepository;

	@GetMapping("/readata")
	public String listData(Model model){
		model.addAttribute("data", solarDataRepository.findAll());
		return "data";
	}

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/rest/{date}")
	@ResponseBody
	public Optional<SolarData> read(@PathVariable("date") int date) {
		Optional<SolarData> foundDate = solarDataRepository.findById(date);
		return foundDate;
	}

	@GetMapping("/rest/all")
	@ResponseBody
	public List<SolarData> readAll() {
		List<SolarData> foundDate = solarDataRepository.findAll();
		return foundDate;
	}

	//TODO: Make this return json objects, not ArayList!
	@GetMapping("/rest/minbatvol")
	@ResponseBody
	public List<SolarData> readVal() {
		List<SolarData> minBatVolList = solarDataRepository.getMinBatVolList();
		return minBatVolList;

	}

	//TODO: Make this return json objects, not ArayList!
	@GetMapping("/rest/minbatvol/{from}/{to}")
	@ResponseBody
	public List<SolarData> readVal(@PathVariable("from") int from, @PathVariable("to") int to) {
		return solarDataRepository.getMinBatVolList(from, to);
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		Path filePath = storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		CsvFileParser csvParser = new CsvFileParser();
		csvParser.parse(filePath);

		List solarDataList = csvParser.getSolarDataList();
		solarDataRepository.saveAll(solarDataList);
		solarDataRepository.flush();
		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
