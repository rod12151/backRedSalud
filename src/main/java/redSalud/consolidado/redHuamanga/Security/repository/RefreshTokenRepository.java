package redSalud.consolidado.redHuamanga.Security.repository;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import redSalud.consolidado.redHuamanga.Security.entity.RefreshToken;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("""
        SELECT rt FROM RefreshToken rt
        WHERE rt.usuario.id = :usuarioId
        AND rt.expirado = false
        AND rt.revocado = false
    """)
    List<RefreshToken> findAllValidTokenByUsuario(Long usuarioId);

    Optional<RefreshToken> findByToken(String token);

    @Query("""
        SELECT rt FROM RefreshToken rt
        WHERE rt.token = :token
        AND rt.usuario.id = :usuarioId
    """)
    Optional<RefreshToken> findByTokenAndUsuarioId(String token, Long usuarioId);
}
