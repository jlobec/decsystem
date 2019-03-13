package es.udc.fic.decisionsystem.repository.asamblea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;

@Repository
public interface AsambleaRepository extends JpaRepository<Asamblea, Integer> {

	@Query("SELECT a FROM UsuarioAsamblea ua "
			+ "INNER JOIN Asamblea a ON ua.asamblea = a "
			+ "INNER JOIN Usuario u ON ua.usuario = u "
			+ "WHERE u.idUsuario =  :idUser")
	Page<Asamblea> findByUser(Pageable pageable, @Param("idUser") Long idUser);
	
}
