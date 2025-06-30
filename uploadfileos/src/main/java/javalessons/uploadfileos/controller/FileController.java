package javalessons.uploadfileos.controller;

import javalessons.uploadfileos.model.UploadedOsFile;
import javalessons.uploadfileos.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<UploadedOsFile> uploadFile(@RequestParam("file") MultipartFile file){
        UploadedOsFile uploadedOsFile = fileService.storeFile(file);
        log.info("uploadedOsFile = " + uploadedOsFile);
        return ResponseEntity.ok(uploadedOsFile);
    }

    @GetMapping
    public ResponseEntity<List<UploadedOsFile>> getAllFiles(){
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename){
        fileService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }
}