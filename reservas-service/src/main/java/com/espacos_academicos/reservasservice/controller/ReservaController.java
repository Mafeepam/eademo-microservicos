package com.espacos_academicos.reservasservice.controller;

import com.espacos_academicos.reservasservice.dto.ReservaRequestDTO;
import com.espacos_academicos.reservasservice.dto.ReservaResponseDTO;
import com.espacos_academicos.reservasservice.dto.ReservaStatusUpdateRequestDTO;
import com.espacos_academicos.reservasservice.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@Valid @RequestBody ReservaRequestDTO reservaRequestDTO) {
        ReservaResponseDTO novaReserva = reservaService.criarReserva(reservaRequestDTO);
        return new ResponseEntity<>(novaReserva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReservaPorId(@PathVariable Long id) {
        ReservaResponseDTO reserva = reservaService.buscarReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarTodasReservas() {
        List<ReservaResponseDTO> reservas = reservaService.listarTodasReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/por-professor/{professorId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasPorProfessor(@PathVariable Integer professorId) {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasPorProfessor(professorId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/por-espaco/{espacoId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasPorEspacoEData(
            @PathVariable Long espacoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasPorEspacoEData(espacoId, data);
        return ResponseEntity.ok(reservas);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservaResponseDTO> atualizarStatusReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaStatusUpdateRequestDTO statusUpdateRequest) {
        ReservaResponseDTO reservaAtualizada = reservaService.atualizarStatusReserva(
                id,
                statusUpdateRequest.getNovoStatus(),
                statusUpdateRequest.getResponsavelId(),
                statusUpdateRequest.getPerfilResponsavel()
        );
        return ResponseEntity.ok(reservaAtualizada);
    }
}