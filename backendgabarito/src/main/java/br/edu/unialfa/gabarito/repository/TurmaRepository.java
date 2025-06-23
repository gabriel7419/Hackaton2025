package br.edu.unialfa.gabarito.repository;

import br.edu.unialfa.gabarito.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}