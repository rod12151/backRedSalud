package redSalud.consolidado.redHuamanga.infraestructure.serviceImpl;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redSalud.consolidado.redHuamanga.api.model.response.graficoResponse;
import redSalud.consolidado.redHuamanga.domain.entities.Registro;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.ExcelService;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.RegistroService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ExcelServiceServiceImpl implements ExcelService {
    RegistroService registroService;
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

    @Override
    public byte[] generarExcel(String red, String anio, String mes, String microRed, String ipress) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        String redAux="HUAMANGA";
        String mesAux = mes;
        String microRedAux =microRed;
        String ipressAux =ipress;

        if (mes != null && mes.isBlank()) mes = null;
        if (Objects.equals(microRed,"TODOS")) microRed = null;
        if (Objects.equals((microRed),"NO MICRORED")) microRed="NO PERTENECE A NINGUNA MICRORED";
        if (Objects.equals(ipress,"TODOS")) ipress = null;
        Integer anioInt = null;
        if (anio != null && !anio.equalsIgnoreCase("TODOS") && !anio.isBlank()) {
            anioInt = Integer.valueOf(anio);
        }

        graficoResponse graficoResponse=registroService.getGraficoCursoVida(red,anioInt,mes,microRed,ipress);
        List<String> labels=graficoResponse.getLabels();
        List<Integer> atendidos_eess=graficoResponse.getAtendidosEess();
        List<Integer> atendidos_Serv=graficoResponse.getAtendidosServ();
        List<Integer> atenciones_Serv=graficoResponse.getAtencionesServ();


        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)12);
        font.setFontName("Arial");
        font.setItalic(true);
        headerStyle.setFont(font);

        Sheet sheet = workbook.createSheet("reporte");

        Row redRow=sheet.createRow(2);
        Cell redRowCell1=redRow.createCell(0);
        redRowCell1.setCellValue("RED DE SALUD");
        redRowCell1.setCellStyle(headerStyle);

        redRow.createCell(1).setCellValue(redAux);

        Row microRedRow=sheet.createRow(3);
        microRedRow.createCell(0).setCellValue("MICRORED");
        microRedRow.createCell(1).setCellValue(microRedAux);
        Row ipressRow=sheet.createRow(4);
        ipressRow.createCell(0).setCellValue("PS");
        ipressRow.createCell(1).setCellValue(ipressAux);
        Row anioRow=sheet.createRow(5);
        anioRow.createCell(0).setCellValue("AÑO");
        anioRow.createCell(1).setCellValue(anio);
        Row mesRow=sheet.createRow(6);
        mesRow.createCell(0).setCellValue("MES");
        mesRow.createCell(1).setCellValue(mesAux);

        Row headerRow=sheet.createRow(8);
        headerRow.createCell(0).setCellValue("curso_vida");
        headerRow.createCell(1).setCellValue("atendidos_eess");
        headerRow.createCell(2).setCellValue("atendidos_serv");
        headerRow.createCell(3).setCellValue("atenciones_serv");
        for(int i =0;i<labels.size();i++){
            Row row = sheet.createRow(9+i);
            row.createCell(0).setCellValue(labels.get(i));
            row.createCell(1).setCellValue(atendidos_eess.get(i) );
            row.createCell(2).setCellValue(atenciones_Serv.get(i));
            row.createCell(3).setCellValue(atendidos_Serv.get(i));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();

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
