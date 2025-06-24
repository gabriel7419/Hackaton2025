package com.unialfa.gabarito_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@JsonIgnoreProperties({"turma", "aluno"}) // Evita loops ao serializar TurmaAluno
@Entity
@Table(name = "turma_alunos")
public class TurmaAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private User aluno;

    public TurmaAluno() {}

    public TurmaAluno(Turma turma, User aluno) {
        this.turma = turma;
        this.aluno = aluno;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public User getAluno() { return aluno; }
    public void setAluno(User aluno) { this.aluno = aluno; }
}
