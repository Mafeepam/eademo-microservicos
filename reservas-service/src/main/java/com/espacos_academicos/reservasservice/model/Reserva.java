package com.espacos_academicos.reservasservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reservas", schema = "reservas_schema")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "professor_id", nullable = false)
    private Integer professorId;

    @Column(name = "espaco_id", nullable = false)
    private Long espacoId;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.SOLICITADA;

    @CreationTimestamp
    @Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

    // Construtores
    public Reserva() {}

    public Reserva(LocalDate data, LocalTime horaInicio, LocalTime horaFim, 
                   Integer professorId, Long espacoId, String observacao) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.professorId = professorId;
        this.espacoId = espacoId;
        this.observacao = observacao;
        this.status = StatusReserva.SOLICITADA;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }

    public Long getEspacoId() { return espacoId; }
    public void setEspacoId(Long espacoId) { this.espacoId = espacoId; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public StatusReserva getStatus() { return status; }
    public void setStatus(StatusReserva status) { this.status = status; }

    public OffsetDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(OffsetDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public OffsetDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(OffsetDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}