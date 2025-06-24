package com.unialfa.gabarito_app.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<TurmaAluno> turmas;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Prova> provas;

    // Construtores
    public User() {}

    public User(String username, String password, String nome, String email, Role role) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<TurmaAluno> getTurmas() { return turmas; }
    public void setTurmas(List<TurmaAluno> turmas) { this.turmas = turmas; }

    public List<Prova> getProvas() { return provas; }
    public void setProvas(List<Prova> provas) { this.provas = provas; }
}
