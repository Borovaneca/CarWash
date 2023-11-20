package com.example.carwash.service;

import com.cloudinary.Cloudinary;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ProfileImageService;
import com.example.carwash.service.interfaces.UserService;
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
    private final UserService userService;
    private final Cloudinary cloudinary;


    @Autowired
    public ProfileImageServiceImpl(ProfileImageRepository profileImageRepository, UserService userService, Cloudinary cloudinary) {
        this.profileImageRepository = profileImageRepository;
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @Override
    public ProfileImage saveProfileImage(MultipartFile multipartFile, String username) {

        try {
            String url = cloudinary
                    .uploader()
                    .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();

            boolean exists = userService.findByUsername(username) == null;

            ProfileImage profileImage = exists ? userService.findByUsername(username).getImage() : new ProfileImage();
            profileImage.setLocatedOn(url);
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProfileImage getDefaultProfileImage() {
        File image = new File("src/main/resources/static/images/default-profile-image.png");
        try {
            String url = cloudinary
                    .uploader()
                    .upload(image, Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();

            ProfileImage profileImage = new ProfileImage();
            profileImage.setLocatedOn(url);
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProfileImage save(ProfileImage profileImage) {
        return profileImageRepository.save(profileImage);
    }
}
