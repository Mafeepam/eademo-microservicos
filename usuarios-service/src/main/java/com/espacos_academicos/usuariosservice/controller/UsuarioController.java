package com.espacos_academicos.usuariosservice.controller;

import com.espacos_academicos.usuariosservice.entity.Usuario;
import com.espacos_academicos.usuariosservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
// @CrossOrigin(origins = "*") // Considere gerenciar CORS no API Gateway
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}