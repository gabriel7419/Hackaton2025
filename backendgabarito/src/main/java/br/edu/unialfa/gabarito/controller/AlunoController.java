package br.edu.unialfa.gabarito.controller;

import br.edu.unialfa.gabarito.model.*;
import br.edu.unialfa.gabarito.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired private RespostaAlunoRepository respostaAlunoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProvaRepository provaRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        Usuario aluno = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        if (aluno != null) {
            List<RespostaAluno> respostas = respostaAlunoRepository.findByAlunoId(aluno.getId());
            model.addAttribute("respostas", respostas);
            model.addAttribute("totalProvas", respostas.size());
        }
        return "aluno/dashboard";
    }

    @GetMapping("/notas")
    public String notas(Model model, Authentication auth) {
        Usuario aluno = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        if (aluno != null) {
            List<RespostaAluno> respostas = respostaAlunoRepository.findByAlunoId(aluno.getId());
            model.addAttribute("respostas", respostas);
        }
        return "aluno/notas";
    }
}