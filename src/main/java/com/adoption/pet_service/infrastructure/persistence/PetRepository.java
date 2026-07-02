package com.adoption.pet_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adoption.pet_service.domain.model.PetSpecies;
import com.adoption.pet_service.domain.model.PetStatus;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, UUID> {
    List<PetEntity> findByDeletedAtIsNull();

    Optional<PetEntity> findByIdAndDeletedAtIsNull(UUID id);

    List<PetEntity> findByCustomerIdAndDeletedAtIsNull(UUID customerId);

    @Query("""
                SELECT p FROM PetEntity p
                WHERE p.deletedAt IS NULL
                AND (:status IS NULL OR p.status = :status)
                AND (:species IS NULL OR p.species = :species)
                AND (:city IS NULL OR LOWER(p.city) = LOWER(CAST(:city AS string)))
            """)
    List<PetEntity> findWithFilters(
            @Param("status") PetStatus status,
            @Param("species") PetSpecies species,
            @Param("city") String city);
}