package de.ait.javalessons.properties;


import de.ait.javalessons.model.CompanyInfoResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "company")
public class CompanyProperties {

    private String name;

    private String ceo;

    private int employeeCount;

    public String getCompanyInfo() {
        return new CompanyInfoResponse(name, ceo, employeeCount).toString();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }


}
