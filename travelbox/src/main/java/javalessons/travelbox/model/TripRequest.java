package javalessons.travelbox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TripRequest {

    private String email;
    private String name;
    private String title;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private String fileName;

    }
