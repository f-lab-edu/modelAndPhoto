package com.api.files;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new FileUploadResponse("file_123"
                                                        ,"/uploads/files/example.jpg"
                                                        , LocalDateTime.now()));
    }

    // 파일 다운로드
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

        // 실제 파일 데이터를 ByteArrayResource로
        byte[] fileData = "this is a test file".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fileData, "application/octet-stream");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
