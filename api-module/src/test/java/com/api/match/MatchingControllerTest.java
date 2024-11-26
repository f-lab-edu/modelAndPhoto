package com.api.match;

import com.api.dto.match.MatchingRequestDto;
import com.api.dto.match.MatchRespondRequestDto;
import com.api.enums.MatchingStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MatchingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("매칭 가능한 사용자 목록 조회")
    public void testRetrieveMatchingUsers() throws Exception {
        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/matchings")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matches").exists())
                .andExpect(jsonPath("$.matches[0].userId").exists())
                .andExpect(jsonPath("$.matches[0].role").exists())
                .andExpect(jsonPath("$.matches[0].location").exists())
                .andExpect(jsonPath("$.matches[0].style").exists())
                .andExpect(jsonPath("$.matches[0].introduce").exists());
    }

    @Test
    @DisplayName("매칭 요청 전송")
    public void testSendMatchRequest() throws Exception {

        MatchingRequestDto matchRequest = new MatchingRequestDto("123", "456");

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(post("/api/v1/matchings/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(matchRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchRequestId").exists())
                .andExpect(jsonPath("$.status").value("pending"));
    }


    @Test
    @DisplayName("매칭 요청 목록 조회 (GET)")
    public void testRetrieveMatchRequests() throws Exception {
        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/matchings/requests")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requests[0].requestId").exists())
                .andExpect(jsonPath("$.requests[0].senderName").exists())
                .andExpect(jsonPath("$.requests[0].senderRole").exists())
                .andExpect(jsonPath("$.requests[0].status").exists());
    }


    @Test
    @DisplayName("매칭 요청 수락 또는 거절")
    public void testRespondToMatchRequest() throws Exception {

        MatchRespondRequestDto request = new MatchRespondRequestDto("req_123", MatchingStatus.ACCEPTED);

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(post("/api/v1/matchings/respond")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(request.getResponse().name()));
    }
}