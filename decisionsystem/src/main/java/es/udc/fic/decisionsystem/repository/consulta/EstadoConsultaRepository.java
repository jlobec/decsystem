package es.udc.fic.decisionsystem.repository.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consulta.EstadoConsulta;

@Repository
public interface EstadoConsultaRepository extends JpaRepository<EstadoConsulta, Integer> {

}
