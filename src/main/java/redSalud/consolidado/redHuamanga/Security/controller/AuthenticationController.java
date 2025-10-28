package redSalud.consolidado.redHuamanga.Security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redSalud.consolidado.redHuamanga.Security.dto.*;
import redSalud.consolidado.redHuamanga.Security.service.AuthenticationService;
import redSalud.consolidado.redHuamanga.Security.service.LogoutService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

    private final LogoutService logoutService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
          @Valid @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @Valid @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponse> refreshToken(
          @Valid @RequestBody RefreshTokenRequest request
  ) {
    return ResponseEntity.ok(authenticationService.refreshToken(request));
  }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @Valid @RequestBody LogoutRequest request
    ) {
        System.out.println("el token que ingresa es"+request.getRefreshToken());
        // Revocar el refresh token específico si se proporciona
         logoutService.revokeSpecificToken(request.getRefreshToken());


        return ResponseEntity.ok(Map.of(
                "message", "Logout exitoso",
                "status", "success"
        ));
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Map<String, String>> logoutAll() {
        // Este endpoint revoca todos los tokens del usuario actual
        // La lógica se maneja en el LogoutHandler configurado en SecurityConfig
        return ResponseEntity.ok(Map.of(
                "message", "Sesiones cerradas en todos los dispositivos",
                "status", "success"
        ));
    }
}
