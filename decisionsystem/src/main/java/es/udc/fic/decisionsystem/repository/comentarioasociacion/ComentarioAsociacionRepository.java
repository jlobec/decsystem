package es.udc.fic.decisionsystem.repository.comentarioasociacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.comentarioasociacion.ComentarioAsociacion;

@Repository
public interface ComentarioAsociacionRepository extends JpaRepository<ComentarioAsociacion, Long>{

}
