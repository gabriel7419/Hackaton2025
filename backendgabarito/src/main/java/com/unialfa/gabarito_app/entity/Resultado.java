package com.unialfa.gabarito_app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resultados")
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prova_id")
    private Prova prova;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private User aluno;

    @Column
    private String respostas; // A,B,C,D,A,B,C...

    @Column
    private Double nota;

    @Column
    private Integer acertos;

    @Column
    private Integer erros;

    public Resultado() {}

    public Resultado(Prova prova, User aluno, String respostas) {
        this.prova = prova;
        this.aluno = aluno;
        this.respostas = respostas;
        calcularNota();
    }

    public void calcularNota() {
        if (prova != null && prova.getGabarito() != null && respostas != null) {
            String[] gabaritoArray = prova.getGabarito().split(",");
            String[] respostasArray = respostas.split(",");

            int acertosCount = 0;
            int minLength = Math.min(gabaritoArray.length, respostasArray.length);

            for (int i = 0; i < minLength; i++) {
                if (gabaritoArray[i].trim().equals(respostasArray[i].trim())) {
                    acertosCount++;
                }
            }

            this.acertos = acertosCount;
            this.erros = prova.getQuantidadeQuestoes() - acertosCount;
            this.nota = (double) acertosCount / prova.getQuantidadeQuestoes() * 10.0;
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Prova getProva() { return prova; }
    public void setProva(Prova prova) { this.prova = prova; }

    public User getAluno() { return aluno; }
    public void setAluno(User aluno) { this.aluno = aluno; }

    public String getRespostas() { return respostas; }
    public void setRespostas(String respostas) {
        this.respostas = respostas;
        calcularNota();
    }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public Integer getAcertos() { return acertos; }
    public void setAcertos(Integer acertos) { this.acertos = acertos; }

    public Integer getErros() { return erros; }
    public void setErros(Integer erros) { this.erros = erros; }
}
