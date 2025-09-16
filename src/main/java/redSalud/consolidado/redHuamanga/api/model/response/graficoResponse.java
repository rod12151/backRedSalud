package redSalud.consolidado.redHuamanga.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class graficoResponse {
    private List<String> labels;
    private List<Integer> atendidosEess;
    private List<Integer> atencionesServ;
    private List<Integer> atendidosServ;
}
