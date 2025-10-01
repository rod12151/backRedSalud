package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Maestro_His_Registrador")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisRegistrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idRegistrador;
    private String idTipodocumento;
    private String numeroDocumento;
    private String apellidoPaternoRegistrador;
    private String apellidoMaternoRegistrador;
    private String nombreRegistardor;
    private String fechaNacimiento;


}
