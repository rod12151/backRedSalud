package redSalud.consolidado.redHuamanga.infraestructure.abstractService;

import redSalud.consolidado.redHuamanga.api.dto.requests.CreateUserRequest;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(CreateUserRequest request);

    Usuario editarUsuario(String id, CreateUserRequest request);

    List<Usuario> listarUsuarios();
}