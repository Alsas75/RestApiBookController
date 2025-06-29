package javalessons.travelbox.controller;


import javalessons.travelbox.model.Trip;
import javalessons.travelbox.model.TripRequest;
import javalessons.travelbox.service.FileDatabaseUploadService;
import javalessons.travelbox.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/send-trip")
    public ResponseEntity<String> postRequest(@RequestBody TripRequest  tripRequest) {
        try {
            if (tripRequest.equals(null)) {
                log.error("Request is empty");
                ResponseEntity.badRequest().body("Request is empty");
            }

            Trip saveTrip = tripService.saveTrip(tripRequest);
            return ResponseEntity.ok().body(saveTrip.getTitle());

        }
        catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return ResponseEntity.badRequest().body("Wrong upload: " + exception.getMessage());
        }

    }

}
