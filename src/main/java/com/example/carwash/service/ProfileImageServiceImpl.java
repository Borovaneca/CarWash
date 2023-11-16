package com.example.carwash.service;

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

@Service
public class ProfileImageServiceImpl implements ProfileImageService {

    private final ProfileImageRepository profileImageRepository;
    private final UserRepository userRepository;
    public static final String IMAGE_PATH = "C:/Users/borot/IdeaProjects/CarWash/src/main/resources/static/images/";
    public static final String PATH_FOR_DB = "http://localhost:8080/images/";

    @Autowired
    public ProfileImageServiceImpl(ProfileImageRepository profileImageRepository, UserRepository userRepository) {
        this.profileImageRepository = profileImageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileImage saveProfileImage(MultipartFile multipartFile, String username) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileDirectory = IMAGE_PATH + username;
            String filePath = fileDirectory + "/" + fileName;
            Path path = Path.of(fileDirectory);
            boolean exists = Files.exists(path);
            if (exists) {
                File directory = new File(fileDirectory);
                FileUtils.deleteDirectory(directory);
            }
            Files.createDirectories(path);
            multipartFile.transferTo(new File(filePath));
            ProfileImage profileImage = exists ? userRepository.findByUsername(username).get().getImage() : new ProfileImage();
            profileImage.setLocatedOn(PATH_FOR_DB + username + "/" + fileName);
            return profileImageRepository.save(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
