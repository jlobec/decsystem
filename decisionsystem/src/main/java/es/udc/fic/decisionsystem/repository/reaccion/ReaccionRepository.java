package es.udc.fic.decisionsystem.repository.reaccion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.reaccion.Reaccion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Repository
public interface ReaccionRepository extends JpaRepository<Reaccion, Long> {

	List<Reaccion> findByComentario(Comentario comentario);
	
	Optional<Reaccion> findByComentarioAndUsuario(Comentario comentario, Usuario usuario);
	
	List<Reaccion> findAllByComentarioAndUsuario(Comentario comentario, Usuario usuario);
}
