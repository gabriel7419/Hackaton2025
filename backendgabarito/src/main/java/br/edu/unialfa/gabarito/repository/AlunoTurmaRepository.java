package br.edu.unialfa.gabarito.repository;

import br.edu.unialfa.gabarito.model.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Long> {
    List<AlunoTurma> findByTurmaId(Long turmaId);
    List<AlunoTurma> findByAlunoId(Long alunoId);
}