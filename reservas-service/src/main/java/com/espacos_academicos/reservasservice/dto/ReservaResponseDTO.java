package com.espacos_academicos.reservasservice.dto;

import com.espacos_academicos.reservasservice.model.StatusReserva;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data
public class ReservaResponseDTO {
    private Long id;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer professorId;
    private Long espacoId;
    private String observacao;
    private StatusReserva status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataAtualizacao;
}