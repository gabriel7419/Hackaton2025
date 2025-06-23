package br.edu.unialfa.gabarito.repository;

import br.edu.unialfa.gabarito.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPerfil(Usuario.Perfil perfil);

    // Método simples sem JOIN
    default List<Usuario> findAlunosByTurmaId(Long turmaId) {
        return findByPerfil(Usuario.Perfil.ALUNO);
    }
}