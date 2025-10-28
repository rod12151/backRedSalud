package redSalud.consolidado.redHuamanga.Security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
  @NotBlank(message = "El username es obligatorio")
  @Email(message = "username inválido")
  private String username;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;
}
