package javalessons.uploadfileos.service;

import javalessons.uploadfileos.model.UploadedOsFile;
import javalessons.uploadfileos.repository.UploadedOsFileRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    private final UploadedOsFileRepository uploadedOsFileRepository;

    private final Path rootLocation;



    public FileService(@Value("${file.upload-dir}")
                       String uploadDir, UploadedOsFileRepository uploadedOsFileRepository) {
        this.uploadedOsFileRepository = uploadedOsFileRepository;
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootLocation);
            log.info("Created the directory where the uploaded files will be stored at: {}", rootLocation.toAbsolutePath().normalize());
        }
        catch (IOException exception){
            log.error("Could not create the directory where the uploaded files will be stored at: {}", rootLocation.toAbsolutePath().normalize());
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", exception);
        }
    }

    public UploadedOsFile storeFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String storedFilename = originalFilename + "_" + UUID.randomUUID();
            Path destination = rootLocation.resolve(storedFilename);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            UploadedOsFile uploadedOsFile = new UploadedOsFile();
            uploadedOsFile.setOriginalFilename(originalFilename);
            uploadedOsFile.setStoredFilename(storedFilename);
            uploadedOsFile.setPath(destination.toString());
            uploadedOsFile.setSize(file.getSize());
            uploadedOsFile.setUploadTime(LocalDateTime.now());
            log.info("File uploaded successfully: {}", uploadedOsFile.toString());
            return uploadedOsFileRepository.save(uploadedOsFile);

        }
        catch (IOException exception){
            log.error("Could not store the file. Error: {}", exception.getMessage());
            throw new RuntimeException("Could not store the file. Error: " + exception.getMessage());
        }
    }

    public List<UploadedOsFile> getAllFiles(){
        log.info("Getting all uploaded files.");
        return uploadedOsFileRepository.findAll();
    }

    public void deleteFile(String storedFilename){
        UploadedOsFile file = uploadedOsFileRepository.findByOriginalFilename(storedFilename)
                .orElseThrow(() -> new RuntimeException("File not found." + storedFilename));
        try {

            Files.deleteIfExists(Paths.get(file.getPath()));
            uploadedOsFileRepository.delete(file);
            log.info("File deleted successfully: {}", file.getOriginalFilename());
        }
        catch (IOException exception){
            log.error("Could not delete the file. Error: {}", exception.getMessage());
            throw new RuntimeException("Could not delete the file. Error: " + exception.getMessage());
        }

    }

}
