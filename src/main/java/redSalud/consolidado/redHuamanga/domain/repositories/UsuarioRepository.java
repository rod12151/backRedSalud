package redSalud.consolidado.redHuamanga.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
