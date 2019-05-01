package es.udc.fic.decisionsystem.repository.comentario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.consulta.Consulta;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

	Page<Comentario> findByConsulta(Pageable pageable, Consulta consulta);
}
