package redSalud.consolidado.redHuamanga.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.ExcelService;

@RestController
@RequestMapping("/api/reportes")

@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;
    @GetMapping("/excel")
    public ResponseEntity<byte[]> getGrafico(
            @RequestParam(required = false) String red,
            @RequestParam(required = false) String anio,
            @RequestParam(required = false) String mes,
            @RequestParam(required = false) String microRed,
            @RequestParam(required = false) String ipress
    ) throws  Exception{
       byte[]excelBytes =  excelService.generarExcel(red,anio,mes,microRed,ipress);
       return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=grafico.xlsx")
               .contentType(MediaType.APPLICATION_OCTET_STREAM)
               .body(excelBytes);
    }
}
