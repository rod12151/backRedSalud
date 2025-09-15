package redSalud.consolidado.redHuamanga.infraestructure.abstractService;

import redSalud.consolidado.redHuamanga.domain.entities.Usuario;

public interface usuarioService {
    Usuario crearUsuario();

    Usuario editarUsuario();
}