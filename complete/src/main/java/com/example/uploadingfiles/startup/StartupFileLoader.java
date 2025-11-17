package com.example.uploadingfiles.startup;

import com.example.uploadingfiles.model.SolarData;
import com.example.uploadingfiles.db.SolarDataRepository;
import com.example.uploadingfiles.parse.CsvFileParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StartupFileLoader implements CommandLineRunner {

    @Value("${storage.scan-dir}")
    private String scanDir;

    private final SolarDataRepository solarDataRepository;

    public StartupFileLoader(SolarDataRepository solarDataRepository) {
        this.solarDataRepository = solarDataRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Path directory = Paths.get(scanDir);

        // ggf. Verzeichnis anlegen, falls nicht existent
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
            // nichts zu importieren beim allerersten Start
            return;
        }

        try (Stream<Path> paths = Files.list(directory)) {
            paths
                    .filter(Files::isRegularFile) // nur „normale“ Dateien
                    .forEach(this::handleFileSafe);
        }
    }

    private void handleFileSafe(Path file) {
        try {
            handleFile(file);
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten von " + file.toAbsolutePath());
            e.printStackTrace();
        }
    }

    private void handleFile(Path file) {
        System.out.println("Gefundene Datei beim Start: " + file.toAbsolutePath());

        CsvFileParser parser = new CsvFileParser();
        parser.parse(file);

        List<SolarData> solarDataList = parser.getSolarDataList();

        if (solarDataList.isEmpty()) {
            System.out.println("Keine verwertbaren Daten in Datei: " + file.getFileName());
            return;
        }

// In DB speichern
        List<SolarData> newOnes = solarDataList.stream()
                .filter(s -> !solarDataRepository.existsById(s.getDate()))
                .collect(Collectors.toList());

        solarDataRepository.saveAll(newOnes);

        System.out.println("Import abgeschlossen: " + solarDataList.size()
                + " Datensätze aus " + file.getFileName());

        // optional: Datei nach erfolgreichem Import verschieben/markieren
        // Files.move(file, file.resolveSibling(file.getFileName() + ".done"));
    }
}
