package com.adoption.pet_service.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adoption.pet_service.application.dto.PetResponse;
import com.adoption.pet_service.application.dto.UpdateLocationRequest;
import com.adoption.pet_service.application.service.PetService;
import com.adoption.pet_service.domain.exception.PetNotFoundException;
import com.adoption.pet_service.domain.model.PetStatus;
import com.adoption.pet_service.infrastructure.persistence.PetEntity;
import com.adoption.pet_service.infrastructure.persistence.PetRepository;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    private PetEntity buildEntity(UUID id, PetStatus status) {
        PetEntity entity = new PetEntity();
        entity.setId(id);
        entity.setName("Rex");
        entity.setBreed("Labrador");
        entity.setBirthDate(LocalDate.of(2020, 1, 1));
        entity.setStatus(status);
        entity.setLatitude(-23.5);
        entity.setLongitude(-46.6);
        entity.setCity("São Paulo");
        entity.setState("SP");
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return entity;
    }

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void findById_shouldReturnPet_whenFound() {
        UUID id = UUID.randomUUID();
        PetEntity entity = buildEntity(id, PetStatus.AVAILABLE);
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(entity));

        PetResponse response = petService.findById(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.name()).isEqualTo("Rex");
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.findById(id));
    }

    @Test
    void findByFilters_shouldReturnPets_whenFound() {
        PetEntity entity1 = buildEntity(UUID.randomUUID(), PetStatus.AVAILABLE);
        PetEntity entity2 = buildEntity(UUID.randomUUID(), PetStatus.AVAILABLE);
        when(petRepository.findWithFilters(PetStatus.AVAILABLE, "São Paulo")).thenReturn(List.of(entity1, entity2));

        var responses = petService.findWithFilters(PetStatus.AVAILABLE, "São Paulo");

        assertThat(responses).hasSize(2);
    }

    @Test
    void findByFilters_shouldReturnEmptyList_whenNotFound() {
        when(petRepository.findWithFilters(PetStatus.AVAILABLE, "São Paulo")).thenReturn(List.of());

        var responses = petService.findWithFilters(PetStatus.AVAILABLE, "São Paulo");

        assertThat(responses).isEmpty();
    }

    @Test
    void updateStatus_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.updateStatus(id, PetStatus.ADOPTED));
    }

    @Test
    void updateStatus_shouldReturnUpdatedPet_whenFound() {
        UUID id = UUID.randomUUID();

        PetEntity entity = buildEntity(id, PetStatus.AVAILABLE);
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(entity));
        when(petRepository.save(entity)).thenReturn(entity);

        PetResponse response = petService.updateStatus(id, PetStatus.ADOPTED);

        assertThat(response.status()).isEqualTo(PetStatus.ADOPTED);
    }

    @Test
    void updateLocation_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,
                () -> petService.updateLocation(id, new UpdateLocationRequest(-23.5, -46.6, "São Paulo", "SP")));
    }

    @Test
    void updateLocation_shouldReturnUpdatedPet_whenFound() {
        UUID id = UUID.randomUUID();
        PetEntity entity = buildEntity(id, PetStatus.AVAILABLE);
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(entity));
        when(petRepository.save(entity)).thenReturn(entity);

        PetResponse response = petService.updateLocation(id,
                new UpdateLocationRequest(-23.5, -46.6, "São Paulo", "SP"));
        assertThat(response.latitude()).isEqualTo(-23.5);
        assertThat(response.longitude()).isEqualTo(-46.6);
        assertThat(response.city()).isEqualTo("São Paulo");
        assertThat(response.state()).isEqualTo("SP");
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.delete(id));
    }

    @Test
    void delete_shouldSetDeletedAt_whenFound() {
        UUID id = UUID.randomUUID();
        PetEntity entity = buildEntity(id, PetStatus.AVAILABLE);

        when(petRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(entity));
        when(petRepository.save(entity)).thenReturn(entity);

        assertThat(entity.getDeletedAt()).isNull();

        petService.delete(id);

        assertThat(entity.getDeletedAt()).isNotNull();
    }
}
