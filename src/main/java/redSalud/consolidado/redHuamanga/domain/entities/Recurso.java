package redSalud.consolidado.redHuamanga.domain.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "recursos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String url; // Enlace al recurso

    @Column(nullable = false)
    private Boolean esSensitivo;

    @OneToMany(mappedBy = "recurso")
    private Set<AccesoRecurso> accesos;
}

