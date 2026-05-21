package com.adoption.pet_service.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class Pet {
    private UUID id;
    private String name;
    private String breed;
    private LocalDate birthDate;
    private PetStatus status;
    private Double latitude;
    private Double longitude;
    private String city;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isActive() {
        return this.deletedAt == null;
    }
}
