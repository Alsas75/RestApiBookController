package de.ait.javalessons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoResponse {
    private String name;
    private String ceo;
    private int employeeCount;

}
