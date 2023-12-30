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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3AmazonUtils {

    @SneakyThrows
    public static String putS3Object(S3Client s3, String bucketName, String objectKey, File file) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            System.out.println("***" + file.getName() + " in putS3Object");
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
    public static String uploadImageInUrlToS3(S3Client s3, String bucketName, String prefixToObjectKey, String imageUrl) {
        try {
            // Download the image from the URL
            URL url = new URL(imageUrl);

            // Extract file extension from the URL
            String fileExtension = "." + getFileExtension(url.getPath());

            // Create a temporary file with a unique name and the extracted extension
            Path tempImagePath = Files.createTempFile(System.currentTimeMillis() + "-tmp", fileExtension);

            // Copy the contents from the URL to the temporary file
            Files.copy(url.openStream(), tempImagePath, StandardCopyOption.REPLACE_EXISTING);

            // Upload the downloaded image to S3

            String objectKey = prefixToObjectKey + "/" + System.currentTimeMillis() + "-" + getImageNameFromTheMovieDBLink(imageUrl);
            System.out.println("***" + tempImagePath.toString() + " in uploadImageInUrlToS3");
            return putS3Object(s3, bucketName, objectKey, new File(tempImagePath.toString()));

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

//    public static void main(String[] args) {
//        System.out.println(getImageNameFromTheMovieDBLink("http://image.tmdb.org/t/p/original/c54HpQmuwXjHq2C9wmoACjxoom3.jpg"));
//    }

    // New
    /* Create a presigned URL to use in a subsequent PUT request */
//    public String createPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
//        try (S3Presigner presigner = S3Presigner.create()) {
//
//            PutObjectRequest objectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(keyName)
//                    .metadata(metadata)
//                    .build();
//
//            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
//                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
//                    .putObjectRequest(objectRequest)
//                    .build();
//
//
//            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
//            String myURL = presignedRequest.url().toString();
//
//            return presignedRequest.url().toExternalForm();
//        }
//    }

    /* Use the AWS SDK for Java V2 SdkHttpClient class to do the upload. */
//    public void useSdkHttpClientToPut(String presignedUrlString, File fileToPut, Map<String, String> metadata) {
//
//        try {
//            URL presignedUrl = new URL(presignedUrlString);
//
//            SdkHttpRequest.Builder requestBuilder = SdkHttpRequest.builder()
//                    .method(SdkHttpMethod.PUT)
//                    .uri(presignedUrl.toURI());
//            // Add headers
//            metadata.forEach((k, v) -> requestBuilder.putHeader("x-amz-meta-" + k, v));
//            // Finish building the request.
//            SdkHttpRequest request = requestBuilder.build();
//
//            HttpExecuteRequest executeRequest = HttpExecuteRequest.builder()
//                    .request(request)
//                    .contentStreamProvider(new FileContentStreamProvider(fileToPut.toPath()))
//                    .build();
//
//            try (SdkHttpClient sdkHttpClient = ApacheHttpClient.create()) {
//                HttpExecuteResponse response = sdkHttpClient.prepareRequest(executeRequest).call();
//
//            }
//        } catch (URISyntaxException | IOException e) {
//
//        }
//    }
}
