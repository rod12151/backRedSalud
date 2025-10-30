package redSalud.consolidado.redHuamanga.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redSalud.consolidado.redHuamanga.api.dto.requests.CreateUserRequest;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    public final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<Usuario> usuario(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(usuarioService.crearUsuario(request));

    }
    @GetMapping()
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@Valid @PathVariable String id, @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(usuarioService.editarUsuario(id,request));
    }
    @PutMapping(value = "estado/{id}")
    public ResponseEntity<Void> changeStatusUsuario(@PathVariable String id){
        usuarioService.changeEstatusById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
