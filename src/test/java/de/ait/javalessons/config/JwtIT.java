package de.ait.javalessons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.javalessons.model.AuthRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLoginWuthCredialsReturnJwt() throws Exception{
        AuthRequest request = new AuthRequest();
        request.setUsername("admin");
        request.setPassword("admin");

        String response = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isNull();
        assertThat(response).isNotBlank();
        assertThat(response).startsWith("ey");
    }
    @Test
    void testLoginWithWrongCredentialsReturnUnauthorized() throws Exception{
        AuthRequest request = new AuthRequest();
        request.setUsername("admin");
        request.setPassword("admin");
        mockMvc.perform(post("/auth/login")
    }


}
