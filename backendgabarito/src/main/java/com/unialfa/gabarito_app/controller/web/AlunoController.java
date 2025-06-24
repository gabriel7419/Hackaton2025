package com.unialfa.gabarito_app.controller.web;

import com.unialfa.gabarito_app.entity.User;
import com.unialfa.gabarito_app.service.UserService;
import com.unialfa.gabarito_app.service.ResultadoService;
import com.unialfa.gabarito_app.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/notas")
    public String notas(Model model, Authentication auth) {
        Optional<User> aluno = userService.findByUsername(auth.getName());
        if (aluno.isPresent()) {
            var resultados = resultadoService.findByAluno(aluno.get());
            model.addAttribute("resultados", resultados);

            if (!resultados.isEmpty()) {
                double mediaGeral = resultados.stream()
                        .mapToDouble(r -> r.getNota())
                        .average()
                        .orElse(0.0);

                long aprovacoes = resultados.stream()
                        .filter(r -> r.getNota() >= 6.0)
                        .count();

                model.addAttribute("mediaGeral", mediaGeral);
                model.addAttribute("aprovacoes", aprovacoes);
                model.addAttribute("reprovacoes", resultados.size() - aprovacoes);
            }
        }
        return "aluno/notas";
    }

    @GetMapping("/dashboard")
    public String dashboardAluno(Model model, Authentication auth) {
        Optional<User> aluno = userService.findByUsername(auth.getName());
        if (aluno.isPresent()) {
            var resultados = resultadoService.findByAluno(aluno.get());
            var turmas = turmaService.findTurmasByAluno(aluno.get());

            model.addAttribute("totalResultados", resultados.size());
            model.addAttribute("totalTurmas", turmas.size());
            model.addAttribute("resultadosRecentes", resultados.stream().limit(5).toList());

            if (!resultados.isEmpty()) {
                double mediaGeral = resultados.stream()
                        .mapToDouble(r -> r.getNota())
                        .average()
                        .orElse(0.0);
                model.addAttribute("mediaGeral", mediaGeral);
            }
        }
        return "aluno/dashboard";
    }

    @GetMapping("/turmas")
    public String minhasTurmas(Model model, Authentication auth) {
        Optional<User> aluno = userService.findByUsername(auth.getName());
        if (aluno.isPresent()) {
            model.addAttribute("turmasAluno", turmaService.findTurmasByAluno(aluno.get()));
        }
        return "aluno/turmas";
    }
}