package redSalud.consolidado.redHuamanga.api.errors;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsResponse extends BaseErrorResponse {
    private List<Object> errors;
}
