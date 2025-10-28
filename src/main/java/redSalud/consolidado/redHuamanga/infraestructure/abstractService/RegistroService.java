package redSalud.consolidado.redHuamanga.infraestructure.abstractService;

import redSalud.consolidado.redHuamanga.domain.entities.Registro;
 import redSalud.consolidado.redHuamanga.api.dto.response.graficoResponse;
import java.util.List;

public interface RegistroService {
    List<Registro> getAll();
    List<Registro> getByAnio(Integer anio);
    List<Registro> getByMes(String mes);
    List<Registro> getByMicroRed(String microRed);
    Registro save(Registro registro);
    List<Registro> saveAll(List<Registro> registros);

    graficoResponse getGraficoCursoVida(String red, Integer anio, String mes, String microRed, String ipress);
}
