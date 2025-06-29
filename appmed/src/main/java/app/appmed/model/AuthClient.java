package app.appmed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthClient {
    private String email;
    private String name;
    private String doctor;
    private String department;
    private String datetime;
    private String pdfPath;

}