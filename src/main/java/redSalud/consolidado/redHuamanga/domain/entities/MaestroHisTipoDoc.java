package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_CondicionContrato")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisTipoDoc {
    private Integer idTipoDocumento;
    private String abrevTipoDoc;
    private String descripcionTipoDocumento;
}
