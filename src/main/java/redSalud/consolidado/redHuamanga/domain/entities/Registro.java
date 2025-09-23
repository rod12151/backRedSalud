package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "registros")
@Getter
@Setter
@Builder
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String red;
    private Integer anio;
    private String mes;
    private String microRed;
    private String ipress;
    private String cursoVida;

    private Integer atendidosEess;
    private Integer atendidosServ;
    private Integer atencionesServ;
    private Integer app;
    private Integer aa;
}
