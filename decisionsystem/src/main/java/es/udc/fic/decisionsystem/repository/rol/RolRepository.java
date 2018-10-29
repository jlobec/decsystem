package es.udc.fic.decisionsystem.repository.rol;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.rol.RoleName;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol> findByName(RoleName roleName);
	
}