package javalessons.travelbox.service;


import javalessons.travelbox.model.Trip;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileService {

    @Value("${uploads.dir}")
    private String uploadDir;


    public String generateTxtFile(Trip saveTrip) throws IOException {

        String fileName = "trip-" + saveTrip.getOwnerName().toLowerCase() + "-" + UUID.randomUUID() + ".txt";

            Path filePath = Paths.get(uploadDir, fileName);

            String content = String.format(
                    "Название маршрута: %s\nИмя пользователя: %s\nГорода: %s\nДаты: %s - %s\nНазвание файла: %s",
                    saveTrip.getTitle(),
                    saveTrip.getOwnerName(),
                    saveTrip.getCity(),
                    saveTrip.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    saveTrip.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    fileName
            );
        try {

            Files.write(filePath, content.getBytes());

        } catch (IOException e) {
            throw new RuntimeException("No data!" + e.getMessage());
        }

        return filePath.toString();
    }
}
