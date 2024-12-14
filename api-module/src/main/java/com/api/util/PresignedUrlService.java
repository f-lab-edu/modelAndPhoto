package com.api.util;

import com.api.config.AwsS3Properties;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.time.Duration;

@Service
public class PresignedUrlService {

    private final AwsS3Properties awsS3Properties;

    public PresignedUrlService(AwsS3Properties awsS3Properties) {
        this.awsS3Properties = awsS3Properties;
    }

    public String generatePresignedPutUrl(String bucketName, String fileId, String contentType) {
        try (S3Presigner s3Presigner = createS3Presigner()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileId)
                    .contentType(contentType)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10)) // URL 유효 시간 설정 (10분)
                    .putObjectRequest(putObjectRequest)
                    .build();

            return s3Presigner.presignPutObject(presignRequest).url().toString();
        }
    }

    private S3Presigner createS3Presigner() {
        return S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsS3Properties.getCredentials().getAccessKey(), awsS3Properties.getCredentials().getSecretKey())))
                .region(Region.of(awsS3Properties.getRegion().getRegionStatic()))
                .endpointOverride(URI.create(awsS3Properties.getS3().getEndpoint())) // NCP Object Storage의 엔드포인트
                .build();
    }
}
