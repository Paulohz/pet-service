package com.adoption.pet_service.domain.model;

import java.time.LocalDate;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class Pet {
    private UUID id;
    private String name;
    private PetSpecies species;
    private LocalDate birthDate;
    private PetStatus status;
    private Double latitude;
    private Double longitude;
    private String city;
    private String state;
    private UUID customerId;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public boolean isActive() {
        return this.deletedAt == null;
    }
}
