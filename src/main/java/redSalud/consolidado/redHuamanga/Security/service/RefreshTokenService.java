package redSalud.consolidado.redHuamanga.Security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redSalud.consolidado.redHuamanga.Security.entity.RefreshToken;
import redSalud.consolidado.redHuamanga.Security.repository.RefreshTokenRepository;
import redSalud.consolidado.redHuamanga.domain.entities.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(Usuario usuario, String token) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .usuario(usuario)
                .expirado(false)
                .revocado(false)
                .fechaCreacion(LocalDateTime.now())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void revokeAllUserTokens(Usuario usuario) {
        List<RefreshToken> validTokens = refreshTokenRepository
                .findAllValidTokenByUsuario(usuario.getId());

        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(token -> {
            token.setRevocado(true);
        });

        refreshTokenRepository.saveAll(validTokens);
    }

    public boolean isRefreshTokenValid(Usuario usuario, String token) {
        return refreshTokenRepository
                .findByTokenAndUsuarioId(token, usuario.getId())
                .map(rt -> !rt.isRevocado() && !rt.isExpirado())
                .orElse(false);
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(rt -> {
                    rt.setRevocado(true);
                    refreshTokenRepository.save(rt);
                });
    }
}
