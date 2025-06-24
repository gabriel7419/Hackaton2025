package com.unialfa.gabarito_app.config;

import com.unialfa.gabarito_app.entity.*;
import com.unialfa.gabarito_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private ProvaService provaService;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuários padrão se não existirem
        if (!userService.existsByUsername("admin")) {
            User admin = new User("admin", "admin123", "Administrador", "admin@unialfa.edu.br", Role.ADMINISTRADOR);
            userService.save(admin);
        }

        if (!userService.existsByUsername("professor1")) {
            User professor = new User("professor1", "prof123", "Prof. João Silva", "joao@unialfa.edu.br", Role.PROFESSOR);
            userService.save(professor);
        }

        if (!userService.existsByUsername("aluno1")) {
            User aluno1 = new User("aluno1", "aluno123", "Maria Santos", "maria@aluno.unialfa.edu.br", Role.ALUNO);
            userService.save(aluno1);
        }

        if (!userService.existsByUsername("aluno2")) {
            User aluno2 = new User("aluno2", "aluno123", "Pedro Costa", "pedro@aluno.unialfa.edu.br", Role.ALUNO);
            userService.save(aluno2);
        }

        if (!userService.existsByUsername("aluno3")) {
            User aluno3 = new User("aluno3", "aluno123", "Ana Oliveira", "ana@aluno.unialfa.edu.br", Role.ALUNO);
            userService.save(aluno3);
        }

        // Criar disciplinas
        if (!disciplinaService.existsByCodigo("SPI001")) {
            Disciplina disciplina1 = new Disciplina("Sistemas para Internet", "SPI001");
            disciplinaService.save(disciplina1);
        }

        if (!disciplinaService.existsByCodigo("JAVA001")) {
            Disciplina disciplina2 = new Disciplina("Frameworks de Desenvolvimento Web Java", "JAVA001");
            disciplinaService.save(disciplina2);
        }

        if (!disciplinaService.existsByCodigo("MOBILE001")) {
            Disciplina disciplina3 = new Disciplina("Programação para Dispositivos Móveis", "MOBILE001");
            disciplinaService.save(disciplina3);
        }

        // Criar turmas
        Disciplina disciplina1 = disciplinaService.findByCodigo("SPI001").orElse(null);
        Disciplina disciplina2 = disciplinaService.findByCodigo("JAVA001").orElse(null);

        if (disciplina1 != null) {
            Turma turma1 = new Turma("5º Período - Sistemas para Internet", "5º", disciplina1);
            turma1 = turmaService.save(turma1);

            // Adicionar alunos à turma
            User aluno1 = userService.findByUsername("aluno1").orElse(null);
            User aluno2 = userService.findByUsername("aluno2").orElse(null);
            User aluno3 = userService.findByUsername("aluno3").orElse(null);

            if (aluno1 != null) turmaService.adicionarAluno(turma1, aluno1);
            if (aluno2 != null) turmaService.adicionarAluno(turma1, aluno2);
            if (aluno3 != null) turmaService.adicionarAluno(turma1, aluno3);
        }

        if (disciplina2 != null) {
            Turma turma2 = new Turma("5º Período - Frameworks Java", "5º", disciplina2);
            turma2 = turmaService.save(turma2);

            // Adicionar alunos à turma
            User aluno1 = userService.findByUsername("aluno1").orElse(null);
            User aluno2 = userService.findByUsername("aluno2").orElse(null);

            if (aluno1 != null) turmaService.adicionarAluno(turma2, aluno1);
            if (aluno2 != null) turmaService.adicionarAluno(turma2, aluno2);
        }

        // Criar uma prova exemplo
        User professor = userService.findByUsername("professor1").orElse(null);
        Turma turma = turmaService.findAll().stream().findFirst().orElse(null);

        if (professor != null && turma != null) {
            Prova prova = new Prova(
                    "Prova 1 - Hackathon",
                    LocalDate.now(),
                    10,
                    "A,B,C,D,A,B,C,D,A,B", // Gabarito
                    turma,
                    professor
            );
            provaService.save(prova);
        }

        System.out.println("=================================");
        System.out.println("Dados iniciais carregados!");
        System.out.println("=================================");
        System.out.println("Admin: admin / admin123");
        System.out.println("Professor: professor1 / prof123");
        System.out.println("Aluno1: aluno1 / aluno123");
        System.out.println("Aluno2: aluno2 / aluno123");
        System.out.println("Aluno3: aluno3 / aluno123");
        System.out.println("=================================");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("JDBC URL: jdbc:h2:mem:gabarito_db");
        System.out.println("=================================");
    }
}