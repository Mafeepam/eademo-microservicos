package com.espacos_academicos.reservasservice.dto;

import com.espacos_academicos.reservasservice.model.StatusReserva;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaStatusUpdateRequestDTO {

    @NotNull(message = "Novo status é obrigatório")
    private StatusReserva novoStatus;

    @NotNull(message = "ID do responsável pela alteração é obrigatório")
    private Integer responsavelId;

    @NotBlank(message = "Perfil do responsável (ex: 'admin', 'professor') é obrigatório")
    private String perfilResponsavel;
}