package redSalud.consolidado.redHuamanga.domain.entities;
import jakarta.persistence.*;
import lombok.*;
import redSalud.consolidado.redHuamanga.util.enums.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "usuarios")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Role nombre;


    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios=new HashSet<>();
}
