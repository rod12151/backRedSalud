package redSalud.consolidado.redHuamanga.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8,max = 8,message = "el DNI debe tener 8 caracteres")
    private String dni;
    @NotBlank(message = "El Nombre es obligatorio")
    private String name;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    private String email;

    @NotNull(message = "debe ingresar un puesto")

    private Integer idPuesto;
    @NotNull(message = "debe ingresar un rol")
    private Integer idRole;

}
