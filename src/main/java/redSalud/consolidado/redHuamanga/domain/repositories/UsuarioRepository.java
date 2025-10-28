package redSalud.consolidado.redHuamanga.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

  Optional<Usuario> findByUsername(String username);
  boolean existsByUsername(String username);
}
