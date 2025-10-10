package redSalud.consolidado.redHuamanga.Security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redSalud.consolidado.redHuamanga.Security.entity.RefreshToken;
import redSalud.consolidado.redHuamanga.Security.repository.RefreshTokenRepository;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;
import redSalud.consolidado.redHuamanga.domain.repositories.UsuarioRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.startsWith("Bearer ")) {
            return;
        }
        String token = authHeader.replace("Bearer ", "");
        try {
            String username = jwtService.extractUsername(token);
            if (username != null) {
                Optional<Usuario>  usuarioOpt = usuarioRepository.findByUsername(username);
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();
                    revokeAllUserTokens(usuario);
                    SecurityContextHolder.clearContext();
                }

            }
        } catch (Exception e){
            System.err.println("Error durante logout"+e.getMessage());
        }

    }
    @Transactional
    public void revokeAllUserTokens(Usuario usuario) {
        var validUserTokens = refreshTokenRepository.findAllValidTokenByUsuario(usuario.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setRevocado(true);
            token.setExpirado(true);
        });

        refreshTokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    public void revokeSpecificToken(String refreshToken) {
        Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByToken(refreshToken);

        tokenOpt.ifPresent(token -> {
            token.setRevocado(true);
            token.setExpirado(true);
            refreshTokenRepository.save(token);
        });
    }
}
