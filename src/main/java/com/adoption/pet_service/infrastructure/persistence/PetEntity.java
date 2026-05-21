package com.adoption.pet_service.infrastructure.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.adoption.pet_service.domain.model.Pet;
import com.adoption.pet_service.domain.model.PetStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "pets")
@Getter @Setter @NoArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_pet")
    private UUID id;

    @Column(name= "nm_pet", nullable = false)
    private String name;

    @Column(name= "nm_breed")
    private String breed;

    @Column(name = "dt_birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_pet", nullable = false)
    private PetStatus status;

    @Column(name = "vl_latitude")
    private Double latitude;

    @Column(name = "vl_longitude")
    private Double longitude;

    @Column(name = "nm_city")
    private String city;

    @Column(name = "nm_state")
    private String state;

    @Column(name = "dh_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "dh_updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "dh_deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = PetStatus.AVAILABLE;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static PetEntity fromDomain(Pet pet) {
        PetEntity e = new PetEntity();
        e.setId(pet.getId());
        e.setId(pet.getId());
        e.setName(pet.getName());
        e.setBreed(pet.getBreed());
        e.setBirthDate(pet.getBirthDate());
        e.setStatus(pet.getStatus());
        e.setLatitude(pet.getLatitude());
        e.setLongitude(pet.getLongitude());
        e.setCity(pet.getCity());
        e.setState(pet.getState());
        e.setCreatedAt(pet.getCreatedAt());
        e.setUpdatedAt(pet.getUpdatedAt());
        e.setDeletedAt(pet.getDeletedAt());
        return e;
    }

    public Pet toDomain() {
        Pet p = new Pet();
        p.setId(this.id);
        p.setName(this.name);
        p.setBreed(this.breed);
        p.setBirthDate(this.birthDate);
        p.setStatus(this.status);
        p.setLatitude(this.latitude);
        p.setLongitude(this.longitude);
        p.setCity(this.city);
        p.setState(this.state);
        p.setCreatedAt(this.createdAt);
        p.setUpdatedAt(this.updatedAt);
        p.setDeletedAt(this.deletedAt);
        return p;
    }

    
}
