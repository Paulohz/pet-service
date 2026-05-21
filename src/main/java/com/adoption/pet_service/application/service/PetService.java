package com.adoption.pet_service.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adoption.pet_service.application.dto.CreatePetRequest;
import com.adoption.pet_service.application.dto.PetResponse;
import com.adoption.pet_service.application.dto.UpdateLocationRequest;
import com.adoption.pet_service.domain.exception.PetNotFoundException;
import com.adoption.pet_service.domain.model.PetStatus;
import com.adoption.pet_service.infrastructure.persistence.PetEntity;
import com.adoption.pet_service.infrastructure.persistence.PetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public PetResponse create(CreatePetRequest req) {
        var entity = new PetEntity();
        entity.setName(req.name());
        entity.setBreed(req.breed());
        entity.setBirthDate(req.birthDate());
        entity.setStatus(PetStatus.AVAILABLE);
        entity.setLatitude(req.latitude());
        entity.setLongitude(req.longitude());
        entity.setCity(req.city());
        entity.setState(req.state());
        return PetResponse.from(petRepository.save(entity).toDomain());
    }

    public PetResponse findById(UUID id) {
        return petRepository.findByIdAndDeletedAtIsNull(id)
                .map(e -> PetResponse.from(e.toDomain()))
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    public List<PetResponse> findWithFilters(PetStatus status, String city) {
        return petRepository.findWithFilters(status, city)
                .stream().map(e -> PetResponse.from(e.toDomain())).toList();
    }

    public PetResponse updateStatus(UUID id, PetStatus status) {
        var entity = petRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        entity.setStatus(status);
        return PetResponse.from(petRepository.save(entity).toDomain());
    }

    public PetResponse updateLocation(UUID id, UpdateLocationRequest req) {
        var entity = petRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        entity.setLatitude(req.latitude());
        entity.setLongitude(req.longitude());
        entity.setCity(req.city());
        entity.setState(req.state());
        return PetResponse.from(petRepository.save(entity).toDomain());
    }

    public void delete(UUID id) {
        var entity = petRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        entity.setDeletedAt(Instant.now());
        petRepository.save(entity);
    }
}