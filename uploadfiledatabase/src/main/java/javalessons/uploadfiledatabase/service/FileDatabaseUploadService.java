package javalessons.uploadfiledatabase.service;

import jakarta.transaction.Transactional;
import javalessons.uploadfiledatabase.model.UploadFile;
import javalessons.uploadfiledatabase.repository.UploadFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class FileDatabaseUploadService {

    private UploadFileRepository uploadFileRepository;

    public FileDatabaseUploadService(UploadFileRepository uploadFileRepository) {
        this.uploadFileRepository = uploadFileRepository;
    }

    public String saveFile(String originalFileName, String contentType, long size, String storedFileName,
    LocalDateTime uploadTime, byte[] data) {
        UploadFile uploadFileToSave = new UploadFile();
        uploadFileToSave.setOriginalFileName(originalFileName);
        uploadFileToSave.setContentType(contentType);
        uploadFileToSave.setSize(size);
        uploadFileToSave.setStoredFilename(storedFileName);
        uploadFileToSave.setUploadDate(uploadTime);
        uploadFileToSave.setData(data);
        log.info("File saved: {}", uploadFileToSave.getOriginalFileName());
        UploadFile savedFile = uploadFileRepository.save(uploadFileToSave);
        return savedFile.getOriginalFileName();

    }

    @Transactional
    public void deleteFile(String originalFileName) {
        UploadFile uploadFileToDelete = uploadFileRepository.findByOriginalFileName(originalFileName)
                .orElseThrow(() -> new IllegalArgumentException("File " + originalFileName + " not found"));
        uploadFileRepository.deleteByOriginalFileName(originalFileName);
        log.info("File deleted: {}", uploadFileToDelete.getOriginalFileName());
    }

}
