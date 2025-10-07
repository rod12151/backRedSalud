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
import redSalud.consolidado.redHuamanga.domain.entities.Rol;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;
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

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Validar que el usuario no exista
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        Rol rol = rolRepository.findByNombre(request.getRol()).orElseThrow(()-> new RuntimeException("El rol no existe"));


        // Crear nuevo usuario
        Usuario usuario = Usuario.builder()
                .name(request.getNombre())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .build();

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
                .expiresIn(3600L) // 1 hora en segundos
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
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
                .expiresIn(3600L)
                .build();
    }

    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // Extraer email del token
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new RuntimeException("Token inválido");
        }

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar refresh token
        if (!jwtService.isTokenValid(refreshToken, usuario)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        // Validar que el refresh token existe y está activo
        if (!refreshTokenService.isRefreshTokenValid(usuario, refreshToken)) {
            throw new RuntimeException("Refresh token revocado o inválido");
        }

        // Generar nuevo access token
        String accessToken = jwtService.generateAccessToken(usuario);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();
    }



}
