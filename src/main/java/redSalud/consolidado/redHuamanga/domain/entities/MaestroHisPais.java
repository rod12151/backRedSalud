package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Maestro_His_Pais")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisPais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,length = 3)
    private String idPais;
    @Column(nullable = false,length = (50))
    private String descripcionPais;

}
