package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Etnia")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisEtnia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Id_Etnia",nullable = false,length = 3,unique = true)
    private String idEtnia;
    @Column(name = "Descripcion_Etnia",nullable = false,length = 30)
    private String DescripcionEtnia;

}
