package com.espacos_academicos.espacosservice.dto;

import com.espacos_academicos.espacosservice.model.StatusEspaco;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequestDTO {
    @NotNull(message = "Novo status é obrigatório")
    private StatusEspaco novoStatus;
}