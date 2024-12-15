package com.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AwsS3PropertiesTest {

    @Autowired
    public AwsS3Properties awsS3Properties;

    @Test
    @DisplayName("AwsS3Properties 에 설정값이 잘 바인딩 되었는지 확인한다.")
    void testAwsS3PropertiesBinding() {

        assertThat(awsS3Properties.getCredentials().getAccessKey()).isNotNull();
        assertThat(awsS3Properties.getCredentials().getSecretKey()).isNotNull();
        assertThat(awsS3Properties.getRegion().getRegionStatic()).isNotNull();
        assertThat(awsS3Properties.getS3().getBucket()).isNotNull();
        assertThat(awsS3Properties.getS3().getEndpoint()).isNotNull();
    }
}