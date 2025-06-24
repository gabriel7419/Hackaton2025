package com.unialfa.gabarito_app.repository;

import com.unialfa.gabarito_app.entity.Turma;
import com.unialfa.gabarito_app.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByDisciplina(Disciplina disciplina);
    List<Turma> findByPeriodo(String periodo);
}