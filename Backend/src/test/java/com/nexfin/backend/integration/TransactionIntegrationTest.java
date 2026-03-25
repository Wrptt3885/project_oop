package com.nexfin.backend.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldTransferBalance() throws Exception {
        RegisterResult sender = register("Alice Sender", "alice.integration@nexfin.com");
        RegisterResult receiver = register("Bob Receiver", "bob.integration@nexfin.com");

        mockMvc.perform(post("/api/wallets/{userId}/top-up", sender.userId())
                        .header("Authorization", bearer(sender.token()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 500.00,
                                  "reference": "INITIAL_TOPUP"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(500.00));

        mockMvc.perform(post("/api/transactions/transfer")
                        .header("Authorization", bearer(sender.token()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fromUserId": "%s",
                                  "toUserId": "%s",
                                  "amount": 125.00,
                                  "reference": "PAYMENT_001"
                                }
                                """.formatted(sender.userId(), receiver.userId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(125.00))
                .andExpect(jsonPath("$.type").value("TRANSFER"))
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.reference").value("PAYMENT_001"));

        mockMvc.perform(get("/api/wallets/{userId}", sender.userId())
                        .header("Authorization", bearer(sender.token())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(375.00));

        mockMvc.perform(get("/api/wallets/{userId}", receiver.userId())
                        .header("Authorization", bearer(receiver.token())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(125.00));

        mockMvc.perform(get("/api/transactions/user/{userId}", sender.userId())
                        .header("Authorization", bearer(sender.token())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    private RegisterResult register(String fullName, String email) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "%s",
                                  "email": "%s",
                                  "password": "StrongPass1"
                                }
                                """.formatted(fullName, email)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return new RegisterResult(json.get("token").asText(), json.get("userId").asText());
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private record RegisterResult(String token, String userId) {
    }
}
