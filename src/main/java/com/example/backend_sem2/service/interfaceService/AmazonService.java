package com.example.backend_sem2.service.interfaceService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AmazonService {
    String createPreSignedPosterUrl (String posterUrl);
    String handleImageInCreateMovieRequest(MultipartFile multipartFile) throws IOException;

    public String handleImageUploading (String rootFolder, MultipartFile multipartFile) throws IOException;
    public String uploadImageInUrlToS3(String rootFolder, String imageUrl);
}
