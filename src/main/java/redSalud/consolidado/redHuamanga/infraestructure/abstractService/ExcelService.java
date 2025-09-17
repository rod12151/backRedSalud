package redSalud.consolidado.redHuamanga.infraestructure.abstractService;

import org.springframework.web.multipart.MultipartFile;
import redSalud.consolidado.redHuamanga.api.model.response.graficoResponse;
import redSalud.consolidado.redHuamanga.domain.entities.Registro;

import java.util.List;

public interface ExcelService {
    List<Registro> parseExcel(MultipartFile file);

    byte[] generarExcel(String red, String anio, String mes, String microRed, String ipress)throws Exception;
}
