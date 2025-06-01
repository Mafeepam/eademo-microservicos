package com.espacos_academicos.reservasservice.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspacoFisicoDTO {
    private Long id;
    private String sigla;
    private String nome;
    private String descricao;
    private TipoEspaco tipo;
    private Integer capacidade;
    private StatusEspaco status;
}