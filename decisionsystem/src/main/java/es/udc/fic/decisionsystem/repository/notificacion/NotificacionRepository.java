package es.udc.fic.decisionsystem.repository.notificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.notificacion.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

}
