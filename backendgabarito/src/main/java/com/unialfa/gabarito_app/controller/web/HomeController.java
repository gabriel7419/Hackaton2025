package com.unialfa.gabarito_app.controller.web;


import com.unialfa.gabarito_app.entity.Role;
import com.unialfa.gabarito_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProvaService provaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        if (auth != null) {
            // Verificar o papel do usuário e redirecionar para dashboard específico
            String role = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("");

            // Estatísticas gerais para todos
            model.addAttribute("totalUsuarios", userService.findAll().size());
            model.addAttribute("totalProvas", provaService.findAll().size());
            model.addAttribute("totalTurmas", turmaService.findAll().size());
            model.addAttribute("totalDisciplinas", disciplinaService.findAll().size());

            // Estatísticas por role
            model.addAttribute("totalProfessores", userService.findByRole(Role.PROFESSOR).size());
            model.addAttribute("totalAlunos", userService.findByRole(Role.ALUNO).size());
            model.addAttribute("totalAdmins", userService.findByRole(Role.ADMINISTRADOR).size());

            // Dados recentes
            model.addAttribute("provasRecentes", provaService.findAll().stream().limit(5).toList());

            switch (role) {
                case "ROLE_ADMINISTRADOR":
                    return "admin/dashboard";
                case "ROLE_PROFESSOR":
                    return "professor/dashboard";
                case "ROLE_ALUNO":
                    return "aluno/dashboard";
                default:
                    return "dashboard";
            }
        }
        return "dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

    @GetMapping("/error")
    public String error() {
        return "error/error";
    }
}
