package es.udc.fic.decisionsystem.repository.consultaopcion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;

@Repository
public interface ConsultaOpcionRepository extends JpaRepository<ConsultaOpcion, Long>{

}
