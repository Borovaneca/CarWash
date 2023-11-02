package com.example.carwash.service;

import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.repository.ProfileImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProfileImageService {

    private final ProfileImageRepository profileImageRepository;
    public static final String IMAGE_PATH = "C:/Users/borot/IdeaProjects/CarWash/src/main/resources/static/images/";

    @Autowired
    public ProfileImageService(ProfileImageRepository profileImageRepository) {
        this.profileImageRepository = profileImageRepository;
    }

    public ProfileImage saveProfileImage(MultipartFile multipartFile, String username) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileDirectory = IMAGE_PATH + username;
            String filePath = fileDirectory + "/" + fileName;
            Path path = Path.of(fileDirectory);
            Files.deleteIfExists(path);
            Files.createDirectories(path);

            multipartFile.transferTo(new File(filePath));
            ProfileImage profileImage = new ProfileImage();
            profileImage.setLocatedOn(filePath);
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
