package com.api.files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("파일 업로드")
    public void testUploadFile() throws Exception {

        // 가상의 파일 생성(파일 이름, 파일 내용 타입, 파일 내용)
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                       // 파라미터 이름(컨트롤러에서 받는 이름과 일치)
                "example.jpg",                      // 파일 이름
                MediaType.IMAGE_JPEG_VALUE,         // MIME 타입
                "test file content".getBytes());    // 파일 내용

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(multipart("/api/v1/files/upload")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileId").exists())
                .andExpect(jsonPath("$.fileUrl").exists())
                .andExpect(jsonPath("$.fileId").exists())
                .andExpect(jsonPath("$.uploadedTime").exists());
    }


    @Test
    @DisplayName("파일 다운로드")
    public void testDownloadFile() throws Exception {
        String fileId = "file_123";

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/files/{fileId}", fileId)
                .accept(MediaType.APPLICATION_OCTET_STREAM)) // 바이너리 데이터
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/octet-stream"));
    }
}