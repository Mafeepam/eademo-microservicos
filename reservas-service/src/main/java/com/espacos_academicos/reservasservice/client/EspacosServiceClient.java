package com.espacos_academicos.reservasservice.client;

import com.espacos_academicos.reservasservice.client.dto.EspacoFisicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "espacos-service", path = "/api/v1/espacos")
public interface EspacosServiceClient {

    @GetMapping("/{id}")
    ResponseEntity<EspacoFisicoDTO> getEspacoById(@PathVariable("id") Long id);
}