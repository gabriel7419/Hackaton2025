package com.unialfa.gabarito_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties({"resultados"}) // Evita ciclos ao serializar Prova
@Entity
@Table(name = "provas")
public class Prova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Integer quantidadeQuestoes;

    @Column
    private String gabarito; // A,B,C,D,A,B,C...

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL)
    private List<Resultado> resultados;

    public Prova() {}

    public Prova(String titulo, LocalDate data, Integer quantidadeQuestoes, String gabarito, Turma turma, User professor) {
        this.titulo = titulo;
        this.data = data;
        this.quantidadeQuestoes = quantidadeQuestoes;
        this.gabarito = gabarito;
        this.turma = turma;
        this.professor = professor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Integer getQuantidadeQuestoes() { return quantidadeQuestoes; }
    public void setQuantidadeQuestoes(Integer quantidadeQuestoes) { this.quantidadeQuestoes = quantidadeQuestoes; }

    public String getGabarito() { return gabarito; }
    public void setGabarito(String gabarito) { this.gabarito = gabarito; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public User getProfessor() { return professor; }
    public void setProfessor(User professor) { this.professor = professor; }

    public List<Resultado> getResultados() { return resultados; }
    public void setResultados(List<Resultado> resultados) { this.resultados = resultados; }
}
