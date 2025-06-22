package de.ait.javalessons.controller;

import de.ait.javalessons.properties.CompanyProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private final CompanyProperties companyProperties;

    public CompanyController(CompanyProperties companyProperties) {
        this.companyProperties = companyProperties;
    }

    @GetMapping("/company")
    public CompanyProperties getCompanyInfo() {
        return companyProperties;
    }

}
