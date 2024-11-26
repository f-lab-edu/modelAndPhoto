package com.api.controller;

import com.api.dto.file.FileUploadResponse;
import com.api.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {

        FileUploadResponse fileUploadResponse = fileService.upload(file);

        return ResponseEntity.ok(fileUploadResponse);
    }

    // 파일 다운로드
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

        // 실제 파일 데이터를 ByteArrayResource로
        byte[] fileData = "this is a test file".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fileData, "application/octet-stream");

        fileService.download(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
