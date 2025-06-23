package br.edu.unialfa.gabarito.repository;

import br.edu.unialfa.gabarito.model.Prova;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProvaRepository extends JpaRepository<Prova, Long> {
    List<Prova> findByProfessorId(Long professorId);
    List<Prova> findByTurmaId(Long turmaId);
}