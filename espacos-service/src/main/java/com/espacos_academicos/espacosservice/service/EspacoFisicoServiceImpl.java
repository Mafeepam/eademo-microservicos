package com.espacos_academicos.espacosservice.service;

import com.espacos_academicos.espacosservice.dto.EspacoFisicoRequestDTO;
import com.espacos_academicos.espacosservice.dto.EspacoFisicoResponseDTO;
import com.espacos_academicos.espacosservice.dto.StatusUpdateRequestDTO;
import com.espacos_academicos.espacosservice.entity.EspacoFisico;
import com.espacos_academicos.espacosservice.exception.ResourceNotFoundException;
import com.espacos_academicos.espacosservice.model.StatusEspaco;
import com.espacos_academicos.espacosservice.model.TipoEspaco;
import com.espacos_academicos.espacosservice.repository.EspacoFisicoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspacoFisicoServiceImpl implements EspacoFisicoService {

    @Autowired
    private EspacoFisicoRepository espacoFisicoRepository;

    @Override
    @Transactional
    public EspacoFisicoResponseDTO criarEspaco(EspacoFisicoRequestDTO espacoRequestDTO) {
        if (espacoFisicoRepository.findBySigla(espacoRequestDTO.getSigla()).isPresent()) {
            throw new IllegalArgumentException("Já existe um espaço físico com a sigla: " + espacoRequestDTO.getSigla());
        }
        EspacoFisico espacoFisico = new EspacoFisico();
        BeanUtils.copyProperties(espacoRequestDTO, espacoFisico);
        espacoFisico.setStatus(StatusEspaco.ATIVO); // Padrão ao criar
        EspacoFisico salvo = espacoFisicoRepository.save(espacoFisico);
        return convertToResponseDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacoFisicoResponseDTO> listarTodosEspacos(StatusEspaco status, TipoEspaco tipo) {
        List<EspacoFisico> espacos;
        if (status != null && tipo != null) {
            // Implementar busca por status e tipo se necessário no repositório ou filtrar aqui
            espacos = espacoFisicoRepository.findAll().stream()
                    .filter(e -> e.getStatus() == status && e.getTipo() == tipo)
                    .collect(Collectors.toList());
        } else if (status != null) {
            espacos = espacoFisicoRepository.findByStatus(status);
        } else if (tipo != null) {
            espacos = espacoFisicoRepository.findAll().stream()
                    .filter(e -> e.getTipo() == tipo)
                    .collect(Collectors.toList()); //findByTipo espera String, precisaria de conversão ou ajuste no repo
        } else {
            espacos = espacoFisicoRepository.findAll();
        }
        return espacos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EspacoFisicoResponseDTO buscarEspacoPorId(Long id) {
        EspacoFisico espacoFisico = espacoFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espaço físico não encontrado com o ID: " + id));
        return convertToResponseDTO(espacoFisico);
    }

    @Override
    @Transactional
    public EspacoFisicoResponseDTO atualizarEspaco(Long id, EspacoFisicoRequestDTO espacoRequestDTO) {
        EspacoFisico espacoExistente = espacoFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espaço físico não encontrado com o ID: " + id));

        // Verifica se a nova sigla já existe em outro espaço
        espacoFisicoRepository.findBySigla(espacoRequestDTO.getSigla()).ifPresent(espacoComMesmaSigla -> {
            if (!espacoComMesmaSigla.getId().equals(id)) {
                throw new IllegalArgumentException("Já existe outro espaço físico com a sigla: " + espacoRequestDTO.getSigla());
            }
        });

        BeanUtils.copyProperties(espacoRequestDTO, espacoExistente, "id", "status"); // Não atualiza status aqui
        EspacoFisico atualizado = espacoFisicoRepository.save(espacoExistente);
        return convertToResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public EspacoFisicoResponseDTO atualizarStatusEspaco(Long id, StatusUpdateRequestDTO statusUpdateRequestDTO) {
        EspacoFisico espacoExistente = espacoFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espaço físico não encontrado com o ID: " + id));
        espacoExistente.setStatus(statusUpdateRequestDTO.getNovoStatus());
        EspacoFisico atualizado = espacoFisicoRepository.save(espacoExistente);
        return convertToResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public void deletarEspaco(Long id) {
        EspacoFisico espacoExistente = espacoFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espaço físico não encontrado com o ID: " + id));
        espacoExistente.setStatus(StatusEspaco.INATIVO); // Soft delete
        espacoFisicoRepository.save(espacoExistente);
    }

    private EspacoFisicoResponseDTO convertToResponseDTO(EspacoFisico espacoFisico) {
        EspacoFisicoResponseDTO responseDTO = new EspacoFisicoResponseDTO();
        BeanUtils.copyProperties(espacoFisico, responseDTO);
        return responseDTO;
    }
}