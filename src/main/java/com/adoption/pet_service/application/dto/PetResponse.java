package com.adoption.pet_service.application.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import com.adoption.pet_service.domain.model.Pet;
import com.adoption.pet_service.domain.model.PetStatus;

public record PetResponse(
        UUID id,
        String name,
        String breed,
        LocalDate birthDate,
        int age,
        PetStatus status,
        Double latitude,
        Double longitude,
        String city,
        String state,
        Instant createdAt,
        Instant updatedAt) {
    public static PetResponse from(Pet pet) {
        int age = Period.between(pet.getBirthDate(), LocalDate.now()).getYears();
        return new PetResponse(
                pet.getId(), pet.getName(), pet.getBreed(),
                pet.getBirthDate(), age, pet.getStatus(),
                pet.getLatitude(), pet.getLongitude(),
                pet.getCity(), pet.getState(),
                pet.getCreatedAt(), pet.getUpdatedAt());
    }
}