package redSalud.consolidado.redHuamanga.Security.exception;

public class UsuarioDuplicado extends RuntimeException {
    private static final String ERROR_MESSAGE = "El usuario:  %s, que intenta ingresar, ya se encuentra registrado en el sistema";
    public UsuarioDuplicado(String usuario) {
        super(String.format(ERROR_MESSAGE, usuario));
    }
}
