package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Maestro_His_Nuevo_Archivo_Plano")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisNuevoArchivoPlano {
    @Id
    private Integer id;
    private String idCita;
    private String anio;
    private String mes;
    private String dia;
    private String fechaAtencion;
    private String lote;
    private Integer numPag;
    private Integer numReg;
    private Integer idUps;
    private String idEstablecimiento;
    private String idPaciente;
    private String idPersona;
    private String idPersonal;
    private String idRegistrador;
    private String idFinanciador;
    private String idCondicionestablecimiento;
    private String idCondicionServicio;
    private String edadReg;
    private String tipoEdad;
    private String anioActualPaciente;
    private String mesActualPaciente;;
    private String diaActualPaciente;
    private String idTurno;
    private String codigoItem;
    private String tipoDiagnostico;
    private String valorLab;
    private String idCorrelativo;
    private Double peso;
    private Double talla;
    private Double hemoglobina;
    private Integer pac;
    private Integer pc;
    private Integer idOtraCondicion;
    private LocalDateTime fechaUltimaRegla;
    private LocalDateTime fechaSolicitudHb;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModificacion;
    private String idPais;



}
