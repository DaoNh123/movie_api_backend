package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.service.interfaceService.AmazonService;
import com.example.backend_sem2.utils.S3AmazonUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.CollectionUtils;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Date;

@Service
public class AmazonServiceImpl implements AmazonService {
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.aws-url}")
    private String awsUrl;
    @Autowired
    private S3Client s3Client;
    @Autowired
    private S3Presigner s3Presigner;

    @SneakyThrows
    public String createPreSignedPosterUrl (String posterUrl){
        if(posterUrl == null || posterUrl.startsWith("http://image.tmdb.org/t/p")) return posterUrl;

        String[] exactInfoObjectUrl = posterUrl.split("/" + bucketName + "/");

        URI uri = new URI(posterUrl);
        String objectKey = uri.getPath();

        // Remove leading slash if present
        if (objectKey.startsWith("/")) {
            objectKey = objectKey.substring(1);
        }
//        System.out.println("***: " + objectKey);
//        https://s3.ap-southeast-1.amazonaws.com/
//        https://s3.ap-southeast-1.amazonaws.com
        return S3AmazonUtils.createPreSignedGetUrl(bucketName, objectKey, s3Presigner);
    }

        /*  return Object Url in S3 Amazon  */
    public String handleImageInCreateMovieRequest(MultipartFile multipartFile) throws IOException {
        String rootFolder = "images";
        System.out.println("bucketName = " + bucketName);

        return String.join("/", awsUrl, S3AmazonUtils.putS3Object(s3Client, bucketName, rootFolder, convertMultiPartToFile(multipartFile)));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public String uploadImageInUrlToS3(String rootFolder, String imageUrl){
        return String.join("/", awsUrl,S3AmazonUtils.uploadImageInUrlToS3(s3Client, bucketName, rootFolder, imageUrl));
    }
}
