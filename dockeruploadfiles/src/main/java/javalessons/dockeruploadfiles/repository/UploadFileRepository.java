
package javalessons.dockeruploadfiles.repository;

import javalessons.dockeruploadfiles.model.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UploadFileRepository extends JpaRepository <UploadFile, Long>{

    Optional<UploadFile> findByOriginalFileName(String originalFileName);

    void deleteByOriginalFileName(String originalFileName);
}
