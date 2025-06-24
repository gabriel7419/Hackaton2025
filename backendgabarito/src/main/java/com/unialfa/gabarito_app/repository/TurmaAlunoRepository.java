package com.unialfa.gabarito_app.repository;

import com.unialfa.gabarito_app.entity.TurmaAluno;
import com.unialfa.gabarito_app.entity.Turma;
import com.unialfa.gabarito_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {
    List<TurmaAluno> findByTurma(Turma turma);
    List<TurmaAluno> findByAluno(User aluno);
    Optional<TurmaAluno> findByTurmaAndAluno(Turma turma, User aluno);
    boolean existsByTurmaAndAluno(Turma turma, User aluno);
}