package com.unialfa.gabarito_app.controller.api;

import com.unialfa.gabarito_app.entity.*;
import com.unialfa.gabarito_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProvaService provaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("username", user.getUsername());
                userMap.put("nome", user.getNome());
                userMap.put("email", user.getEmail());
                userMap.put("role", user.getRole().name());
                response.put("success", true);
                response.put("user", userMap);
                return ResponseEntity.ok(response);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Credenciais inválidas");
        return ResponseEntity.badRequest().body(response);
    }

    // Listar alunos
    @GetMapping("/alunos")
    public ResponseEntity<List<User>> getAlunos() {
        List<User> alunos = userService.findByRole(Role.ALUNO);
        return ResponseEntity.ok(alunos);
    }

    // Listar provas
    @GetMapping("/provas")
    public ResponseEntity<List<Prova>> getProvas() {
        List<Prova> provas = provaService.findAll();
        return ResponseEntity.ok(provas);
    }

    // Buscar prova por ID
    @GetMapping("/provas/{id}")
    public ResponseEntity<Prova> getProva(@PathVariable Long id) {
        Optional<Prova> prova = provaService.findById(id);
        return prova.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Submeter respostas
    @PostMapping("/respostas")
    public ResponseEntity<Map<String, Object>> submeterRespostas(@RequestBody Map<String, Object> dados) {
        try {
            Long provaId = Long.valueOf(dados.get("provaId").toString());
            Long alunoId = Long.valueOf(dados.get("alunoId").toString());
            String respostas = dados.get("respostas").toString();

            Optional<Prova> provaOpt = provaService.findById(provaId);
            Optional<User> alunoOpt = userService.findById(alunoId);

            if (provaOpt.isEmpty() || alunoOpt.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Prova ou aluno não encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            Resultado resultado = new Resultado(provaOpt.get(), alunoOpt.get(), respostas);
            resultado = resultadoService.save(resultado);

            Map<String, Object> resultadoMap = new HashMap<>();
            resultadoMap.put("id", resultado.getId());
            resultadoMap.put("nota", resultado.getNota());
            resultadoMap.put("acertos", resultado.getAcertos());
            resultadoMap.put("erros", resultado.getErros());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("resultado", resultadoMap);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erro ao processar respostas: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Buscar turmas do aluno
    @GetMapping("/alunos/{alunoId}/turmas")
    public ResponseEntity<List<Map<String, Object>>> getTurmasAluno(@PathVariable Long alunoId) {
        Optional<User> alunoOpt = userService.findById(alunoId);
        if (alunoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TurmaAluno> turmasAluno = turmaService.findTurmasByAluno(alunoOpt.get());
        List<Map<String, Object>> response = new ArrayList<>();

        for (TurmaAluno ta : turmasAluno) {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", ta.getTurma().getId());
            mapa.put("nome", ta.getTurma().getNome());
            mapa.put("periodo", ta.getTurma().getPeriodo());
            mapa.put("disciplina", ta.getTurma().getDisciplina().getNome());
            response.add(mapa);
        }

        return ResponseEntity.ok(response);
    }

    // Buscar notas do aluno
    @GetMapping("/alunos/{alunoId}/notas")
    public ResponseEntity<List<Map<String, Object>>> getNotasAluno(@PathVariable Long alunoId) {
        Optional<User> alunoOpt = userService.findById(alunoId);
        if (alunoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Resultado> resultados = resultadoService.findByAluno(alunoOpt.get());
        List<Map<String, Object>> response = new ArrayList<>();

        for (Resultado r : resultados) {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("provaId", r.getProva().getId());
            mapa.put("prova", r.getProva().getTitulo());
            mapa.put("turma", r.getProva().getTurma().getNome());
            mapa.put("disciplina", r.getProva().getTurma().getDisciplina().getNome());
            mapa.put("data", r.getProva().getData().toString());
            mapa.put("nota", r.getNota());
            mapa.put("acertos", r.getAcertos());
            mapa.put("erros", r.getErros());
            response.add(mapa);
        }

        return ResponseEntity.ok(response);
    }
}
