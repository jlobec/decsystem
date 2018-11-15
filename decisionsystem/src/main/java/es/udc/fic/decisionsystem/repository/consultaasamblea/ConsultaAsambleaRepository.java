package es.udc.fic.decisionsystem.repository.consultaasamblea;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaasamblea.ConsultaAsamblea;

@Repository
public interface ConsultaAsambleaRepository extends JpaRepository<ConsultaAsamblea, Long> {
	
	Optional<ConsultaAsamblea> findByConsultaAndAsamblea(Consulta consulta, Asamblea asamblea);
	
}
