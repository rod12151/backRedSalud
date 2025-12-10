package redSalud.consolidado.redHuamanga.util.exceptions;

public class EntityNoExiste extends RuntimeException {
    private static final String ERROR_MESSAGE = "No se encontraron datos con el id: %s, en la entidad: %s ";
    public EntityNoExiste(String id,String entidad) {
        super(String.format(ERROR_MESSAGE,id,entidad));
    }

}
