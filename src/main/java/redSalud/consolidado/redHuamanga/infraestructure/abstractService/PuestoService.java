package redSalud.consolidado.redHuamanga.infraestructure.abstractService;

import redSalud.consolidado.redHuamanga.api.dto.requests.CreatePuestoRequest;
import redSalud.consolidado.redHuamanga.domain.entities.Puesto;

import java.util.List;

public interface PuestoService {
    Puesto findById(String id);
    List<Puesto> findAll();
    Puesto edit(String idPuesto, CreatePuestoRequest entity);
    Puesto add(CreatePuestoRequest entity);
    void changeStatusById(String id);
}
