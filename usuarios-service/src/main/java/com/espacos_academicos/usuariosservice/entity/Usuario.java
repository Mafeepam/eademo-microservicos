package com.espacos_academicos.usuariosservice.entity;

import jakarta.persistence.*;
// Se estiver usando Lombok e ele estiver no pom.xml do usuarios-service:
// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuario") // Schema será pego do application.properties
// @Getter // Lombok
// @Setter // Lombok
// @NoArgsConstructor // Lombok
// @AllArgsConstructor // Lombok
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String funcao;

    // Se não usar Lombok, mantenha ou adicione os getters e setters manuais:
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
}