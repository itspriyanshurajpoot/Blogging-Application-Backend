package com.blog.services.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICloudinaryService {
    String uploadImage(MultipartFile file) throws IOException;
    public String uploadProfile(MultipartFile file) throws IOException;
}
