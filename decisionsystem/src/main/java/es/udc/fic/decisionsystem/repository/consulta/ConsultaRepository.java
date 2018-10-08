package es.udc.fic.decisionsystem.repository.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consulta.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
