package es.udc.fic.decisionsystem.repository.usuarioasamblea;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.usuarioasamblea.UsuarioAsamblea;

@Repository
public interface UsuarioAsambleaRepository extends JpaRepository<UsuarioAsamblea, Long>{

	Optional<UsuarioAsamblea> findByUsuarioAndAsamblea(Usuario usuario, Asamblea asamblea);
}
