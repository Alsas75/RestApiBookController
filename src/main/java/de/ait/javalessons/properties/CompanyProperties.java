package de.ait.javalessons.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "company")
@Setter
@Getter
public class CompanyProperties {

    private String name;

    private String ceo;

    private int employeeCount;


}
