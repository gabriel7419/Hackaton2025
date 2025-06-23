package br.edu.unialfa.gabarito.controller;

import br.edu.unialfa.gabarito.model.*;
import br.edu.unialfa.gabarito.repository.*;
import br.edu.unialfa.gabarito.service.GabaritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProvaRepository provaRepository;
    @Autowired private RespostaAlunoRepository respostaAlunoRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private GabaritoService gabaritoService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "usuario", Map.of(
                            "id", usuario.getId(),
                            "nome", usuario.getNome(),
                            "email", usuario.getEmail(),
                            "perfil", usuario.getPerfil().toString()
                    )
            ));
        }

        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Credenciais inválidas"));
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<Usuario>> getAlunos() {
        List<Usuario> alunos = usuarioRepository.findByPerfil(Usuario.Perfil.ALUNO);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/alunos/turma/{turmaId}")
    public ResponseEntity<List<Usuario>> getAlunosByTurma(@PathVariable Long turmaId) {
        List<Usuario> alunos = usuarioRepository.findAlunosByTurmaId(turmaId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/provas")
    public ResponseEntity<List<Prova>> getProvas() {
        List<Prova> provas = provaRepository.findAll();
        return ResponseEntity.ok(provas);
    }

    @GetMapping("/provas/turma/{turmaId}")
    public ResponseEntity<List<Prova>> getProvasByTurma(@PathVariable Long turmaId) {
        List<Prova> provas = provaRepository.findByTurmaId(turmaId);
        return ResponseEntity.ok(provas);
    }

    @PostMapping("/respostas")
    public ResponseEntity<?> enviarRespostas(@RequestBody Map<String, Object> dados) {
        try {
            Long provaId = Long.valueOf(dados.get("provaId").toString());
            Long alunoId = Long.valueOf(dados.get("alunoId").toString());
            List<String> respostas = (List<String>) dados.get("respostas");

            Prova prova = provaRepository.findById(provaId).orElse(null);
            Usuario aluno = usuarioRepository.findById(alunoId).orElse(null);

            if (prova == null || aluno == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Prova ou aluno não encontrado"));
            }

            // Verifica se já existe resposta
            RespostaAluno respostaExistente = respostaAlunoRepository.findByProvaIdAndAlunoId(provaId, alunoId).orElse(null);

            RespostaAluno respostaAluno;
            if (respostaExistente != null) {
                respostaAluno = respostaExistente;
            } else {
                respostaAluno = new RespostaAluno();
                respostaAluno.setProva(prova);
                respostaAluno.setAluno(aluno);
            }

            // Converte respostas para JSON
            ObjectMapper mapper = new ObjectMapper();
            respostaAluno.setRespostas(mapper.writeValueAsString(respostas));

            // Corrige a prova
            respostaAluno = gabaritoService.corrigirProva(respostaAluno);

            // Salva no banco
            respostaAlunoRepository.save(respostaAluno);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "nota", respostaAluno.getNota(),
                    "acertos", respostaAluno.getTotalAcertos(),
                    "total", prova.getTotalQuestoes()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/turmas")
    public ResponseEntity<List<Turma>> getTurmas() {
        List<Turma> turmas = turmaRepository.findAll();
        return ResponseEntity.ok(turmas);
    }
}