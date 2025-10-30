package redSalud.consolidado.redHuamanga.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import redSalud.consolidado.redHuamanga.domain.entities.AccesoRecurso;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;
import redSalud.consolidado.redHuamanga.domain.entities.Rol;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"roles", "accesos", "puesto"}) // Excluye las relaciones
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length =8 )
    private String dni;

    @Column(name = "nombre",nullable = false,length = 20)
    private String name;

    @Column(name = "apellidos",nullable = true,length = 40)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Encriptada (BCrypt)

    @Column(nullable = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "puesto_id", nullable = true)
    private Puesto puesto;
    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;





    @PreUpdate
    protected void onUpdate() {
    fechaActualizacion = LocalDateTime.now();
  }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles=new HashSet<>();

    @PrePersist
    protected void onCreate() {
    fechaCreacion = LocalDateTime.now();
    fechaActualizacion = LocalDateTime.now();


  }
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<AccesoRecurso> accesos;

  // Implementación de UserDetails
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return getRoles()
              .stream()
              .map(r -> new SimpleGrantedAuthority(r.getNombre().toString()))
              .collect(Collectors.toList());
    }
    @JsonIgnore
    @Override
    public String getPassword() {
    return password;
  }


    @Override
    public String getUsername() {
    return username;
  }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
    return true;
  }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
    return true;
  }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
    return true;
  }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
    return activo;
  }


}
