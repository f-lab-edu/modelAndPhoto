package com.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@TestConfiguration
@EnableConfigurationProperties(AwsS3Properties.class)
class AwsS3PropertiesTest {

    @Bean
    public AwsS3Properties awsS3Properties() {
        return new AwsS3Properties();
    }

    @Test
    @DisplayName("AwsS3Properties 에 설정값이 잘 바인딩 되는지 확인한다.")
    void testAwsS3PropertiesBinding() {
        AwsS3Properties awsS3Properties = new AwsS3Properties();

        awsS3Properties.getCredentials().setAccessKey("test-access-key");
        awsS3Properties.getCredentials().setSecretKey("test-secret-key");
        awsS3Properties.getRegion().setRegionStatic("us-east-1");
        awsS3Properties.getS3().setBucket("test-bucket");
        awsS3Properties.getS3().setEndpoint("https://test-endpoint.com");

        assertThat(awsS3Properties.getCredentials().getAccessKey()).isEqualTo("test-access-key");
        assertThat(awsS3Properties.getCredentials().getSecretKey()).isEqualTo("test-secret-key");
        assertThat(awsS3Properties.getRegion().getRegionStatic()).isEqualTo("us-east-1");
        assertThat(awsS3Properties.getS3().getBucket()).isEqualTo("test-bucket");
        assertThat(awsS3Properties.getS3().getEndpoint()).isEqualTo("https://test-endpoint.com");
    }
}