package redSalud.consolidado.redHuamanga.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redSalud.consolidado.redHuamanga.api.dto.requests.CreatePuestoRequest;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.PuestoService;

import java.util.List;

@RestController
@RequestMapping(value = "api/puesto")
@AllArgsConstructor
public class PuestoController {
    private final PuestoService puestoService;
    @PostMapping()
    public ResponseEntity<Puesto> createPuesto(@Valid @RequestBody CreatePuestoRequest request) {
        return ResponseEntity.ok(puestoService.add(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Puesto>  actualizarPuesto(@Valid @PathVariable String id, @RequestBody CreatePuestoRequest puesto){
         return ResponseEntity.ok(puestoService.edit(id,puesto));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Puesto> PuestoById( @PathVariable String id){
        return ResponseEntity.ok(puestoService.findById(id));
    }
    @GetMapping()
    public ResponseEntity<List<Puesto>> Puestos(){
        return ResponseEntity.ok(puestoService.findAll());
    }
    @PutMapping(value = "estado/{id}")
    public ResponseEntity<Void> changeStatusUsuario(@PathVariable String id){
        puestoService.changeStatusById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
