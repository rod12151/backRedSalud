package redSalud.consolidado.redHuamanga.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import redSalud.consolidado.redHuamanga.domain.entities.Registro;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro,Long> {
    List<Registro> findByAnio(Integer anio);
    List<Registro> findByMes(String mes);
    List<Registro> findByMicroRed(String microRed);

    @Query(value = "SELECT r.cursoVida, SUM(r.atendidosEess), SUM(r.atendidosServ),SUM(r.atencionesServ) " +
            "FROM Registro r WHERE (:red IS NULL OR r.red = :red)" +
            " AND (:anio IS NULL OR r.anio = :anio) AND (:mes IS NULL OR r.mes = :mes) " +
            "AND (:microRed IS NULL OR r.microRed = :microRed) " +
            "AND (:ipress IS NULL OR r.ipress = :ipress) GROUP BY r.cursoVida ORDER BY r.cursoVida")
    List<Object[]> graficoCursoVida(String red, Integer anio, String mes, String microRed, String ipress);

}
