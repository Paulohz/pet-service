package com.adoption.pet_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLocationRequest(
    @NotNull Double latitude,
    @NotNull Double longitude,
    @NotBlank String city,
    @NotBlank String state
) {}