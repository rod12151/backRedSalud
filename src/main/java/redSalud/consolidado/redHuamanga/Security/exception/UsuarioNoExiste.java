package redSalud.consolidado.redHuamanga.Security.exception;

public class UsuarioNoExiste extends RuntimeException {
    private static final String ERROR_MESSAGE = "El usuario:  %s, que intenta ingresar, no se encuentra registrado en el sistema";
    public UsuarioNoExiste(String usuario) {
        super(String.format(ERROR_MESSAGE, usuario));
    }
}
