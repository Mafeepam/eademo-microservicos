package com.espacos_academicos.espacosservice.dto;

import com.espacos_academicos.espacosservice.model.StatusEspaco;
import com.espacos_academicos.espacosservice.model.TipoEspaco;
import lombok.Data;

@Data
public class EspacoFisicoResponseDTO {
    private Long id;
    private String sigla;
    private String nome;
    private String descricao;
    private TipoEspaco tipo;
    private Integer capacidade;
    private StatusEspaco status;
}