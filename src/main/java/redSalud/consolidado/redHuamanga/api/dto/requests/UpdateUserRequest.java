package redSalud.consolidado.redHuamanga.api.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    private String dni;
    private String name;
    private String lastname;
    private String email;
    private Integer idPuesto;

}
