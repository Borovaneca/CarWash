package com.example.carwash.service;

import com.cloudinary.Cloudinary;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.service.interfaces.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class ProfileImageServiceImpl implements ProfileImageService {

    private final ProfileImageRepository profileImageRepository;
    private final Cloudinary cloudinary;
    private final String DEFAULT_IMAGE = "http://res.cloudinary.com/dy2y8i2de/image/upload/v1701332934/faf63408-1397-4299-8fe3-f4b8c1aa37f0.png";


    @Autowired
    public ProfileImageServiceImpl(ProfileImageRepository profileImageRepository, Cloudinary cloudinary) {
        this.profileImageRepository = profileImageRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public ProfileImage saveProfileImage(MultipartFile multipartFile, User user) {

        try {
            String url = cloudinary
                    .uploader()
                    .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();

            boolean exists = user.getImage() != null;

            ProfileImage profileImage = exists ? user.getImage() : new ProfileImage();
            profileImage.setLocatedOn(url);
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProfileImage getDefaultProfileImage() {

            ProfileImage profileImage = new ProfileImage();
            profileImage.setLocatedOn(DEFAULT_IMAGE);
            return profileImageRepository.save(profileImage);
    }

    @Override
    public ProfileImage save(ProfileImage profileImage) {
        return profileImageRepository.save(profileImage);
    }
}
