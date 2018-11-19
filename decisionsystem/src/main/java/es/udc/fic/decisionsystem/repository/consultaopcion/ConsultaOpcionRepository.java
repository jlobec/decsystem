package es.udc.fic.decisionsystem.repository.consultaopcion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;

@Repository
public interface ConsultaOpcionRepository extends JpaRepository<ConsultaOpcion, Long> {

	List<ConsultaOpcion> findByConsulta(Consulta c);
}
