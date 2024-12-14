package com.api.service;


import com.api.config.AwsS3Properties;
import com.api.dto.file.PresingedUrlResult;
import com.api.util.PresignedUrlService;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final AwsS3Properties awsS3Properties;

    private final PresignedUrlService presignedUrlService;

    public FileService(AwsS3Properties awsS3Properties, PresignedUrlService presignedUrlService) {
        this.awsS3Properties = awsS3Properties;
        this.presignedUrlService = presignedUrlService;
    }

    public PresingedUrlResult generatePresignedPutUrl(String fileId, String contentType) {
        if(!contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일의 형식이 아닙니다.");
        }

        String bucketName = awsS3Properties.getS3().getBucket();

        String generatePresignedPutUrl = presignedUrlService.generatePresignedPutUrl(bucketName, fileId, contentType);

        return new PresingedUrlResult(generatePresignedPutUrl, fileId);
    }
}
