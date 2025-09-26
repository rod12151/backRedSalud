package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Maestro_His_CondicionContrato")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisRegistrador {

    private Integer idRegistrador;
    private String idTipodocumento;
    private String numeroDocumento;
    private String apellidoPaternoRegistrador;
    private String apellidoMaternoRegistrador;
    private String nombreRegistardor;
    private String fechaNacimiento;


}
