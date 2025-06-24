package com.unialfa.gabarito_app.controller.web;


import com.unialfa.gabarito_app.entity.*;
import com.unialfa.gabarito_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProvaService provaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/provas")
    public String provas(Model model, Authentication auth) {
        Optional<User> professor = userService.findByUsername(auth.getName());
        if (professor.isPresent()) {
            model.addAttribute("provas", provaService.findByProfessor(professor.get()));
        }
        model.addAttribute("prova", new Prova());
        model.addAttribute("turmas", turmaService.findAll());
        return "professor/provas";
    }

    @PostMapping("/provas")
    public String criarProva(@ModelAttribute Prova prova, Authentication auth) {
        Optional<User> professor = userService.findByUsername(auth.getName());
        if (professor.isPresent()) {
            prova.setProfessor(professor.get());
            provaService.save(prova);
            return "redirect:/professor/provas?success";
        }
        return "redirect:/professor/provas?error";
    }

    @GetMapping("/provas/delete/{id}")
    public String deletarProva(@PathVariable Long id, Authentication auth) {
        Optional<Prova> prova = provaService.findById(id);
        Optional<User> professor = userService.findByUsername(auth.getName());

        if (prova.isPresent() && professor.isPresent()) {
            // Verificar se a prova pertence ao professor ou se é admin
            if (prova.get().getProfessor().getId().equals(professor.get().getId()) ||
                    professor.get().getRole() == Role.ADMINISTRADOR) {
                try {
                    provaService.delete(id);
                    return "redirect:/professor/provas?deleted";
                } catch (Exception e) {
                    return "redirect:/professor/provas?error";
                }
            }
        }

        return "redirect:/professor/provas?unauthorized";
    }

    @GetMapping("/provas/{id}/resultados")
    public String resultados(@PathVariable Long id, Model model, Authentication auth) {
        Optional<Prova> provaOpt = provaService.findById(id);
        Optional<User> professor = userService.findByUsername(auth.getName());

        if (provaOpt.isPresent() && professor.isPresent()) {
            Prova prova = provaOpt.get();

            // Verificar permissão
            if (prova.getProfessor().getId().equals(professor.get().getId()) ||
                    professor.get().getRole() == Role.ADMINISTRADOR) {

                model.addAttribute("prova", prova);
                model.addAttribute("resultados", prova.getResultados());
                model.addAttribute("estatisticas", provaService.getEstatisticas(id));
                return "professor/resultados";
            }
        }

        return "redirect:/professor/provas?unauthorized";
    }

    @GetMapping("/dashboard")
    public String dashboardProfessor(Model model, Authentication auth) {
        Optional<User> professor = userService.findByUsername(auth.getName());
        if (professor.isPresent()) {
            var provas = provaService.findByProfessor(professor.get());
            model.addAttribute("totalProvas", provas.size());
            model.addAttribute("provasRecentes", provas.stream().limit(5).toList());

            long totalResultados = provas.stream()
                    .mapToLong(p -> p.getResultados().size())
                    .sum();
            model.addAttribute("totalResultados", totalResultados);
        }
        return "professor/dashboard";
    }
}
