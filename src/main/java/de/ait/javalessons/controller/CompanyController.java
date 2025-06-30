package de.ait.javalessons.controller;

import de.ait.javalessons.properties.CompanyProperties;
import de.ait.javalessons.service.ExternalApiCompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final ExternalApiCompanyService externalApiCompanyService;

    public CompanyController(ExternalApiCompanyService externalApiCompanyService) {
        this.externalApiCompanyService = externalApiCompanyService;
    }

    @GetMapping
    public String getCompanyInfo() {
        return externalApiCompanyService.callCompanyInfo();
    }
}
