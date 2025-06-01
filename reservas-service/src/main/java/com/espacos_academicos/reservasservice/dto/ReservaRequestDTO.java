package com.espacos_academicos.reservasservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaRequestDTO {

    @NotNull(message = "Data da reserva é obrigatória")
    @FutureOrPresent(message = "Data da reserva deve ser no presente ou futuro")
    private LocalDate data;

    @NotNull(message = "Hora de início é obrigatória")
    private LocalTime horaInicio;

    @NotNull(message = "Hora de fim é obrigatória")
    private LocalTime horaFim;

    @NotNull(message = "ID do Professor é obrigatório")
    private Integer professorId;

    @NotNull(message = "ID do Espaço é obrigatório")
    private Long espacoId;

    private String observacao;
}