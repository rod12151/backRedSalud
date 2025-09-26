package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_CondicionContrato")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisCondicionContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Condicion;
    @Column(name = "Descripcion_Colegio",nullable = false, length = 30)
    private String descripcionCondicion;

}
