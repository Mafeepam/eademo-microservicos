package com.espacos_academicos.reservasservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reservas", schema = "reservas_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data da reserva é obrigatória")
    @FutureOrPresent(message = "Data da reserva deve ser no presente ou futuro")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "Hora de início é obrigatória")
    @Column(nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "Hora de fim é obrigatória")
    @Column(nullable = false)
    private LocalTime horaFim;

    @NotNull(message = "ID do Professor é obrigatório")
    @Column(nullable = false)
    private Integer professorId;

    @NotNull(message = "ID do Espaço é obrigatório")
    @Column(nullable = false)
    private Long espacoId;

    @Lob
    private String observacao;

    @NotNull(message = "Status da reserva é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusReserva status = StatusReserva.SOLICITADA;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;

    @PrePersist
    @PreUpdate
    private void verificarHorario() {
        if (horaInicio != null && horaFim != null && !horaFim.isAfter(horaInicio)) {
            throw new IllegalArgumentException("A hora de fim deve ser após a hora de início.");
        }
    }
}