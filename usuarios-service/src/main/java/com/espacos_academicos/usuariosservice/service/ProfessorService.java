package com.espacos_academicos.usuariosservice.service;

import com.espacos_academicos.usuariosservice.entity.Professores;
import com.espacos_academicos.usuariosservice.repository.ProfessoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessoresRepository repository;

    public List<Professores> listarTodos() {
        return repository.findAll();
    }

    public Professores buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    public Professores cadastrar(Professores professor) {
        // Adicionar validações se necessário (ex: email já existe)
        return repository.save(professor);
    }

    public Professores atualizarProfessor(Long id, Professores dadosAtualizados) {
        Professores professorExistente = buscarPorId(id); // Reusa o método buscarPorId para checar existência

        professorExistente.setNome(dadosAtualizados.getNome());
        professorExistente.setEmail(dadosAtualizados.getEmail());
        professorExistente.setCurso(dadosAtualizados.getCurso());

        return repository.save(professorExistente);
    }

    public void excluirProfessor(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado para exclusão");
        }
        repository.deleteById(id);
    }
}