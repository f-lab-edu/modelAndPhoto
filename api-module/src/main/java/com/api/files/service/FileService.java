package com.api.files.service;

import com.api.files.dto.FileUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class FileService {


    public FileUploadResponse upload(MultipartFile file) {
        // todo ???

        return new FileUploadResponse("file_123"
                ,"/uploads/files/example.jpg"
                , LocalDateTime.now());
    }

    public void download(String fileId) {

        // todo ???

    }
}
