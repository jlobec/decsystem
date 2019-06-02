package es.udc.fic.decisionsystem.repository.notificacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.notificacion.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

	@Query("SELECT n FROM Notificacion n "
			+ "INNER JOIN Usuario u ON n.usuario = u "
			+ "WHERE u.idUsuario = :idUser AND n.vista = false")
	List<Notificacion> findUnSeenByUser(@Param("idUser") Long idUser);
	
	@Query("SELECT n FROM Notificacion n "
			+ "INNER JOIN Usuario u ON n.usuario = u "
			+ "WHERE u.idUsuario = :idUser ")
	List<Notificacion> findAllByUser(@Param("idUser") Long idUser);
	
}
