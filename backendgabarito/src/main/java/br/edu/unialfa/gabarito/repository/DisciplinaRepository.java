package br.edu.unialfa.gabarito.repository;

import br.edu.unialfa.gabarito.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}