package br.edu.unialfa.gabarito.controller;

import br.edu.unialfa.gabarito.model.*;
import br.edu.unialfa.gabarito.repository.*;
import br.edu.unialfa.gabarito.service.GabaritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired private ProvaRepository provaRepository;
    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private RespostaAlunoRepository respostaAlunoRepository;
    @Autowired private GabaritoService gabaritoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        Usuario professor = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        if (professor != null) {
            List<Prova> provas = provaRepository.findByProfessorId(professor.getId());
            model.addAttribute("totalProvas", provas.size());
            model.addAttribute("provas", provas);
        }
        return "professor/dashboard";
    }

    @GetMapping("/provas")
    public String provas(Model model, Authentication auth) {
        Usuario professor = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        if (professor != null) {
            model.addAttribute("provas", provaRepository.findByProfessorId(professor.getId()));
        }
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("turmas", turmaRepository.findAll());
        model.addAttribute("prova", new Prova());
        return "professor/provas";
    }

    @PostMapping("/provas")
    public String salvarProva(@ModelAttribute Prova prova, Authentication auth) {
        Usuario professor = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        prova.setProfessor(professor);

        // Converte o gabarito para JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Assumindo que o gabarito vem como string separada por vírgulas
            String[] respostas = prova.getGabaritoOficial().split(",");
            List<String> gabaritoList = List.of(respostas);
            prova.setGabaritoOficial(mapper.writeValueAsString(gabaritoList));
        } catch (Exception e) {
            // Mantém o valor original se não conseguir converter
        }

        provaRepository.save(prova);
        return "redirect:/professor/provas";
    }

    @GetMapping("/resultados/{provaId}")
    public String resultados(@PathVariable Long provaId, Model model) {
        Prova prova = provaRepository.findById(provaId).orElse(null);
        if (prova != null) {
            List<RespostaAluno> respostas = respostaAlunoRepository.findByProvaId(provaId);

            // Calcula estatísticas
            double media = respostas.stream()
                    .mapToDouble(r -> r.getNota().doubleValue())
                    .average().orElse(0.0);

            model.addAttribute("prova", prova);
            model.addAttribute("respostas", respostas);
            model.addAttribute("media", BigDecimal.valueOf(media).setScale(2, BigDecimal.ROUND_HALF_UP));
            model.addAttribute("totalAlunos", respostas.size());
        }
        return "professor/resultados";
    }
}