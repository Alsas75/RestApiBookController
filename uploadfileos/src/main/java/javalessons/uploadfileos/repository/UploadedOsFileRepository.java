package javalessons.uploadfileos.repository;

import javalessons.uploadfileos.model.UploadedOsFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UploadedOsFileRepository  extends JpaRepository <UploadedOsFile, Long>{
    Optional<UploadedOsFile> findByOriginalFilename(String originalFilename);

    Optional<UploadedOsFile> findByStoredFilename(String storedFilename);
}
