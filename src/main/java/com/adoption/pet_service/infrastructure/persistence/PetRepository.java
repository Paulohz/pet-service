package com.adoption.pet_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adoption.pet_service.domain.model.PetStatus;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, UUID> {
    List<PetEntity> findByDhDeletedAtIsNull();
    Optional<PetEntity> findByIdPetAndDhDeletedAtIsNull(UUID id);
    List<PetEntity> findByStPetAndDhDeletedAtIsNull(PetStatus status);
    List<PetEntity> findByNmCityIgnoreCaseAndDhDeletedAtIsNull(String city);
}
