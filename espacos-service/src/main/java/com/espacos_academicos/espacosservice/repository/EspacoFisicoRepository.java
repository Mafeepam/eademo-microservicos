package com.espacos_academicos.espacosservice.repository;

import com.espacos_academicos.espacosservice.model.EspacoFisico;
import com.espacos_academicos.espacosservice.model.StatusEspaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacoFisicoRepository extends JpaRepository<EspacoFisico, Long> {
    Optional<EspacoFisico> findBySigla(String sigla);
    List<EspacoFisico> findByStatus(StatusEspaco status);
    List<EspacoFisico> findByTipo(String tipo);
}