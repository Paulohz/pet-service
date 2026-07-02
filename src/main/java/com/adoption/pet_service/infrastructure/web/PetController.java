package com.adoption.pet_service.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adoption.pet_service.application.dto.CreatePetRequest;
import com.adoption.pet_service.application.dto.PetResponse;
import com.adoption.pet_service.application.dto.UpdateLocationRequest;
import com.adoption.pet_service.application.service.PetService;
import com.adoption.pet_service.domain.model.PetSpecies;
import com.adoption.pet_service.domain.model.PetStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar pet", description = "Cadastra um novo pet disponível para adoção")
    @ApiResponse(responseCode = "201", description = "Pet cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public PetResponse create(@Valid @RequestBody CreatePetRequest req) {
        return petService.create(req);
    }

    @GetMapping
    @Operation(summary = "Listar pets", description = "Lista todos os pets ativos, com filtro opcional por status ou cidade")
    public List<PetResponse> findAll(
            @RequestParam(name = "status", required = false) PetStatus status,
            @RequestParam(name = "species", required = false) PetSpecies species,
            @RequestParam(name = "city", required = false) String city) {
        return petService.findWithFilters(status, species, city);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    @ApiResponse(responseCode = "200", description = "Pet encontrado")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    public PetResponse findById(
            @Parameter(description = "ID do pet", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable(name = "id") UUID id) {
        return petService.findById(id);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status", description = "Atualiza o status do pet para AVAILABLE ou ADOPTED")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    public PetResponse updateStatus(
            @Parameter(description = "ID do pet", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable(name = "id") UUID id,
            @RequestParam(name = "status") PetStatus status) {
        return petService.updateStatus(id, status);
    }

    @PutMapping("/{id}/location")
    @Operation(summary = "Atualizar localização", description = "Atualiza a localização atual do pet")
    @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    public PetResponse updateLocation(
            @Parameter(description = "ID do pet", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable(name = "id") UUID id,
            @Valid @RequestBody UpdateLocationRequest req) {
        return petService.updateLocation(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover pet", description = "Remove o pet via soft delete")
    @ApiResponse(responseCode = "204", description = "Pet removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    public void delete(
            @Parameter(description = "ID do pet", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable(name = "id") UUID id) {
        petService.delete(id);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Listar pets de um customer")
    @ApiResponse(responseCode = "200", description = "Pets encontrados")
    @ApiResponse(responseCode = "404", description = "Customer não encontrado")
    public List<PetResponse> findByCustomer(
            @Parameter(description = "ID do customer", required = true, example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable(name = "customerId") UUID customerId) {
        return petService.findByCustomer(customerId);
    }
}
