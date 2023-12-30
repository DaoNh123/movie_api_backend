package com.example.backend_sem2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class AmazonConfig {
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @Value("${amazonProperties.aws-url}")
    private String awsUrl;
    @Value("${amazonProperties.region}")
    private String region;

    @Bean
    public S3Client s3Client() throws URISyntaxException {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.of(region))  // Replace with your desired region
                .endpointOverride(new URI(awsUrl))
                .credentialsProvider(() -> awsCredentials)
                .build();
    }

    @Bean
    public S3Presigner presigner(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(awsUrl))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
        return presigner;
    }
}
