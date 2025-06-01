package com.espacos_academicos.espacosservice.service;

import com.espacos_academicos.espacosservice.dto.EspacoFisicoRequestDTO;
import com.espacos_academicos.espacosservice.dto.EspacoFisicoResponseDTO;
import com.espacos_academicos.espacosservice.dto.StatusUpdateRequestDTO;
import com.espacos_academicos.espacosservice.model.StatusEspaco;
import com.espacos_academicos.espacosservice.model.TipoEspaco;


import java.util.List;

public interface EspacoFisicoService {
    EspacoFisicoResponseDTO criarEspaco(EspacoFisicoRequestDTO espacoRequestDTO);
    List<EspacoFisicoResponseDTO> listarTodosEspacos(StatusEspaco status, TipoEspaco tipo);
    EspacoFisicoResponseDTO buscarEspacoPorId(Long id);
    EspacoFisicoResponseDTO atualizarEspaco(Long id, EspacoFisicoRequestDTO espacoRequestDTO);
    EspacoFisicoResponseDTO atualizarStatusEspaco(Long id, StatusUpdateRequestDTO statusUpdateRequestDTO);
    void deletarEspaco(Long id); // Muda status para INATIVO
}