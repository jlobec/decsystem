package es.udc.fic.decisionsystem.repository.usuarioasamblea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.usuarioasamblea.UsuarioAsamblea;

@Repository
public interface UsuarioAsambleaRepository extends JpaRepository<UsuarioAsamblea, Long>{

}
