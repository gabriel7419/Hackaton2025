package br.edu.unialfa.gabarito.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "respostas_aluno")
public class RespostaAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prova_id", nullable = false)
    private Prova prova;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String respostas;

    @Column(precision = 5, scale = 2)
    private BigDecimal nota;

    @Column(name = "total_acertos")
    private Integer totalAcertos;

    @Column(name = "data_submissao")
    private LocalDateTime dataSubmissao = LocalDateTime.now();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Prova getProva() { return prova; }
    public void setProva(Prova prova) { this.prova = prova; }

    public Usuario getAluno() { return aluno; }
    public void setAluno(Usuario aluno) { this.aluno = aluno; }

    public String getRespostas() { return respostas; }
    public void setRespostas(String respostas) { this.respostas = respostas; }

    public BigDecimal getNota() { return nota; }
    public void setNota(BigDecimal nota) { this.nota = nota; }

    public Integer getTotalAcertos() { return totalAcertos; }
    public void setTotalAcertos(Integer totalAcertos) { this.totalAcertos = totalAcertos; }

    public LocalDateTime getDataSubmissao() { return dataSubmissao; }
    public void setDataSubmissao(LocalDateTime dataSubmissao) { this.dataSubmissao = dataSubmissao; }
}