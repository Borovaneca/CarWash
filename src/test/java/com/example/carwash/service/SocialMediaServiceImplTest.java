package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.SocialMediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SocialMediaServiceImplTest {

    @Mock
    private SocialMediaRepository socialMediaRepository;

    @InjectMocks
    private SocialMediaServiceImpl socialMediaService;

    @Mock
    private CustomMapper customMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addSocialMediaToUser_ValidInput_ShouldReturnSavedSocialMedia() {
        User user = new User(); // Create a RegisterDTO object
        SocialMediaAddDTO socialMediaAddDTO = new SocialMediaAddDTO();
        socialMediaAddDTO.setLink("https://example.com");
        socialMediaAddDTO.setType("Facebook");

        SocialMedia expectedSocialMedia = new SocialMedia();
        expectedSocialMedia.setLink(socialMediaAddDTO.getLink());
        expectedSocialMedia.setType(socialMediaAddDTO.getType());
        expectedSocialMedia.setUser(user);

        when(socialMediaRepository.save(any())).thenReturn(expectedSocialMedia);

        SocialMedia result = socialMediaService.addSocialMediaToUser(socialMediaAddDTO, user);

        assertEquals(expectedSocialMedia, result);
        verify(socialMediaRepository, times(1)).save(any());
    }

    @Test
    void delete_ValidSocialMedia_ShouldInvokeRepositoryDelete() {
        SocialMedia socialMedia = new SocialMedia();

        socialMediaService.delete(socialMedia);

        verify(socialMediaRepository, times(1)).delete(socialMedia);
    }

    @Test
    void getByNameAndUser_ExistingSocialMedia_ShouldReturnSocialMedia() {
        User user = new User(); // Create a RegisterDTO object
        String socialMediaName = "Facebook";

        SocialMedia expectedSocialMedia = new SocialMedia();
        expectedSocialMedia.setLink("https://example.com");
        expectedSocialMedia.setType(socialMediaName);
        expectedSocialMedia.setUser(user);

        when(socialMediaRepository.findByTypeAndUser(eq(socialMediaName), any()))
                .thenReturn(Optional.of(expectedSocialMedia));

        SocialMedia result = socialMediaService.getByNameAndUser(socialMediaName, user);

        assertEquals(expectedSocialMedia, result);
    }

    @Test
    void getByNameAndUser_NonExistingSocialMedia_ShouldReturnNull() {
        User user = new User(); // Create a RegisterDTO object
        String socialMediaName = "NonExistingSocialMedia";

        when(socialMediaRepository.findByTypeAndUser(eq(socialMediaName), any()))
                .thenReturn(Optional.empty());

        SocialMedia result = socialMediaService.getByNameAndUser(socialMediaName, user);

        assertNull(result);
    }

}