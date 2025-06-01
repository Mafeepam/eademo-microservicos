package com.espacos_academicos.usuariosservice.service;

// ATENÇÃO: As importações de DTOs e outras entidades/serviços precisarão ser verificadas/ajustadas
// quando migrarmos os DTOs e se houver chamadas para outros serviços (que agora serão inter-serviços)
import com.espacos_academicos.usuariosservice.dto.PerfilDTO; // Supondo que PerfilDTO será migrado para este pacote
import com.espacos_academicos.usuariosservice.entity.Usuario;
import com.espacos_academicos.usuariosservice.entity.Professores;
import com.espacos_academicos.usuariosservice.repository.UsuarioRepository;
import com.espacos_academicos.usuariosservice.repository.ProfessoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;

    public PerfilDTO buscarPerfilPorId(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuário não encontrado!"); // Considere exceções mais específicas
        }
        Usuario usuario = usuarioOpt.get();

        Optional<Professores> professorOpt = professoresRepository.findByEmail(usuario.getEmail());

        PerfilDTO perfil = new PerfilDTO();
        perfil.setId(usuario.getId());
        perfil.setEmail(usuario.getEmail());
        // Atenção: Expor senha no DTO de perfil geralmente não é uma boa prática.
        // Avalie se a senha é realmente necessária aqui.
        perfil.setSenha(usuario.getSenha());
        perfil.setFuncao(usuario.getFuncao());

        if (professorOpt.isPresent()) {
            Professores professor = professorOpt.get();
            perfil.setNome(professor.getNome());
            perfil.setCurso(professor.getCurso());
            // perfil.setTelefone(professor.getTelefone()); // Se existir
        }
        return perfil;
    }
}