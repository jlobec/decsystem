package es.udc.fic.decisionsystem.repository.reaccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.reaccion.Reaccion;

@Repository
public interface ReaccionRepository extends JpaRepository<Reaccion, Long>{

}
