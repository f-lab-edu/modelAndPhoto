package com.api.service;


import com.api.dto.file.PresingedUrlResult;
import com.api.util.PresignedUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final PresignedUrlService presignedUrlService;

    public FileService(PresignedUrlService presignedUrlService) {
        this.presignedUrlService = presignedUrlService;
    }

    public PresingedUrlResult generatePresignedPutUrl(String fileId, String contentType) {
        if(!contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일의 형식이 아닙니다.");
        }

        String bucketName = this.bucketName;

        String generatePresignedPutUrl = presignedUrlService.generatePresignedPutUrl(bucketName, fileId, contentType);

        return new PresingedUrlResult(generatePresignedPutUrl, fileId);
    }

    public void uploadFile(String presignedUrl, MultipartFile multipartFile) throws IOException {
        File file = convertMultipartFileToFile(multipartFile);
        multipartFile.getContentType();
        HttpURLConnection connection = (HttpURLConnection) new URL(presignedUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", multipartFile.getContentType());

        try (var outputStream = connection.getOutputStream()) {
            outputStream.write(java.nio.file.Files.readAllBytes(file.toPath()));
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("파일업로드 성공");
        } else {
            System.out.println("파일업로드 실패 : " + responseCode);
        }
    }

    private static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }



    public void downloadFile(String key, String downloadPath) {
        // todo
    }
}
