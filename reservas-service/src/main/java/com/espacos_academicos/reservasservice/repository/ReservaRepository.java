package com.espacos_academicos.reservasservice.repository;

import com.espacos_academicos.reservasservice.model.Reserva;
import com.espacos_academicos.reservasservice.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.espacoId = :espacoId " +
            "AND r.data = :data " +
            "AND r.status NOT IN ('CANCELADA_PROFESSOR', 'CANCELADA_ADMIN', 'EXPIRADA', 'NAO_COMPARECEU') " +
            "AND ((r.horaInicio < :horaFim AND r.horaFim > :horaInicio))")
    List<Reserva> findConflictingReservas(
            @Param("espacoId") Long espacoId,
            @Param("data") LocalDate data,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );

    List<Reserva> findByProfessorIdAndStatusIn(Integer professorId, List<StatusReserva> statuses);
    List<Reserva> findByEspacoIdAndDataAndStatusIn(Long espacoId, LocalDate data, List<StatusReserva> statuses);
    List<Reserva> findByDataBetweenAndStatusIn(LocalDate dataInicio, LocalDate dataFim, List<StatusReserva> statuses);
}