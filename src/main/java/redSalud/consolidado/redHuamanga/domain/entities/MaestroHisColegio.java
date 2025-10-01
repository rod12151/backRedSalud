package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;

@Entity
@Table(name = "Maestro_His_Colegio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisColegio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="Id_Colegio",unique = true,nullable = false)
    private Integer idColegio;
    @Column(name = "Descripcion_Colegio",nullable = false, length = 100)
    private String descripcionColegio;
}
