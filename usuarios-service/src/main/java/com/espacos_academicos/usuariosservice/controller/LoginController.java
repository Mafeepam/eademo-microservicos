package com.espacos_academicos.usuariosservice.controller;

import com.espacos_academicos.usuariosservice.entity.Usuario; // IMPORT NOVO
import com.espacos_academicos.usuariosservice.repository.UsuarioRepository; // IMPORT NOVO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // Mantendo o caminho base
@CrossOrigin(origins = "*") // Se você ainda precisar de CORS neste nível, ou pode ser gerenciado no Gateway
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Injetando o repositório do usuarios-service

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginRequest) {
        return usuarioRepository.findByEmail(loginRequest.getEmail())
                .filter(usuario -> usuario.getSenha().equals(loginRequest.getSenha()))
                // ATENÇÃO: Comparação de senhas em texto plano! Em produção, use hashing de senhas.
                // Para o seu projeto e demonstração, isso mantém a lógica original.
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    // Este endpoint estava no seu LoginController original.
    // Considere se ele pertence mais logicamente a um UsuarioController.
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}