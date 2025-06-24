package com.unialfa.gabarito_app.repository;

import com.unialfa.gabarito_app.entity.Resultado;
import com.unialfa.gabarito_app.entity.Prova;
import com.unialfa.gabarito_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    List<Resultado> findByProva(Prova prova);
    List<Resultado> findByAluno(User aluno);
    Optional<Resultado> findByProvaAndAluno(Prova prova, User aluno);
    boolean existsByProvaAndAluno(Prova prova, User aluno);

    @Query("SELECT AVG(r.nota) FROM Resultado r WHERE r.prova = :prova")
    Double findMediaByProva(Prova prova);

    @Query("SELECT COUNT(r) FROM Resultado r WHERE r.prova = :prova AND r.nota >= :nota")
    Long countByProvaAndNotaGreaterThanEqual(Prova prova, Double nota);
}
