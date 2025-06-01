package com.espacos_academicos.reservasservice.service;

import com.espacos_academicos.reservasservice.client.EspacosServiceClient;
import com.espacos_academicos.reservasservice.client.dto.EspacoFisicoDTO;
import com.espacos_academicos.reservasservice.client.dto.StatusEspaco;
import com.espacos_academicos.reservasservice.dto.ReservaRequestDTO;
import com.espacos_academicos.reservasservice.dto.ReservaResponseDTO;
import com.espacos_academicos.reservasservice.exception.ConflitoReservaException;
import com.espacos_academicos.reservasservice.exception.IntegracaoServicoException;
import com.espacos_academicos.reservasservice.exception.OperacaoNaoPermitidaException;
import com.espacos_academicos.reservasservice.exception.RecursoNaoEncontradoException;
import com.espacos_academicos.reservasservice.model.Reserva;
import com.espacos_academicos.reservasservice.model.StatusReserva;
import com.espacos_academicos.reservasservice.repository.ReservaRepository;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    private static final Logger logger = LoggerFactory.getLogger(ReservaServiceImpl.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EspacosServiceClient espacosServiceClient;

    @Override
    @Transactional
    public ReservaResponseDTO criarReserva(ReservaRequestDTO reservaRequestDTO) {
        if (!reservaRequestDTO.getHoraFim().isAfter(reservaRequestDTO.getHoraInicio())) {
            throw new IllegalArgumentException("A hora de fim deve ser após a hora de início.");
        }

        EspacoFisicoDTO espacoFisico;
        try {
            logger.info("Buscando espaço com ID: {}", reservaRequestDTO.getEspacoId());
            ResponseEntity<EspacoFisicoDTO> responseEspaco = espacosServiceClient.getEspacoById(reservaRequestDTO.getEspacoId());

            if (!responseEspaco.getStatusCode().is2xxSuccessful() || responseEspaco.getBody() == null) {
                throw new RecursoNaoEncontradoException("Espaço físico com ID " + reservaRequestDTO.getEspacoId() + " não encontrado ou serviço indisponível.");
            }
            espacoFisico = responseEspaco.getBody();
            logger.info("Espaço encontrado: {}. Status: {}", espacoFisico.getNome(), espacoFisico.getStatus());

            if (espacoFisico.getStatus() != StatusEspaco.ATIVO) {
                throw new OperacaoNaoPermitidaException("O espaço físico " + espacoFisico.getSigla() + " não está ativo para reservas. Status atual: " + espacoFisico.getStatus());
            }
        } catch (FeignException e) {
            logger.error("Erro ao chamar espacos-service para o ID {}: {}", reservaRequestDTO.getEspacoId(), e.getMessage());
            if (e.status() == 404) {
                throw new RecursoNaoEncontradoException("Espaço físico com ID " + reservaRequestDTO.getEspacoId() + " não encontrado no serviço de espaços.");
            }
            throw new IntegracaoServicoException("Erro de comunicação com o serviço de espaços: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado ao buscar espaço com ID {}: {}", reservaRequestDTO.getEspacoId(), e.getMessage());
            throw new IntegracaoServicoException("Erro inesperado ao verificar espaço físico: " + e.getMessage(), e);
        }

        List<Reserva> conflitos = reservaRepository.findConflictingReservas(
                reservaRequestDTO.getEspacoId(),
                reservaRequestDTO.getData(),
                reservaRequestDTO.getHoraInicio(),
                reservaRequestDTO.getHoraFim()
        );

        if (!conflitos.isEmpty()) {
            throw new ConflitoReservaException("Já existe uma reserva para este espaço neste horário.");
        }

        Reserva novaReserva = new Reserva();
        BeanUtils.copyProperties(reservaRequestDTO, novaReserva);
        novaReserva.setStatus(StatusReserva.SOLICITADA);

        Reserva reservaSalva = reservaRepository.save(novaReserva);
        logger.info("Reserva criada com ID: {}", reservaSalva.getId());
        return convertToResponseDTO(reservaSalva);
    }

    @Override
    public ReservaResponseDTO buscarReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada com o ID: " + id));
        return convertToResponseDTO(reserva);
    }

    @Override
    public List<ReservaResponseDTO> listarReservasPorProfessor(Integer professorId) {
        return reservaRepository.findAll().stream()
                .filter(r -> r.getProfessorId().equals(professorId))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarReservasPorEspacoEData(Long espacoId, LocalDate data) {
        return reservaRepository.findAll().stream()
                .filter(r -> r.getEspacoId().equals(espacoId) && r.getData().equals(data))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaResponseDTO> listarTodasReservas() {
        return reservaRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservaResponseDTO atualizarStatusReserva(Long id, StatusReserva novoStatus, Integer responsavelId, String perfilResponsavel) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada com o ID: " + id));

        logger.info("Atualizando status da reserva ID {} para {} por {} (ID: {})", id, novoStatus, perfilResponsavel, responsavelId);

        if (novoStatus == StatusReserva.REALIZADA) {
            if (!"professor".equalsIgnoreCase(perfilResponsavel) || !reserva.getProfessorId().equals(responsavelId)) {
                throw new OperacaoNaoPermitidaException("Apenas o professor da reserva pode marcar como realizada.");
            }
            if (reserva.getStatus() != StatusReserva.CONFIRMADA && reserva.getStatus() != StatusReserva.SOLICITADA) {
                throw new OperacaoNaoPermitidaException("Reserva não pode ser marcada como realizada neste estado: " + reserva.getStatus());
            }
        }
        // Adicionar mais lógicas aqui

        reserva.setStatus(novoStatus);
        Reserva reservaAtualizada = reservaRepository.save(reserva);
        return convertToResponseDTO(reservaAtualizada);
    }

    private ReservaResponseDTO convertToResponseDTO(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        BeanUtils.copyProperties(reserva, dto);
        return dto;
    }
}