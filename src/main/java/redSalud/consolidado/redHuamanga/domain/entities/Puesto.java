package redSalud.consolidado.redHuamanga.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "puestos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Puesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length =10 )
    private String codigo;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean activo = true;

    @JsonIgnore
    @OneToMany(mappedBy = "puesto")
    private Set<Usuario> usuarios;
}

