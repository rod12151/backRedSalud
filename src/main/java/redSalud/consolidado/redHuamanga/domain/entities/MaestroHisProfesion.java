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
public class MaestroHisProfesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idProfesion;
    @Column(nullable = false,length = 3)
    private String idColegio;
    @Column(nullable = false,length = 100,unique = true)
    private String descripcionProfesion;
}
