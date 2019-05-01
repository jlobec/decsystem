package es.udc.fic.decisionsystem.repository.voto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	Optional<Voto> findByConsultaOpcionAndUsuario(ConsultaOpcion pollOption, Usuario user);
	
	@Query("SELECT v FROM Voto v "
			+ "INNER JOIN ConsultaOpcion co ON v.consultaOpcion = co "
			+ "INNER JOIN Consulta c ON co.consulta = c "
			+ "WHERE c.idConsulta =  :pollId ")
	List<Voto> findByConsulta(@Param("pollId") Long pollId);
}
