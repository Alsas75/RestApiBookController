package javalessons.travelbox.controller;


import javalessons.travelbox.model.Trip;
import javalessons.travelbox.model.TripRequest;
import javalessons.travelbox.service.EmailService;
import javalessons.travelbox.service.FileService;
import javalessons.travelbox.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api")
@Slf4j
public class TripController {
    @Autowired
    private TripService tripService;
    @Autowired
    private FileService fileService;
    @Autowired
    private EmailService emailService;

    public TripController(TripService tripService, FileService fileService) {
        this.tripService = tripService;
        this.fileService = fileService;
        this.emailService = emailService;
    }

    @PostMapping("/send-trip")
    public ResponseEntity<String> postRequest(@RequestBody TripRequest tripRequest) {
        try {
            if (tripRequest.equals(null)) {
                log.error("Request is empty");
                ResponseEntity.badRequest().body("Request is empty");
            }
            //сохраняет его в PostgreSQL;
            Trip saveTrip = tripService.saveTrip(tripRequest);

            //генерирует .txt-файл с маршрутом и сохраняет его в базе данных;
            String filePath = fileService.generateTxtFile(saveTrip);
            saveTrip.setFilePath(filePath);

            //отправляет файл на email как вложение;
            emailService.sendEmailWithAttachment(saveTrip, filePath);

            return ResponseEntity.ok().body(saveTrip.getTitle());


        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return ResponseEntity.badRequest().body("Wrong upload: " + exception.getMessage());
        }

    }

}
