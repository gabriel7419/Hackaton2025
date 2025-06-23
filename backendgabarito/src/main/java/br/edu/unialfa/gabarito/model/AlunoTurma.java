package br.edu.unialfa.gabarito.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aluno_turma")
public class AlunoTurma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aluno_id", nullable = false)
    private Long alunoId;

    @Column(name = "turma_id", nullable = false)
    private Long turmaId;

    @Column(name = "data_matricula")
    private LocalDateTime dataMatricula = LocalDateTime.now();

    // Relacionamentos opcionais
    @ManyToOne
    @JoinColumn(name = "aluno_id", insertable = false, updatable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", insertable = false, updatable = false)
    private Turma turma;

    // Construtores
    public AlunoTurma() {}

    public AlunoTurma(Long alunoId, Long turmaId) {
        this.alunoId = alunoId;
        this.turmaId = turmaId;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAlunoId() { return alunoId; }
    public void setAlunoId(Long alunoId) { this.alunoId = alunoId; }

    public Long getTurmaId() { return turmaId; }
    public void setTurmaId(Long turmaId) { this.turmaId = turmaId; }

    public LocalDateTime getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDateTime dataMatricula) { this.dataMatricula = dataMatricula; }

    public Usuario getAluno() { return aluno; }
    public void setAluno(Usuario aluno) { this.aluno = aluno; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}