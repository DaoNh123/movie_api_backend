package com.example.backend_sem2.service.interfaceService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AmazonService {
    String createPreSignedPosterUrl (String posterUrl);
    String handleImageInCreateMovieRequest(MultipartFile multipartFile) throws IOException;
    public String uploadImageInUrlToS3(String prefixToObjectKey, String imageUrl);
}
