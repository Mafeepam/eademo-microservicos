package com.espacos_academicos.espacosservice.controller;

import com.espacos_academicos.espacosservice.dto.EspacoFisicoRequestDTO;
import com.espacos_academicos.espacosservice.dto.EspacoFisicoResponseDTO;
import com.espacos_academicos.espacosservice.dto.StatusUpdateRequestDTO;
import com.espacos_academicos.espacosservice.model.StatusEspaco;
import com.espacos_academicos.espacosservice.model.TipoEspaco;
import com.espacos_academicos.espacosservice.service.EspacoFisicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/espacos") // Prefixo para os endpoints do serviço
public class EspacoFisicoController {

    @Autowired
    private EspacoFisicoService espacoFisicoService;

    @PostMapping
    public ResponseEntity<EspacoFisicoResponseDTO> criarEspaco(@Valid @RequestBody EspacoFisicoRequestDTO requestDTO) {
        EspacoFisicoResponseDTO responseDTO = espacoFisicoService.criarEspaco(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EspacoFisicoResponseDTO>> listarEspacos(
            @RequestParam(required = false) StatusEspaco status,
            @RequestParam(required = false) TipoEspaco tipo) {
        List<EspacoFisicoResponseDTO> espacos = espacoFisicoService.listarTodosEspacos(status, tipo);
        return ResponseEntity.ok(espacos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacoFisicoResponseDTO> buscarEspacoPorId(@PathVariable Long id) {
        EspacoFisicoResponseDTO responseDTO = espacoFisicoService.buscarEspacoPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspacoFisicoResponseDTO> atualizarEspaco(@PathVariable Long id, @Valid @RequestBody EspacoFisicoRequestDTO requestDTO) {
        EspacoFisicoResponseDTO responseDTO = espacoFisicoService.atualizarEspaco(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EspacoFisicoResponseDTO> atualizarStatusEspaco(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequestDTO statusRequestDTO) {
        EspacoFisicoResponseDTO responseDTO = espacoFisicoService.atualizarStatusEspaco(id, statusRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspaco(@PathVariable Long id) {
        espacoFisicoService.deletarEspaco(id);
        return ResponseEntity.noContent().build();
    }
}