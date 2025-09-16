package redSalud.consolidado.redHuamanga.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redSalud.consolidado.redHuamanga.api.model.response.graficoResponse;
import redSalud.consolidado.redHuamanga.domain.entities.Registro;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.ExcelService;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.RegistroService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class DataControllers {
    private final RegistroService registroService;
    private final ExcelService excelService;
    @GetMapping
    public List<Registro> getAll() {
        return registroService.getAll();
    }

    @GetMapping("/anio/{anio}")
    public List<Registro> getByAnio(@PathVariable Integer anio) {
        return registroService.getByAnio(anio);
    }

    @GetMapping("/mes/{mes}")
    public List<Registro> getByMes(@PathVariable String mes) {
        return registroService.getByMes(mes);
    }

    @GetMapping("/microred/{microRed}")
    public List<Registro> getByMicroRed(@PathVariable String microRed) {
        return registroService.getByMicroRed(microRed);
    }

    @PostMapping
    public Registro save(@RequestBody Registro registro) {
        return registroService.save(registro);
    }

    @PostMapping("/bulk")
    public List<Registro> saveAll(@RequestBody List<Registro> registros) {
        return registroService.saveAll(registros);
    }
    @PostMapping("/upload")
    public List<Registro> uploadExcel(@RequestParam("file") MultipartFile file) {
        List<Registro> registros = excelService.parseExcel(file);
        return registroService.saveAll(registros);
    }
    @GetMapping("/grafico")
    public graficoResponse getGrafico(
            @RequestParam(required = false) String red,
            @RequestParam(required = false) String anio,
            @RequestParam(required = false) String mes,
            @RequestParam(required = false) String microRed,
            @RequestParam(required = false) String ipress
    ) {

        if (mes != null && mes.isBlank()) mes = null;
        if (Objects.equals(microRed,"TODOS")) microRed = null;
        if (Objects.equals(ipress,"TODOS")) ipress = null;
        Integer anioInt = null;
        if (anio != null && !anio.equalsIgnoreCase("todos") && !anio.isBlank()) {
            anioInt = Integer.valueOf(anio);
        }

        return registroService.getGraficoCursoVida(red, anioInt, mes, microRed, ipress);
    }
}
