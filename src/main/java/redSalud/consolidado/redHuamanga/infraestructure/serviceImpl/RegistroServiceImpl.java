package redSalud.consolidado.redHuamanga.infraestructure.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redSalud.consolidado.redHuamanga.api.dto.response.graficoResponse;
import redSalud.consolidado.redHuamanga.domain.entities.Registro;
import redSalud.consolidado.redHuamanga.domain.repositories.RegistroRepository;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.RegistroService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroServiceImpl implements RegistroService {
    private final RegistroRepository registroRepository;

    @Override
    public List<Registro> getAll() {
        return registroRepository.findAll();
    }

    @Override
    public List<Registro> getByAnio(Integer anio) {
        return registroRepository.findByAnio(anio);
    }

    @Override
    public List<Registro> getByMes(String mes) {
        return registroRepository.findByMes(mes);
    }

    @Override
    public List<Registro> getByMicroRed(String microRed) {
        return registroRepository.findByMicroRed(microRed);
    }

    @Override
    public Registro save(Registro registro) {
        return registroRepository.save(registro);

    }

    @Override
    public List<Registro> saveAll(List<Registro> registros) {
        return registroRepository.saveAll(registros);
    }

    @Override
    public graficoResponse getGraficoCursoVida(String red, Integer anio, String mes, String microRed, String ipress) {
        List<Object[]> resultados = registroRepository.graficoCursoVida(red, anio, mes, microRed, ipress);

        List<String> labels = resultados.stream()
                .map(r -> (String) r[0])
                .collect(Collectors.toList());

        List<Integer> values1 = resultados.stream()
                .map(r -> ((Long) r[1]).intValue()) // SUM devuelve Long
                .collect(Collectors.toList());
        List<Integer> values2 = resultados.stream()
                .map(r -> ((Long) r[2]).intValue()) // SUM devuelve Long
                .toList();
        List<Integer> values3 = resultados.stream()
                .map(r -> ((Long) r[3]).intValue()) // SUM devuelve Long
                .toList();

        return new graficoResponse(labels, values1,values2,values3);
    }
}
