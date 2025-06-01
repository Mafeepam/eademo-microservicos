package com.espacos_academicos.usuariosservice.controller;

import com.espacos_academicos.usuariosservice.dto.PerfilDTO;
import com.espacos_academicos.usuariosservice.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/perfil")
// @CrossOrigin(origins = "*") // Considere gerenciar CORS no API Gateway
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Long id) {
        PerfilDTO perfil = perfilService.buscarPerfilPorId(id);
        return ResponseEntity.ok(perfil);
    }
}