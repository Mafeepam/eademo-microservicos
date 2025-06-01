package com.espacos_academicos.usuariosservice.repository; // PACOTE NOVO

import com.espacos_academicos.usuariosservice.entity.Professores; // IMPORT DA ENTIDADE NOVO PACOTE
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessoresRepository extends JpaRepository<Professores, Long> {
    Optional<Professores> findByEmail(String email); // Este método você já tinha
}