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
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;
import redSalud.consolidado.redHuamanga.domain.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Validar que el usuario no exista
        if (usuarioRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = Usuario.builder()
                .name(request.getNombre())
                .username(request.getEmail())
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
        // Autenticar usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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
