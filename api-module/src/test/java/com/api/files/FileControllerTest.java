package com.api.files;

import com.api.dto.file.PresingedUrlResult;
import com.api.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @BeforeEach
    void setUp() {
        // PresingedUrl 생성 메서드 Mock설정
        when(fileService.generatePresignedPutUrl("FIL_001", "image/jpeg"))
                .thenReturn(new PresingedUrlResult("http://example.com", "image/jpeg"));
    }

    @Test
    @DisplayName("PresingedPutUrl 요청시 정상적으로 생성된다.")
    void test_generate_presingedPutUrl() throws Exception {
        // given
        mockMvc.perform(get("/api/v1/files/generate-upload-url")
                .param("fileId", "FIL_001").param("contentType", "image/jpeg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists())
                .andExpect(jsonPath("$.fileId").exists())
                .andDo(print());
    }
}