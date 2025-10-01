package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Personal")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String idPersonal;
    private String idTipoDocumento;
    private String numeroDocumento;
    private String apellidoPaternoPersonal;
    private String apellidoMaternoPersonal;
    private String nombrePersonal;
    private String fechaNacimiento;
    private String idCondicion;
    private String idProfesion;
    private String idColegio;
    private String numeroColegiatura;
    private String idEstablecimiento;
    private String fechaAlta;
    private String fechaBaja;


}
