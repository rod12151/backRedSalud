package redSalud.consolidado.redHuamanga.api.errors;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends BaseErrorResponse {
    private String message;
}
