package redSalud.consolidado.redHuamanga.Security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
  @NotBlank(message = "El refresh token es obligatorio")
  private String refreshToken;
}
