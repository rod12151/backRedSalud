package redSalud.consolidado.redHuamanga.infraestructure.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redSalud.consolidado.redHuamanga.Security.exception.UsuarioNoExiste;
import redSalud.consolidado.redHuamanga.api.dto.requests.CreateUserRequest;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;
import redSalud.consolidado.redHuamanga.domain.entities.Rol;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;
import redSalud.consolidado.redHuamanga.domain.repositories.PuestoRepository;
import redSalud.consolidado.redHuamanga.domain.repositories.RolRepository;
import redSalud.consolidado.redHuamanga.domain.repositories.UsuarioRepository;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.UsuarioService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final PuestoRepository puestoRepository;
    private final RolRepository rolRepository;
    @Override
    public Usuario crearUsuario(CreateUserRequest request) {
        Puesto puesto = puestoRepository.findById(request.getIdPuesto()).orElseThrow();
        Rol rol = rolRepository.findById(request.getIdRole()).orElseThrow();

        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        Usuario usuario = Usuario.builder()
                .dni(request.getDni())
                .name(request.getName())
                .lastName(request.getLastName())
                .username(request.getDni()+"@redhuamanga.pe")
                .password(passwordEncoder.encode((request.getDni())))
                .email(request.getEmail())
                .activo(true)
                .fechaCreacion(LocalDateTime.now())
                .roles(roles)
                .puesto(puesto)
                .build();

        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Usuario editarUsuario(String id, CreateUserRequest request) {
        Usuario userActual= usuarioRepository.findById(Long.valueOf(id)).orElseThrow(()-> new UsuarioNoExiste(request.getDni()));
        Puesto puesto = puestoRepository.findById(request.getIdPuesto()).orElseThrow(()-> new UsuarioNoExiste(request.getIdPuesto().toString()));
        Rol rol = rolRepository.findById(request.getIdRole()).orElseThrow(()-> new UsuarioNoExiste(request.getIdRole().toString()));
        userActual.setDni(request.getDni());
        userActual.setName(request.getName());
        userActual.setLastName(request.getLastName());
        userActual.setEmail(request.getEmail());
        if(!puesto.equals(userActual.getPuesto())){
            userActual.setPuesto(puesto);
        }
        userActual.getRoles().add(rol);
        usuarioRepository.save(userActual);

        return userActual;
    }


    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
