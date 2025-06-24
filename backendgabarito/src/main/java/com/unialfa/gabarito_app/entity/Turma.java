package com.unialfa.gabarito_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties({"provas", "alunos"}) // Evita ciclos com Prova e TurmaAluno
@Entity
@Table(name = "turmas")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String periodo;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<TurmaAluno> alunos;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<Prova> provas;

    public Turma() {}

    public Turma(String nome, String periodo, Disciplina disciplina) {
        this.nome = nome;
        this.periodo = periodo;
        this.disciplina = disciplina;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public List<TurmaAluno> getAlunos() { return alunos; }
    public void setAlunos(List<TurmaAluno> alunos) { this.alunos = alunos; }

    public List<Prova> getProvas() { return provas; }
    public void setProvas(List<Prova> provas) { this.provas = provas; }
}
