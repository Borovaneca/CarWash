package com.example.carwash.model.dtos;

import com.example.carwash.validation.uniqueServiceName.UniqueServiceName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import static com.example.carwash.constants.Messages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAddDTO {

    @NotBlank(message = CANT_BE_NULL_OR_EMPTY_MESSAGE)
    @UniqueServiceName
    private String name;

    @NotBlank(message = CANT_BE_NULL_OR_EMPTY_MESSAGE)
    private String description;

    @NotBlank(message = CANT_BE_NULL_OR_EMPTY_MESSAGE)
    @URL(message = INVALID_URL)
    private String urlVideo;

    @NotNull(message = CANT_BE_NULL_OR_EMPTY_MESSAGE)
    @Positive(message = MUST_BE_POSITIVE)
    private Double price;
}
