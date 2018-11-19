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
}
