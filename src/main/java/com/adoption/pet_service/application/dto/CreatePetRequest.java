package com.adoption.pet_service.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePetRequest(
    @NotBlank String name,
    String breed,
    @NotNull LocalDate birthDate,
    @NotNull Double latitude,
    @NotNull Double longitude,
    @NotBlank String city,
    @NotBlank String state
) {}