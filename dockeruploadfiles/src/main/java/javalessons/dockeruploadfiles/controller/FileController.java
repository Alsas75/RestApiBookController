package javalessons.dockeruploadfiles.controller;


import javalessons.dockeruploadfiles.service.FileDatabaseUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController {

    private final List<String> allowedTypes = List.of("image/png", "image/jpeg", "application/pdf");

    private FileDatabaseUploadService fileDatabaseUploadService;

    @Autowired
    public FileController(FileDatabaseUploadService fileDatabaseUploadService) {
            this.fileDatabaseUploadService = fileDatabaseUploadService;
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("File is empty");
                ResponseEntity.badRequest().body("File is empty");
            }
            if (!allowedTypes.contains(file.getContentType())) {
                log.error("File type " + file.getContentType() + " is not allowed");
                ResponseEntity.badRequest().body("File type is not allowed");
            }

            String originalFileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();
            String storedFileName = originalFileName + "_" + UUID.randomUUID().toString();
            LocalDateTime uploadTime = LocalDateTime.now();
            byte[] data = file.getBytes();

            String result  = fileDatabaseUploadService.saveFile(originalFileName, contentType, size, storedFileName, uploadTime, data);
            log.info("File uploaded: " + result);
            return ResponseEntity.ok("File uploaded: " + result);


        }
        catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return ResponseEntity.badRequest().body("Wrong upload: " + exception.getMessage());
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) {
        if(filename.isEmpty()){
            log.error("Filename is empty");
            return ResponseEntity.badRequest().body("Filename is empty");
        }
        else {
            fileDatabaseUploadService.deleteFile(filename);
            log.info("File deleted: " + filename);
            return ResponseEntity.ok("File deleted: " + filename);
        }
    }

}
