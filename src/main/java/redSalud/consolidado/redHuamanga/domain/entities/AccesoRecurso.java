package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accesos_recursos")
public class AccesoRecurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    @Column(nullable = false)
    private LocalDateTime fechaAcceso;
}