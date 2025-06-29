package app.appmed.controller;

import app.appmed.model.AuthClient;
import app.appmed.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/appointment")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-confirmation")
    public ResponseEntity<String> sendAppointmentConfirmation(@RequestBody AuthClient request) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("name", request.getName());
            model.put("doctor", request.getDoctor());
            model.put("department", request.getDepartment());
            model.put("datetime", request.getDatetime());

            emailService.sendAppointmentConfirmation(request.getEmail(), "Подтверждение записи на прием", model);

            return ResponseEntity.ok("Письмо с подтверждением отправлено");
        } catch (Exception e) {
            log.error("Ошибка отправки подтверждения: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка отправки письма: " + e.getMessage());
        }
    }

    @PostMapping("/send-report")
    public ResponseEntity<String> sendAppointmentReport(@RequestBody AuthClient request) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("name", request.getName());
            model.put("doctor", request.getDoctor());
            model.put("datetime", request.getDatetime());
            model.put("pdfPath", request.getPdfPath());

            File pdfFile = new File(request.getPdfPath());

            emailService.sendAppointmentReport(request.getEmail(), "Результаты приема и рекомендации", model, pdfFile);

            return ResponseEntity.ok("Письмо с отчетом отправлено");
        } catch (Exception e) {
            log.error("Ошибка отправки отчета: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка отправки письма с отчетом: " + e.getMessage());
        }
    }
}