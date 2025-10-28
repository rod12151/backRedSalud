package redSalud.consolidado.redHuamanga.domain.repositories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import redSalud.consolidado.redHuamanga.domain.entities.Rol;
import redSalud.consolidado.redHuamanga.util.enums.Role;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombre(Role rol);
}
