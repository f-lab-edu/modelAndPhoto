package com.api.service;

import com.api.dto.file.PresingedUrlResult;
import com.api.util.PresignedUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private PresignedUrlService presignedUrlService;

    @Test
    @DisplayName("presignedPutUrl이 정상적으로 생성된다.")
    void test_generatePresignedPutUrl() {

        // todo
        // given
        doReturn("www.modelandphoto.com")
                .when(presignedUrlService).generatePresignedPutUrl(eq(null), any(String.class), any(String.class));

        // when
        String fileId = "FIL_001";
        PresingedUrlResult presingedUrlResult = fileService.generatePresignedPutUrl(fileId, MediaType.IMAGE_JPEG_VALUE);

        // then
        assertThat(presingedUrlResult.getUrl()).isNotNull();
        assertThat(presingedUrlResult.getFileId()).isNotNull();
        assertThat(presingedUrlResult.getFileId()).isEqualTo(fileId);

        // verify
        verify(presignedUrlService, times(1)).generatePresignedPutUrl(eq(null), any(String.class), any(String.class));
    }

    @Test
    @DisplayName("presignedPutUrl이 호출시 이미지파일이 아니면 Exception을 던진다.")
    void test_generatePresignedPutUrlException() {
        // given
        String fileId = "FIL_001";
        String applicationJsonValue = MediaType.APPLICATION_JSON_VALUE;

        // then
        assertThatIllegalArgumentException().isThrownBy(() -> fileService.generatePresignedPutUrl(fileId, applicationJsonValue));
    }

}