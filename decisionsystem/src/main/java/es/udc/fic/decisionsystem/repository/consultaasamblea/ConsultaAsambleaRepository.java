package es.udc.fic.decisionsystem.repository.consultaasamblea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consultaasamblea.ConsultaAsamblea;

@Repository
public interface ConsultaAsambleaRepository extends JpaRepository<ConsultaAsamblea, Long> {

}
