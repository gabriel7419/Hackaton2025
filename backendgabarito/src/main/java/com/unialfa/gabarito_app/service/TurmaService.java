package com.unialfa.gabarito_app.service;

import com.unialfa.gabarito_app.entity.Turma;
import com.unialfa.gabarito_app.entity.TurmaAluno;
import com.unialfa.gabarito_app.entity.User;
import com.unialfa.gabarito_app.repository.TurmaRepository;
import com.unialfa.gabarito_app.repository.TurmaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    public Turma save(Turma turma) {
        return turmaRepository.save(turma);
    }

    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    public Optional<Turma> findById(Long id) {
        return turmaRepository.findById(id);
    }

    public void delete(Long id) {
        turmaRepository.deleteById(id);
    }

    public TurmaAluno adicionarAluno(Turma turma, User aluno) {
        if (!turmaAlunoRepository.existsByTurmaAndAluno(turma, aluno)) {
            TurmaAluno turmaAluno = new TurmaAluno(turma, aluno);
            return turmaAlunoRepository.save(turmaAluno);
        }
        return null;
    }

    public void removerAluno(Turma turma, User aluno) {
        Optional<TurmaAluno> turmaAluno = turmaAlunoRepository.findByTurmaAndAluno(turma, aluno);
        turmaAluno.ifPresent(ta -> turmaAlunoRepository.delete(ta));
    }

    public List<TurmaAluno> findAlunosByTurma(Turma turma) {
        return turmaAlunoRepository.findByTurma(turma);
    }

    public List<TurmaAluno> findTurmasByAluno(User aluno) {
        return turmaAlunoRepository.findByAluno(aluno);
    }
}