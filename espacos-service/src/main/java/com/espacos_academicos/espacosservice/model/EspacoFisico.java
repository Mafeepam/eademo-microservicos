package com.espacos_academicos.espacosservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "espacosfisicos", schema = "espacos_schema") // Schema definido aqui
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspacoFisico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Sigla é obrigatória")
    @Size(max = 20, message = "Sigla deve ter no máximo 20 caracteres")
    @Column(unique = true, nullable = false, length = 20)
    private String sigla;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Tipo de espaço é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private TipoEspaco tipo;

    @NotNull(message = "Capacidade é obrigatória")
    @Positive(message = "Capacidade deve ser um número positivo")
    @Column(nullable = false)
    private Integer capacidade;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusEspaco status = StatusEspaco.ATIVO; // Valor padrão
}