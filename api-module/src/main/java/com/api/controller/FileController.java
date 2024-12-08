package com.api.controller;

import com.api.dto.file.PresingedUrlResult;
import com.api.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // presignedPutUrl
    @GetMapping("/generate-upload-url")
    public ResponseEntity<PresingedUrlResult> generatePresignedPutUrl(@RequestParam String fileId,
                                                                      @RequestParam String contentType) {

        PresingedUrlResult presingedUrlResult = fileService.generatePresignedPutUrl(fileId, contentType);

        return ResponseEntity.ok(presingedUrlResult);
    }

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file,
                                             @RequestParam String presignedUrl) throws IOException {

        fileService.uploadFile(presignedUrl, file);

        return ResponseEntity.ok("ok");
    }

    // 파일 다운로드
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId,
                                                 @RequestParam String contentType) throws IOException {

        // todo

//        fileService.download(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(null);
    }
}
