package com.unialfa.gabarito_app.repository;

import com.unialfa.gabarito_app.entity.Prova;
import com.unialfa.gabarito_app.entity.Turma;
import com.unialfa.gabarito_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {
    List<Prova> findByTurma(Turma turma);
    List<Prova> findByProfessor(User professor);
    List<Prova> findByData(LocalDate data);
    List<Prova> findByTurmaAndData(Turma turma, LocalDate data);
}