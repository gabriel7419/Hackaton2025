package br.edu.unialfa.gabarito.repository;

import br.edu.unialfa.gabarito.model.RespostaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RespostaAlunoRepository extends JpaRepository<RespostaAluno, Long> {
    List<RespostaAluno> findByProvaId(Long provaId);
    List<RespostaAluno> findByAlunoId(Long alunoId);
    Optional<RespostaAluno> findByProvaIdAndAlunoId(Long provaId, Long alunoId);
}