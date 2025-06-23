package br.edu.unialfa.gabarito.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "provas")
public class Prova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Usuario professor;

    @Column(name = "data_prova", nullable = false)
    private LocalDate dataProva;

    @Column(name = "total_questoes", nullable = false)
    private Integer totalQuestoes;

    @Column(name = "gabarito_oficial", nullable = false, columnDefinition = "TEXT")
    private String gabaritoOficial;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVA;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public Usuario getProfessor() { return professor; }
    public void setProfessor(Usuario professor) { this.professor = professor; }

    public LocalDate getDataProva() { return dataProva; }
    public void setDataProva(LocalDate dataProva) { this.dataProva = dataProva; }

    public Integer getTotalQuestoes() { return totalQuestoes; }
    public void setTotalQuestoes(Integer totalQuestoes) { this.totalQuestoes = totalQuestoes; }

    public String getGabaritoOficial() { return gabaritoOficial; }
    public void setGabaritoOficial(String gabaritoOficial) { this.gabaritoOficial = gabaritoOficial; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public enum Status {
        ATIVA, FINALIZADA
    }
}