package com.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsS3Properties {

    private CredentialsProperties credentials = new CredentialsProperties();
    private RegionProperties region = new RegionProperties();
    private S3Properties s3 = new S3Properties();

    public static class CredentialsProperties {
        private String accessKey;
        private String secretKey;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

    public static class RegionProperties {

        public String getRegionStatic() {
            return regionStatic;
        }

        public void setRegionStatic(String regionStatic) {
            this.regionStatic = regionStatic;  // region-static
        }

        private String regionStatic;
    }

    public static class S3Properties {
        private String bucket;
        private String endpoint;

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }
    }

    public CredentialsProperties getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsProperties credentials) {
        this.credentials = credentials;
    }

    public RegionProperties getRegion() {
        return region;
    }

    public void setRegion(RegionProperties region) {
        this.region = region;
    }

    public S3Properties getS3() {
        return s3;
    }

    public void setS3(S3Properties s3) {
        this.s3 = s3;
    }
}
