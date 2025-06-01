package com.espacos_academicos.reservasservice.service;

import com.espacos_academicos.reservasservice.dto.ReservaRequestDTO;
import com.espacos_academicos.reservasservice.dto.ReservaResponseDTO;
import com.espacos_academicos.reservasservice.model.StatusReserva;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    ReservaResponseDTO criarReserva(ReservaRequestDTO reservaRequestDTO);
    ReservaResponseDTO buscarReservaPorId(Long id);
    List<ReservaResponseDTO> listarReservasPorProfessor(Integer professorId);
    List<ReservaResponseDTO> listarReservasPorEspacoEData(Long espacoId, LocalDate data);
    List<ReservaResponseDTO> listarTodasReservas();
    ReservaResponseDTO atualizarStatusReserva(Long id, StatusReserva novoStatus, Integer responsavelId, String perfilResponsavel);
}