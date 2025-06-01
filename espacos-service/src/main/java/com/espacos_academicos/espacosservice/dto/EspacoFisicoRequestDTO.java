package com.espacos_academicos.espacosservice.dto;

import com.espacos_academicos.espacosservice.model.TipoEspaco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EspacoFisicoRequestDTO {

    @NotBlank(message = "Sigla é obrigatória")
    @Size(max = 20, message = "Sigla deve ter no máximo 20 caracteres")
    private String sigla;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    private String descricao;

    @NotNull(message = "Tipo de espaço é obrigatório")
    private TipoEspaco tipo;

    @NotNull(message = "Capacidade é obrigatória")
    @Positive(message = "Capacidade deve ser um número positivo")
    private Integer capacidade;

    // O status geralmente é gerenciado por um endpoint específico ou tem um padrão na criação
}