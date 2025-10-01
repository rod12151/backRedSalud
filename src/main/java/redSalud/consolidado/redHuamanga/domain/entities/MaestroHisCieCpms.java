package redSalud.consolidado.redHuamanga.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Entity
@Table(name = "MAESTRO_HIS_CIE_CPMS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaestroHisCieCpms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Codigo_Item",nullable = false,unique = true, length =8 )
    private String codigoItem;
    @Column(name = "Descripcion_Item",nullable = false,length = 500)
    private String descripcionItem;
    @Column(name = "Fg_Tipo",nullable = false,length = 4)
    private String fgTipo;
    @Column(name = "Descripcion_Tipo_Item",nullable = false,length = 50)
    private String descripcionTipoItem;
    @Column(name = "Fg_Estado",nullable = false,length = 1)
    private String fgEstado;
}
