package redSalud.consolidado.redHuamanga.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePuestoRequest {
    @NotBlank(message = "El código es obligatorio")
    @Size(min = 10,max = 10,message = "el codigo debe tener 10 caracteres")
    private String codigo;
    @NotBlank(message = "El Nombre es obligatorio")
    private String nombre;
}
