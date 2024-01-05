package com.example.backend_sem2.utils;

import com.example.backend_sem2.exception.CustomErrorException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3AmazonUtils {

    @SneakyThrows
    public static String putS3Object(S3Client s3, String bucketName, String rootFolder, File file) {
        try {
            Map<String, String> metadata = new HashMap<>();
//            metadata.put("x-amz-meta-myVal", "test");
            System.out.println("***" + file.getName() + " in putS3Object");
            String fileNameWithRoot = file.getName();

            String[] templeArray = fileNameWithRoot.split("/");
            String fileName = templeArray[templeArray.length - 1];

            LocalDate today = LocalDate.now();
            String objectKey = String.join("/",
                    rootFolder,
                    String.valueOf(today.getYear()),
                    String.format("%02d", today.getMonthValue()),
                    String.format("%02d", today.getDayOfMonth()),
                    fileName
            );

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();
            s3.putObject(putOb, RequestBody.fromFile(file));
            System.out.println("Successfully placed " + objectKey + " into bucket " + bucketName);

            Files.delete(Path.of(file.getAbsolutePath()));
            return objectKey;
        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Fail to save image!");
//            System.exit(1);
        }
    }

    public static String createPreSignedGetUrl(String bucketName, String keyName, S3Presigner presigner) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
//        System.out.println("PreSigned URL: " + presignedRequest.url());
//        System.out.println("HTTP method: " + presignedRequest.httpRequest().method());

        return presignedRequest.url().toExternalForm();
    }

    /*  prefixToObjectKey: route to where the file saved    ___ Using with "theMovieDB"*/
    public static String uploadImageInUrlToS3(S3Client s3, String bucketName, String rootFolder, String imageUrl) {
        try {
            // Download the image from the URL
            URL url = new URL(imageUrl);

            // Extract file extension from the URL
            String fileExtension = "." + getFileExtension(url.getPath());

            String imageName = getImageNameFromTheMovieDBLink(imageUrl);

            // Create a temporary file with a unique name and the extracted extension
//            Path tempImagePath = Files.createTempFile(System.currentTimeMillis() + "-tmp", fileExtension);
            Path tempImagePath = Files.createTempFile(imageName.replace(fileExtension, ""), fileExtension);

            // Copy the contents from the URL to the temporary file
            Files.copy(url.openStream(), tempImagePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("***" + tempImagePath.toString() + " in uploadImageInUrlToS3");
            return putS3Object(s3, bucketName, rootFolder, new File(tempImagePath.toString()));

            // Delete the temporary file
//            Files.delete(tempImagePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Some unexpected happened in \"uploadImageInUrlToS3\"");
        }
    }

    private static String getFileExtension(String path) {
        // Extract file extension using regex
        Pattern pattern = Pattern.compile("\\.(\\w+)$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            // If no extension found, default to ".jpg"
            return ".jpg";
        }
    }

    private static String getImageNameFromTheMovieDBLink(String path) {
        // Extract file extension using regex
        String[] tmp = path.split("/");

        return tmp[tmp.length - 1];
    }
}
