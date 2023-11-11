package com.example.carwash.service;

import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.SocialMediaRepository;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaService {

    private final SocialMediaRepository socialMediaRepository;

    public SocialMediaService(SocialMediaRepository socialMediaRepository) {
        this.socialMediaRepository = socialMediaRepository;
    }


    public SocialMedia addSocialMediaToUser(SocialMediaAddDTO socialMediaAddDTO, User user) {
       return socialMediaRepository.save(toSocialMedia(socialMediaAddDTO, user));
    }


    private SocialMedia toSocialMedia(SocialMediaAddDTO socialMediaAddDTO, User user) {
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setLink(socialMediaAddDTO.getLink());
        socialMedia.setType(socialMediaAddDTO.getType());
        socialMedia.setUser(user);
        return socialMedia;
    }


    public void delete(SocialMedia media) {
        socialMediaRepository.delete(media);
    }

    public SocialMedia getByNameAndUser(String socialName, User user) {
       return socialMediaRepository.findByTypeAndUser(socialName, user).orElse(null);
    }
}
