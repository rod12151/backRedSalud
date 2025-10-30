package redSalud.consolidado.redHuamanga.infraestructure.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redSalud.consolidado.redHuamanga.Security.exception.UsuarioNoExiste;
import redSalud.consolidado.redHuamanga.api.dto.requests.CreatePuestoRequest;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;
import redSalud.consolidado.redHuamanga.domain.repositories.PuestoRepository;
import redSalud.consolidado.redHuamanga.infraestructure.abstractService.PuestoService;
import redSalud.consolidado.redHuamanga.util.exceptions.EntityNoExiste;

import java.util.List;

@Service
@AllArgsConstructor
public class PuestoServiceImpl implements PuestoService {
    private final PuestoRepository puestoRepository;
    private final String entidad="PUESTO";

    @Override
    public Puesto findById(String id) {
        return puestoRepository.findById(Integer.valueOf(id)).orElseThrow(()->new EntityNoExiste(id,entidad));
    }

    @Override
    public List<Puesto> findAll() {
        return  puestoRepository.findAll();
    }
    @Transactional
    @Override
    public Puesto edit(String idPuesto, CreatePuestoRequest entity) {
        Puesto puestoActual =puestoRepository.findById(Integer.valueOf(idPuesto)).orElseThrow(()->new EntityNoExiste(idPuesto,entidad));;
        puestoActual.setCodigo(entity.getCodigo());
        puestoActual.setNombre(entity.getName());
         puestoRepository.save(puestoActual);
         return puestoActual;
    }
    @Transactional
    @Override
    public Puesto add( CreatePuestoRequest entity) {
        Puesto puesto = Puesto.builder()
                .codigo(entity.getCodigo())
                .nombre(entity.getName())
                .activo(true)
                .build();
        puestoRepository.save(puesto);
        return puesto;
    }

    @Transactional
    @Override
    public void changeStatusById(String id) {
        Puesto puesto=puestoRepository.findById(Integer.valueOf(id)).orElseThrow(()->new EntityNoExiste(id,entidad));
        boolean status = puesto.getActivo();

        puesto.setActivo(!status);
        puestoRepository.save(puesto);

    }
}
