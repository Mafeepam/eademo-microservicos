package com.espacos_academicos.usuariosservice.repository;

import com.espacos_academicos.usuariosservice.entity.Usuario; // IMPORT DA ENTIDADE NOVO PACOTE
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // A anotação @Repository é opcional se você estende JpaRepository, mas não faz mal tê-la.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Este método você já tinha
}