package redSalud.consolidado.redHuamanga.Security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redSalud.consolidado.redHuamanga.util.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "El nombre es obligatorio")
  private String nombre;

  @NotBlank(message = "El username es obligatorio")
  @Email(message = "Email inválido")
  private String username;

  @NotBlank(message = "La contraseña es obligatoria")
  @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
  private String password;
  @NotBlank(message = "La el dni es obligatorio")
    @Size(min = 6, message = "el dni debe tener al 8 caracteres")
    private String dni;
  @NotNull(message = "El rol es importante")
  private Role rol;
}
