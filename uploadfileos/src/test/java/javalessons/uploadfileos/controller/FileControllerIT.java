package javalessons.uploadfileos.controller;

import jakarta.transaction.Transactional;
import javalessons.uploadfileos.model.UploadedOsFile;
import javalessons.uploadfileos.repository.UploadedOsFileRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.MediaType;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
        "spring.datasource.username=postgres",
        "spring.datasource.password=qwerty007",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false"
})
@Transactional
class FileControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UploadedOsFileRepository fileRepository;


    private static Path uploadDir;


    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) throws IOException {
        uploadDir = Files.createTempDirectory("upload-dir-test");
        registry.add("file.upload-dir", () -> uploadDir.toAbsolutePath().toString());
    }


    @BeforeEach
    void cleanupDatabase(){
        fileRepository.deleteAll();
    }

    @Test
    void testUploadFileAndVerifyDatabaseContent() throws Exception {
        String content = "This is test file content.";

        MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "example.txt",
                MediaType.TEXT_PLAIN.toString(),
                content.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/api/files/upload")
                        .file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalFilename").value("example.txt"))
                .andReturn();

        List<UploadedOsFile> uploadedOsFiles = fileRepository.findAll();
        assertEquals(1, uploadedOsFiles.size());

        UploadedOsFile uploadedOsFile = uploadedOsFiles.get(0);
        Path savePath = Paths.get(uploadedOsFile.getPath());

        assertTrue(Files.exists(savePath));

        String storedContent = Files.readString(savePath);
        assertEquals(content, storedContent);

    }

    @Test
    void testDeleteFile() throws Exception {

        String fileName = "delete_me.txt";

        String content = "This is test file content.";

        Path filePath = uploadDir.resolve(fileName);
        Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));


        UploadedOsFile uploadedOsFile = new UploadedOsFile();
        uploadedOsFile.setOriginalFilename("delete_me.txt");
        uploadedOsFile.setStoredFilename(fileName);
        uploadedOsFile.setPath(filePath.toString());
        uploadedOsFile.setSize(content.getBytes(StandardCharsets.UTF_8).length);
        uploadedOsFile.setUploadTime(LocalDateTime.now());
        fileRepository.save(uploadedOsFile);

        mockMvc.perform(delete("/api/files/{filename}", fileName))
                .andExpect(status().isNoContent());

        assertFalse(Files.exists(filePath));

        assertTrue(fileRepository.findAll().isEmpty());
    }

}
