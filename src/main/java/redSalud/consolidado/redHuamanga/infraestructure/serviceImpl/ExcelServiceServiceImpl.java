package redSalud.consolidado.redHuamanga.infraestructure.serviceImpl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redSalud.consolidado.redHuamanga.domain.entities.Registro;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.ExcelService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class ExcelServiceServiceImpl implements ExcelService {
    @Override
    public List<Registro> parseExcel(MultipartFile file) {
        List<Registro> registros = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0); // primera hoja
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) rows.next(); // saltar cabecera

            while (rows.hasNext()) {
                Row row = rows.next();
                Registro registro = new Registro();

                registro.setRed(getCellString(row, 0));
                registro.setAnio((int) getCellNumeric(row, 1));
                registro.setMes(getCellString(row, 2));
                registro.setMicroRed(getCellString(row, 3));
                registro.setIpress(getCellString(row, 4));
                registro.setCursoVida(getCellString(row, 5));
                registro.setAtendidosEess((int) getCellNumeric(row, 6));
                registro.setAtendidosServ((int) getCellNumeric(row, 7));
                registro.setAtencionesServ((int) getCellNumeric(row, 8));
                registro.setApp((int) getCellNumeric(row, 9));
                registro.setAa((int) getCellNumeric(row, 10));

                registros.add(registro);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el Excel: " + e.getMessage());
        }

        return registros;
    }

    private String getCellString(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell != null ? cell.toString().trim() : null;
    }

    private double getCellNumeric(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
        try {
            return Double.parseDouble(cell.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
