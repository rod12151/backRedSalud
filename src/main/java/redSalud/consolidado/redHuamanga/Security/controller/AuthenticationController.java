package redSalud.consolidado.redHuamanga.Security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redSalud.consolidado.redHuamanga.Security.dto.AuthenticationRequest;
import redSalud.consolidado.redHuamanga.Security.dto.AuthenticationResponse;
import redSalud.consolidado.redHuamanga.Security.dto.RefreshTokenRequest;
import redSalud.consolidado.redHuamanga.Security.dto.RegisterRequest;
import redSalud.consolidado.redHuamanga.Security.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

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
}
