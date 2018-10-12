package es.udc.fic.decisionsystem.repository.tiporeaccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.tiporeaccion.TipoReaccion;

@Repository
public interface TipoReaccionRepository extends JpaRepository<TipoReaccion, Integer>{

}
