package redSalud.consolidado.redHuamanga.infraestructure.abstractService;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void parseExcel(MultipartFile file);

    byte[] generarExcel(String red, String anio, String mes, String microRed, String ipress)throws Exception;
}
