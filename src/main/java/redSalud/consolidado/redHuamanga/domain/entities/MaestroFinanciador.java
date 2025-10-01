package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maestro_financiador")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroFinanciador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFinanciador;

    @Column(name = "descripcion_financiador"
            ,nullable = false,length = 40)
    private String descripcionFinanciador;
}
