package redSalud.consolidado.redHuamanga.domain.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private String nombre;


    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;
}
