package com.example.carwash.web.rest;

import com.example.carwash.model.entity.Service;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.ViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.ap.internal.util.Services;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class ServicesRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;
    private String url = "http://localhost:";


    @Test
    void getServices() throws Exception {
        String expected = "[{\"name\":\"Interior Detailing\",\"description\":\"Get the inside of your vehicle detailed with a thorough vacuum, windows cleaned, mats shampooed and a wipe down and polish of all interior surfaces.\",\"price\":\"40.0\",\"urlVideo\":\"pVWPOBmOKuM\"},{\"name\":\"Express Wash\",\"description\":\"For a quick but thorough cleaning, our express wash hits all the key areas - exterior wash and rinse, towel dry and vacuum. Great for the busy driver on the go.\",\"price\":\"15.0\",\"urlVideo\":\"UJ54Kxk5LsA\"},{\"name\":\"The Works Wash\",\"description\":\"Our signature wash includes a presoak, tri-color foam bath, high-pressure rinse, hand wax and interior vacuum and windows cleaned. Your car will look showroom new!\",\"price\":\"20.0\",\"urlVideo\":\"eWUxqVFBq74\"}]";

        MvcResult mvcResult = mockMvc.perform(get(url + port + "/api/services/"))
                .andExpect(status().isOk())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();

        assertEquals(expected, actual);
    }
}