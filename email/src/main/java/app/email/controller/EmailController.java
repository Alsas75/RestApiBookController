package app.email.controller;


import app.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/email")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String to) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("name", "Alex");
            model.put("date", LocalDate.now());

            emailService.sendTemplateEmail(to, "Привет из приложения", model);
            log.info("Письмо отправлено");
            return ResponseEntity.ok("Письмо отправлено");
        }
        catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка " + exception.getMessage());
        }
    }

    @PostMapping("send-with-pdf")
    public ResponseEntity<String> sendWithAttachmant (@RequestParam String to){
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("name", "Alex");
            model.put("date", LocalDate.now());

            File pdf = new File("src/main/resources/document.pdf");

            emailService.sendEmailWithAttachment(to, "Документ PDF", model, pdf);
            log.info("Письмо с PDF отправлено");
            return ResponseEntity.ok("Письмо с PDF отправлено");
        }
        catch (Exception exception){
            log.error(exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка " + exception.getMessage());
        }
    }
}