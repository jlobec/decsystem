package es.udc.fic.decisionsystem.repository.comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.comentario.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
