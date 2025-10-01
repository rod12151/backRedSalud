package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Tipo_Doc")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisTipoDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoDocumento;
    private String abrevTipoDoc;
    private String descripcionTipoDocumento;
}
