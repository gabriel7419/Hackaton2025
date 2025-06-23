package br.edu.unialfa.gabarito.config;

import br.edu.unialfa.gabarito.model.*;
import br.edu.unialfa.gabarito.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private ProvaRepository provaRepository;
    @Autowired private RespostaAlunoRepository respostaAlunoRepository;

    @Override
    public void run(String... args) throws Exception {

        // Verifica se já existem dados
        if (usuarioRepository.count() > 0) {
            System.out.println("⚠️ Dados já existem no banco. Pulando DataLoader...");
            return;
        }

        System.out.println("🚀 Executando DataLoader...");

        // Deleta na ordem correta (filhos primeiro)
        respostaAlunoRepository.deleteAll();
        provaRepository.deleteAll();
        usuarioRepository.deleteAll();
        disciplinaRepository.deleteAll();
        turmaRepository.deleteAll();

        // Cria usuários com senhas simples
        Usuario admin = new Usuario();
        admin.setNome("Administrador");
        admin.setEmail("admin@unialfa.edu.br");
        admin.setSenha("123456");
        admin.setPerfil(Usuario.Perfil.ADMINISTRADOR);
        admin = usuarioRepository.save(admin);

        Usuario professor = new Usuario();
        professor.setNome("Prof. João Silva");
        professor.setEmail("joao.silva@unialfa.edu.br");
        professor.setSenha("123456");
        professor.setPerfil(Usuario.Perfil.PROFESSOR);
        professor = usuarioRepository.save(professor);

        Usuario aluno1 = new Usuario();
        aluno1.setNome("Maria Santos");
        aluno1.setEmail("maria.santos@estudante.unialfa.edu.br");
        aluno1.setSenha("123456");
        aluno1.setPerfil(Usuario.Perfil.ALUNO);
        aluno1 = usuarioRepository.save(aluno1);

        Usuario aluno2 = new Usuario();
        aluno2.setNome("Pedro Costa");
        aluno2.setEmail("pedro.costa@estudante.unialfa.edu.br");
        aluno2.setSenha("123456");
        aluno2.setPerfil(Usuario.Perfil.ALUNO);
        aluno2 = usuarioRepository.save(aluno2);

        Usuario aluno3 = new Usuario();
        aluno3.setNome("Ana Oliveira");
        aluno3.setEmail("ana.oliveira@estudante.unialfa.edu.br");
        aluno3.setSenha("123456");
        aluno3.setPerfil(Usuario.Perfil.ALUNO);
        aluno3 = usuarioRepository.save(aluno3);

        // Cria turmas
        Turma turma1 = new Turma();
        turma1.setNome("5º Período - Sistemas para Internet");
        turma1.setDescricao("Turma do 5º período");
        turma1 = turmaRepository.save(turma1);

        Turma turma2 = new Turma();
        turma2.setNome("4º Período - Sistemas para Internet");
        turma2.setDescricao("Turma do 4º período");
        turma2 = turmaRepository.save(turma2);

        // Cria disciplinas
        Disciplina disciplina1 = new Disciplina();
        disciplina1.setNome("Frameworks de Desenvolvimento Web Java");
        disciplina1.setCodigo("FDWJ001");
        disciplina1 = disciplinaRepository.save(disciplina1);

        Disciplina disciplina2 = new Disciplina();
        disciplina2.setNome("Programação para Dispositivos Móveis");
        disciplina2.setCodigo("PDM001");
        disciplina2 = disciplinaRepository.save(disciplina2);

        Disciplina disciplina3 = new Disciplina();
        disciplina3.setNome("Banco de Dados");
        disciplina3.setCodigo("BD001");
        disciplina3 = disciplinaRepository.save(disciplina3);

        // Cria provas
        Prova prova1 = new Prova();
        prova1.setTitulo("Prova 1 - Spring Boot");
        prova1.setDisciplina(disciplina1);
        prova1.setTurma(turma1);
        prova1.setProfessor(professor);
        prova1.setDataProva(LocalDate.now().plusDays(3));
        prova1.setTotalQuestoes(10);
        prova1.setGabaritoOficial("[\"A\",\"B\",\"C\",\"D\",\"A\",\"B\",\"C\",\"D\",\"A\",\"B\"]");
        prova1 = provaRepository.save(prova1);

        Prova prova2 = new Prova();
        prova2.setTitulo("Prova 1 - Flutter Básico");
        prova2.setDisciplina(disciplina2);
        prova2.setTurma(turma1);
        prova2.setProfessor(professor);
        prova2.setDataProva(LocalDate.now().plusDays(4));
        prova2.setTotalQuestoes(5);
        prova2.setGabaritoOficial("[\"A\",\"C\",\"B\",\"D\",\"A\"]");
        prova2 = provaRepository.save(prova2);

        Prova prova3 = new Prova();
        prova3.setTitulo("Prova de Banco de Dados");
        prova3.setDisciplina(disciplina3);
        prova3.setTurma(turma1);
        prova3.setProfessor(professor);
        prova3.setDataProva(LocalDate.now().plusDays(5));
        prova3.setTotalQuestoes(8);
        prova3.setGabaritoOficial("[\"B\",\"A\",\"D\",\"C\",\"A\",\"B\",\"D\",\"C\"]");
        prova3 = provaRepository.save(prova3);

        System.out.println("✅ DADOS CARREGADOS COM SUCESSO!");
        System.out.println("🔑 Usuários criados:");
        System.out.println("   👨‍💼 Admin: admin@unialfa.edu.br / 123456");
        System.out.println("   👨‍🏫 Professor: joao.silva@unialfa.edu.br / 123456");
        System.out.println("   👩‍🎓 Aluno: maria.santos@estudante.unialfa.edu.br / 12345678910");
        System.out.println("   👨‍🎓 Aluno: pedro.costa@estudante.unialfa.edu.br / 123456");
        System.out.println("   👩‍🎓 Aluno: ana.oliveira@estudante.unialfa.edu.br / 123456");
        System.out.println("📚 " + disciplinaRepository.count() + " disciplinas criadas");
        System.out.println("🎓 " + turmaRepository.count() + " turmas criadas");
        System.out.println("📝 " + provaRepository.count() + " provas criadas");
        System.out.println("🎉 Sistema pronto para uso!");
    }
}