package es.udc.fic.decisionsystem.repository.usuario;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNicknameOrEmail(String username, String email);

	boolean existsByNickname(String username);

	boolean existsByEmail(String email);

	@Query("SELECT u FROM UsuarioAsamblea ua "
			+ "INNER JOIN Usuario u ON ua.usuario = u "
			+ "INNER JOIN Asamblea a ON ua.asamblea = a "
			+ "WHERE a.idAsamblea = :idAsamblea")
	Page<Usuario> findByIdAsamblea(Pageable pageable, @Param("idAsamblea") Integer idAsamblea);

}
