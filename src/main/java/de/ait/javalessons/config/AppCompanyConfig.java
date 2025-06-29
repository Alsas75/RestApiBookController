package de.ait.javalessons.config;

import de.ait.javalessons.properties.CompanyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(CompanyProperties.class)
public class AppCompanyConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
