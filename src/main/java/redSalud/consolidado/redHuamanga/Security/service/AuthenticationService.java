package redSalud.consolidado.redHuamanga.Security.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redSalud.consolidado.redHuamanga.Security.dto.AuthenticationRequest;
import redSalud.consolidado.redHuamanga.Security.dto.AuthenticationResponse;
import redSalud.consolidado.redHuamanga.Security.dto.RefreshTokenRequest;
import redSalud.consolidado.redHuamanga.Security.dto.RegisterRequest;
import redSalud.consolidado.redHuamanga.Security.exception.InvalidTokenFormatException;
import redSalud.consolidado.redHuamanga.Security.exception.UsuarioDuplicado;
import redSalud.consolidado.redHuamanga.Security.exception.UsuarioNoExiste;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;
import redSalud.consolidado.redHuamanga.domain.entities.Rol;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;
import redSalud.consolidado.redHuamanga.domain.repositories.PuestoRepository;
import redSalud.consolidado.redHuamanga.domain.repositories.RolRepository;
import redSalud.consolidado.redHuamanga.domain.repositories.UsuarioRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;
  private final RolRepository rolRepository;
  private final PuestoRepository puestoRepository;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Validar que el usuario no exista
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsuarioDuplicado(request.getUsername());
        }
       Rol rol = rolRepository.findByNombre(request.getRol()).orElseThrow(()-> new RuntimeException("El rol no existe"));
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        Puesto puesto = puestoRepository.findById(1).orElseThrow();
        // Crear nuevo usuario
        Usuario usuario = Usuario.builder()
                .name(request.getNombre())
                .dni(request.getDni())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .build();

        usuario.setRoles(roles);
        usuario.setPuesto(puesto);

        usuarioRepository.save(usuario);


        // Generar tokens
        String accessToken = jwtService.generateAccessToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);

        // Guardar refresh token
        refreshTokenService.saveRefreshToken(usuario, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .username(usuario.getUsername())// 1 hora en segundos
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsuarioNoExiste(request.getUsername()));
              // Autenticar usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Buscar usuario


        // Generar tokens
        String accessToken = jwtService.generateAccessToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);

        // Revocar tokens anteriores y guardar nuevo
        refreshTokenService.revokeAllUserTokens(usuario);
        refreshTokenService.saveRefreshToken(usuario, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .username(usuario.getName())
                .expiresIn(3600L)
                .build();
    }

    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new InvalidTokenFormatException("El token de actualización es obligatorio");
        }
        if (refreshToken.split("\\.").length != 3) {
            throw new InvalidTokenFormatException("Formato de token inválido");
        }


        // Extraer email del token
        String username ;

        try {
            //  Intentar extraer el usuario del token
            username = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            // Si el token no se puede decodificar o no es válido
            throw new InvalidTokenFormatException("Token malformado o inválido");
        }

        // Verificar que el token contenga un usuario válido
        if (username == null || username.isEmpty()) {
            throw new InvalidTokenFormatException("Token inválido: no contiene usuario");
        }

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenFormatException("Usuario no encontrado"));

        // Validar refresh token
        if (!jwtService.isTokenValid(refreshToken, usuario)) {
            throw new InvalidTokenFormatException("Token inválido o expirado ");
        }

        // Validar que el refresh token existe y está activo
        if (!refreshTokenService.isRefreshTokenValid(usuario, refreshToken)) {
            throw new InvalidTokenFormatException("Refresh token revocado o inválido");
        }

        // Generar nuevo access token
        String accessToken = jwtService.generateAccessToken(usuario);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .username(username)

                .build();
    }



}
