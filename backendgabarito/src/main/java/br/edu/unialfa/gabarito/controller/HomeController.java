package br.edu.unialfa.gabarito.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority();

        return switch (role) {
            case "ROLE_ADMINISTRADOR" -> "redirect:/admin/home";
            case "ROLE_PROFESSOR" -> "redirect:/professor/dashboard";
            case "ROLE_ALUNO" -> "redirect:/aluno/dashboard";
            default -> "redirect:/login";
        };
    }
}
