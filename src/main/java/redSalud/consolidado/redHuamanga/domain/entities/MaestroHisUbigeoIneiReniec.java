package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Ubigeo_Inei_Reniec")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisUbigeoIneiReniec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String idUbigeoInei;
    private String idUbigeoReniec;
    private String departamento;
    private String provincia;
    private String distrito;
    private String codDepInei;
    private String codProvInei;
    private String codDistInei;
    private String codDepReniec;
    private String codProvReniec;
    private String codDistReniec;

}
