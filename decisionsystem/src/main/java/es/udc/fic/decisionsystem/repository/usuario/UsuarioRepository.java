package es.udc.fic.decisionsystem.repository.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByNicknameOrEmail(String username, String email);

	boolean existsByNickname(String username);

	boolean existsByEmail(String email);

}
