package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Establecimiento")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisEstablecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Id_Establecimiento",nullable = false,length = 8,unique = true)
    private String idestablecimiento;
    @Column(name = "Nombre_Establecimiento",nullable = false,length = 50)
    private String nombreEstablecimiento;


}
