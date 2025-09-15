package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import redSalud.consolidado.redHuamanga.domain.entities.AccesoRecurso;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;
import redSalud.consolidado.redHuamanga.domain.entities.Rol;

import java.util.Set;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length =8 )
    private String dni;

    @Column(name = "nombre",nullable = false,length = 20)
    private String name;

    @Column(name = "apellidos",nullable = false,length = 40)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Encriptada (BCrypt)

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "puesto_id", nullable = false)
    private Puesto puesto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<AccesoRecurso> accesos;
}
