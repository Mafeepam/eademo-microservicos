package com.espacos_academicos.usuariosservice.dto;

public class PerfilDTO {
    private Long id;
    private String email;
    private String senha; // Descomentado para manter a funcionalidade da demonstração
    private String funcao;
    private String nome;
    private String curso;
    private String telefone;

    // Construtores, Getters e Setters
    public PerfilDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; } // Descomentado
    public void setSenha(String senha) { this.senha = senha; } // Descomentado
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}