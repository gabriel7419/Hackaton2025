package br.edu.unialfa.gabarito.controller;

import br.edu.unialfa.gabarito.model.*;
import br.edu.unialfa.gabarito.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String dashboard(Model model) {
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalTurmas", turmaRepository.count());
        model.addAttribute("totalDisciplinas", disciplinaRepository.count());
        return "admin/dashboard";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios";
    }

    @PostMapping("/usuarios")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/turmas")
    public String turmas(Model model) {
        model.addAttribute("turmas", turmaRepository.findAll());
        model.addAttribute("turma", new Turma());
        return "admin/turmas";
    }

    @PostMapping("/turmas")
    public String salvarTurma(@ModelAttribute Turma turma) {
        turmaRepository.save(turma);
        return "redirect:/admin/turmas";
    }

    @GetMapping("/disciplinas")
    public String disciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("disciplina", new Disciplina());
        return "admin/disciplinas";
    }

    @PostMapping("/disciplinas")
    public String salvarDisciplina(@ModelAttribute Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
        return "redirect:/admin/disciplinas";
    }
}
