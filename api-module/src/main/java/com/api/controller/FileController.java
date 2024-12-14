package com.api.controller;

import com.api.dto.file.PresingedUrlResult;
import com.api.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * put 요청을 보낼 presingedUrl을 반환
     * @param fileId
     * @param contentType
     * @return
     */
    @GetMapping("/generate-upload-url")
    public ResponseEntity<PresingedUrlResult> generatePresignedPutUrl(@RequestParam String fileId,
                                                                      @RequestParam String contentType) {

        PresingedUrlResult presingedUrlResult = fileService.generatePresignedPutUrl(fileId, contentType);

        return ResponseEntity.ok(presingedUrlResult);
    }
}
