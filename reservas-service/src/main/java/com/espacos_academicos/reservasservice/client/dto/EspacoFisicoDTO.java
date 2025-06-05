package com.espacos_academicos.reservasservice.client.dto;

public class EspacoFisicoDTO {
    private Long id;
    private String nome;
    private String sigla;
    private StatusEspaco status;
    
    // Construtores
    public EspacoFisicoDTO() {}
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }
    
    public StatusEspaco getStatus() { return status; }
    public void setStatus(StatusEspaco status) { this.status = status; }
}