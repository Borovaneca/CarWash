package com.example.carwash.service;

import com.cloudinary.Cloudinary;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ProfileImageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Service
public class ProfileImageServiceImpl implements ProfileImageService {

    private final ProfileImageRepository profileImageRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;


    @Autowired
    public ProfileImageServiceImpl(ProfileImageRepository profileImageRepository, UserRepository userRepository, Cloudinary cloudinary) {
        this.profileImageRepository = profileImageRepository;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public ProfileImage saveProfileImage(MultipartFile multipartFile, String username) {

        try {
            String url = cloudinary
                    .uploader()
                    .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();

            boolean exists = userRepository.findByUsername(username).isPresent();

            ProfileImage profileImage = exists ? userRepository.findByUsername(username).get().getImage() : new ProfileImage();
            profileImage.setLocatedOn(url);
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
