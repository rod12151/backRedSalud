package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Paciente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String idPaciente;
    private String idTipoDocumento;
    private String numeroDocumento;
    private String apellidoPaternoPaciente;
    private String apellidoMaternoPaciente;
    private String nombrePaciente;
    private String fechaNacimiento;
    private String genero;
    private Integer idEtnia;
    private String historiaClinica;
    private String fichaFamiliar;
    private String ubigeoNacimiento;
    private String ubigeoReniec;
    private String domicilioReniec;
    private String ubigeoDeclarado;
    private String domicilioDeclarado;
    private String referenciaDomicilio;
    private String idPais;
    private String idEstablecimiento;
    private String fechaAlta;
    private String fechaModificacion;

}
