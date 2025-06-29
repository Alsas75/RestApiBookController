package de.ait.javalessons.service;

import de.ait.javalessons.properties.CompanyProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiCompanyService {
    // Объект RestTemplate — основной инструмент для HTTP-запросов
    private final RestTemplate restTemplate;

    // Класс, содержащий свойства внешнего API (URL, таймаут и пр.)
    private final CompanyProperties companyProperties;


    public ExternalApiCompanyService(RestTemplate restTemplate, CompanyProperties companyProperties) {
        this.restTemplate = restTemplate;
        this.companyProperties = companyProperties;
    }

    public String callCompanyInfo() {
        return companyProperties.getCompanyInfo();
    }

}
