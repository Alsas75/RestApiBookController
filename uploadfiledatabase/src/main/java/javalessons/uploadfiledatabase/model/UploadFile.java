package javalessons.uploadfiledatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private String originalFileName;

    private String storedFilename;

    private String contentType;

    private long size;

    private LocalDateTime uploadDate;

    private byte[] data;
}
