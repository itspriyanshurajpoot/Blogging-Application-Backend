package com.blog.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResponse = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "blog_images", "resource_type", "image"));
        return (String) uploadResponse.get("secure_url");
    }

    @Override
    public String uploadProfile(MultipartFile file) throws IOException {
        Map uploadResponse = cloudinary.uploader().upload(file.getBytes(), Map.of("folder", "profile_images", "resource_type", "image"));
        return (String) uploadResponse.get("secure_url");
    }
}
