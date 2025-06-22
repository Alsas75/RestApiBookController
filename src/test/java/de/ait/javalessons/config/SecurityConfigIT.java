package de.ait.javalessons.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testWennAdminAccesOk() throws Exception {
    mockMvc.perform(get("/api/private"))
            .andExpect(status().isOk())
            .andExpect(content().string("Это защищенная страница, доступна только для входа"));
    }

    @Test
    @WithMockUser(username = "fakeuser", roles = {})
    void testWennFakeUserAccesForbindden() throws Exception {
        mockMvc.perform(get("/api/private"))
                .andExpect(status().isOk())
                .andExpect(status().is3xxRedirection());
    }

}
