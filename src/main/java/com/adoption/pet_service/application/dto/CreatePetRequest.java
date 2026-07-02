package com.adoption.pet_service.application.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.adoption.pet_service.domain.model.PetSpecies;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePetRequest(
        @NotBlank String name,
        @NotNull PetSpecies species,
        @NotNull LocalDate birthDate,
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotBlank String city,
        @NotBlank String state,
        @NotNull UUID customerId) {
}