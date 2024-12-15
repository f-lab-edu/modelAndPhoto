package com.api.files;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.api.config.AwsS3Properties;
import com.api.dto.file.PresingedUrlResult;
import com.api.service.FileService;
import com.api.util.IdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("aws-integration")
@SpringBootTest
public class FileControllerIntegrationTest {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FileService fileService;

    @Autowired
    private AwsS3Properties awsS3Properties;

    private static final String TEST_IMAGE_PATH = "src/test/resources/test-image.jpg";
    private static final String CONTENT_TYPE = "image/jpeg";

    @Tag("aws-integration")
    @Test
    @DisplayName("presignedURL을 생성하고 생성된 URL로 실제 파일 업로드를 한다.")
    void testGenerateAndUploadFile() throws Exception {
        // given
        String fileId = IdGenerator.getGenerateFileId();
        File testImage = new File(TEST_IMAGE_PATH);

        // when presinged URL 생성
        PresingedUrlResult presingedUrlResult = fileService.generatePresignedPutUrl(fileId, CONTENT_TYPE);

        // then
        assertThat(presingedUrlResult.getUrl()).isNotNull()
                .contains(awsS3Properties.getS3().getBucket())
                .contains(fileId);

        // 실제 파일 업로드
        uploadImageUsingPresingedUrl(presingedUrlResult, CONTENT_TYPE, testImage);

        // 업로드된 이미지 파일 검증
        verifyUploadedImage(fileId, CONTENT_TYPE, testImage);
    }

    @Tag("aws-integration")
    @Test
    @DisplayName("대용량 이미지 업로드 테스트")
    void testUploadLargeImage() throws Exception {
        // given
        String fileId = IdGenerator.getGenerateFileId();
        File largeTestImage = convertToHighQualityFile(createLargeImageWithHighResolution());
        System.out.println("largeTestImage.size() = " + largeTestImage.length() / 1024 / 1024 + "MB");

        // when
        PresingedUrlResult presingedUrlResult = fileService.generatePresignedPutUrl(fileId, CONTENT_TYPE);

        // then
        assertThat(presingedUrlResult.getUrl()).isNotNull();

        // 업로드 시간 측정
        long startTime = System.currentTimeMillis();
        uploadImageUsingPresingedUrl(presingedUrlResult, CONTENT_TYPE, largeTestImage);
        long endTime = System.currentTimeMillis();

        // 업로드 시간이 적절한지 검증 (30초 이내)
        assertThat(endTime - startTime).isLessThan(30000);
    }

    //  고품질 설정을 사용한 변환
    public static File convertToHighQualityFile(BufferedImage bufferedImage) throws IOException {
        File outputFile = File.createTempFile("high-quality-", ".jpg");

        // JPEGImageWriter 얻기
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

        // 이미지 쓰기 파라미터 설정
        ImageWriteParam params = writer.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(1.0f); // 최대 품질

        // 이미지 출력 스트림 생성
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(bufferedImage, null, null), params);
        } finally {
            writer.dispose();
        }

        return outputFile;
    }

    private File createLargeTestImage() throws IOException {
        // 테스트용 대용량 이미지 생성
        BufferedImage image = new BufferedImage(3000, 2000, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 3000, 2000);
        g2d.dispose();

        File tempFile = File.createTempFile("large-test-image", ".jpg");
        ImageIO.write(image, "jpg", tempFile);
        return tempFile;
    }

    // 매우 높은 해상도의 이미지 생성 (예: 8K = 7680x4320)
    private BufferedImage createLargeImageWithHighResolution() {
        int width = 7680;  // 8K 해상도
        int height = 4320;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 그라데이션으로 이미지를 채워 파일 크기를 늘림
        GradientPaint gradient = new GradientPaint(
                0, 0, Color.RED,
                width, height, Color.BLUE
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // 랜덤한 패턴을 추가하여 압축률을 낮춤
        Random random = new Random();
        for (int i = 0; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                image.setRGB(i, j, random.nextInt(16777216));
            }
        }

        g2d.dispose();
        return image;
    }

    private void verifyUploadedImage(String fileId, String contentType, File testImage) throws IOException {
        // S3에서 업로드된 이미지 다운로드
        S3Object s3Object = amazonS3.getObject(awsS3Properties.getS3().getBucket(), fileId);

        // 이미지 메타데이터 검증
        ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
        assertThat(objectMetadata.getContentType()).isEqualTo(contentType);

        // 파일 크기 검증
        assertThat(objectMetadata.getContentLength()).isEqualTo(testImage.length());

        // 이미지 내용 검증
        byte[] uploadedImageBytes = IOUtils.toByteArray(s3Object.getObjectContent());
        byte[] originalImageBytes = Files.readAllBytes(testImage.toPath());

        assertThat(uploadedImageBytes).isEqualTo(originalImageBytes);
    }

    private void uploadImageUsingPresingedUrl(PresingedUrlResult presingedUrlResult, String contentType, File testImage) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(presingedUrlResult.getUrl()).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", contentType);

        // 파일 업로드
        try(OutputStream out = connection.getOutputStream();
            FileInputStream fis = new FileInputStream(testImage)) {
            byte[] bytes = new byte[8192];
            int length;
            while ((length = fis.read(bytes)) > 0) {
                out.write(bytes, 0, length);
            }
        }

        int responseCode = connection.getResponseCode();
        assertThat(responseCode).isEqualTo(200);
    }
}
