package com.unialfa.gabarito_app.controller.web;

import com.unialfa.gabarito_app.entity.*;
import com.unialfa.gabarito_app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", userService.findAll());
        model.addAttribute("usuario", new User());
        return "admin/usuarios";
    }

    @PostMapping("/usuarios")
    public String criarUsuario(@ModelAttribute User usuario) {
        userService.save(usuario);
        return "redirect:/admin/usuarios?success";
    }

    @GetMapping("/usuarios/delete/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        try {
            userService.delete(id);
            return "redirect:/admin/usuarios?deleted";
        } catch (Exception e) {
            return "redirect:/admin/usuarios?error";
        }
    }

    @GetMapping("/disciplinas")
    public String disciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaService.findAll());
        model.addAttribute("disciplina", new Disciplina());
        return "admin/disciplinas";
    }

    @PostMapping("/disciplinas")
    public String criarDisciplina(@ModelAttribute Disciplina disciplina) {
        disciplinaService.save(disciplina);
        return "redirect:/admin/disciplinas?success";
    }

    @GetMapping("/disciplinas/delete/{id}")
    public String deletarDisciplina(@PathVariable Long id) {
        try {
            disciplinaService.delete(id);
            return "redirect:/admin/disciplinas?deleted";
        } catch (Exception e) {
            return "redirect:/admin/disciplinas?error";
        }
    }

    @GetMapping("/turmas")
    public String turmas(Model model) {
        model.addAttribute("turmas", turmaService.findAll());
        model.addAttribute("turma", new Turma());
        model.addAttribute("disciplinas", disciplinaService.findAll());
        return "admin/turmas";
    }

    @PostMapping("/turmas")
    public String criarTurma(@ModelAttribute Turma turma) {
        turmaService.save(turma);
        return "redirect:/admin/turmas?success";
    }

    @GetMapping("/turmas/delete/{id}")
    public String deletarTurma(@PathVariable Long id) {
        try {
            turmaService.delete(id);
            return "redirect:/admin/turmas?deleted";
        } catch (Exception e) {
            return "redirect:/admin/turmas?error";
        }
    }

    @GetMapping("/turmas/{id}/alunos")
    public String gerenciarAlunosTurma(@PathVariable Long id, Model model) {
        var turma = turmaService.findById(id);
        if (turma.isPresent()) {
            model.addAttribute("turma", turma.get());
            model.addAttribute("alunosNaTurma", turmaService.findAlunosByTurma(turma.get()));
            model.addAttribute("todosAlunos", userService.findByRole(Role.ALUNO));
        }
        return "admin/turma-alunos";
    }

    @PostMapping("/turmas/{turmaId}/alunos")
    public String adicionarAlunoTurma(@PathVariable Long turmaId, @RequestParam Long alunoId) {
        var turma = turmaService.findById(turmaId);
        var aluno = userService.findById(alunoId);

        if (turma.isPresent() && aluno.isPresent()) {
            turmaService.adicionarAluno(turma.get(), aluno.get());
            return "redirect:/admin/turmas/" + turmaId + "/alunos?added";
        }

        return "redirect:/admin/turmas/" + turmaId + "/alunos?error";
    }

    @PostMapping("/turmas/{turmaId}/alunos/{alunoId}/remover")
    public String removerAlunoTurma(@PathVariable Long turmaId, @PathVariable Long alunoId) {
        var turma = turmaService.findById(turmaId);
        var aluno = userService.findById(alunoId);

        if (turma.isPresent() && aluno.isPresent()) {
            turmaService.removerAluno(turma.get(), aluno.get());
            return "redirect:/admin/turmas/" + turmaId + "/alunos?removed";
        }

        return "redirect:/admin/turmas/" + turmaId + "/alunos?error";
    }
}