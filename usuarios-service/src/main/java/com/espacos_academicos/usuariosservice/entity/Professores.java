package com.espacos_academicos.usuariosservice.entity; 

import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;
// // ... outras anotações Lombok se usar

@Entity
@Table(name = "professores") // Schema será pego do application.properties
// @Getter // Lombok
// @Setter // Lombok
public class Professores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true) // Boa prática, já que está UNIQUE no BD
    private String email;

    private String curso;

    // Se não usar Lombok, mantenha ou adicione os getters e setters manuais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}