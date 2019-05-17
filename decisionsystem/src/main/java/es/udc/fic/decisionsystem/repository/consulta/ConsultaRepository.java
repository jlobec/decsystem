package es.udc.fic.decisionsystem.repository.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consulta.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	@Query("SELECT c FROM ConsultaAsamblea ca "
			+ "INNER JOIN Consulta c ON ca.consulta = c "
			+ "INNER JOIN Asamblea a ON ca.asamblea = a "
			+ "WHERE a.idAsamblea = :idAsamblea")
	Page<Consulta> findByIdAsamblea(Pageable pageable, @Param("idAsamblea") Integer idAsamblea);
	
	
	
	@Query("SELECT c FROM UsuarioAsamblea ua "
			+ "INNER JOIN Asamblea a ON ua.asamblea = a "
			+ "INNER JOIN Usuario u ON ua.usuario = u "
			+ "INNER JOIN ConsultaAsamblea ca ON ca.asamblea = a "
			+ "INNER JOIN Consulta c ON ca.consulta = c "
			+ "WHERE u.idUsuario =  :idUser")
	Page<Consulta> findByUser(Pageable pageable, @Param("idUser") Long idUser);
	
	@Query("SELECT c FROM UsuarioAsamblea ua "
			+ "INNER JOIN Asamblea a ON ua.asamblea = a "
			+ "INNER JOIN Usuario u ON ua.usuario = u "
			+ "INNER JOIN ConsultaAsamblea ca ON ca.asamblea = a "
			+ "INNER JOIN Consulta c ON ca.consulta = c "
			+ "INNER JOIN SistemaConsulta sc ON c.sistemaConsulta = sc "
			+ "WHERE u.idUsuario =  :idUser AND sc.idSistemaConsulta = :pollTypeId ")
	Page<Consulta> findByUserAndPollType(Pageable pageable, @Param("idUser") Long idUser, @Param("pollTypeId") Integer pollTypeId);
	
	@Query("SELECT c FROM UsuarioAsamblea ua "
			+ "INNER JOIN Asamblea a ON ua.asamblea = a "
			+ "INNER JOIN Usuario u ON ua.usuario = u "
			+ "INNER JOIN ConsultaAsamblea ca ON ca.asamblea = a "
			+ "INNER JOIN Consulta c ON ca.consulta = c "
			+ "INNER JOIN EstadoConsulta ec ON c.estadoConsulta = ec "
			+ "WHERE u.idUsuario =  :idUser AND ec.idEstadoConsulta = :pollStatusId ")
	Page<Consulta> findByUserAndPollStatus(Pageable pageable, @Param("idUser") Long idUser, 
			@Param("pollStatusId") Integer pollStatusId);
	
	@Query("SELECT c FROM UsuarioAsamblea ua "
			+ "INNER JOIN Asamblea a ON ua.asamblea = a "
			+ "INNER JOIN Usuario u ON ua.usuario = u "
			+ "INNER JOIN ConsultaAsamblea ca ON ca.asamblea = a "
			+ "INNER JOIN Consulta c ON ca.consulta = c "
			+ "INNER JOIN SistemaConsulta sc ON c.sistemaConsulta = sc "
			+ "INNER JOIN EstadoConsulta ec ON c.estadoConsulta = ec "
			+ "WHERE u.idUsuario =  :idUser AND sc.idSistemaConsulta = :pollTypeId AND ec.idEstadoConsulta = :pollStatusId ")
	Page<Consulta> findByUserAndPollTypeAndPollStatus(Pageable pageable, @Param("idUser") Long idUser, 
			@Param("pollTypeId") Integer pollTypeId,  
			@Param("pollStatusId") Integer pollStatusId);

}
