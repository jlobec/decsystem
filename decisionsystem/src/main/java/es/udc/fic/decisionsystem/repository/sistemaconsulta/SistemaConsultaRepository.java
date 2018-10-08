package es.udc.fic.decisionsystem.repository.sistemaconsulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;

@Repository
public interface SistemaConsultaRepository extends JpaRepository<SistemaConsulta, Long>{

}
