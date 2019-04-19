package es.udc.fic.decisionsystem.repository.voto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	Optional<Voto> findByConsultaOpcionAndUsuario(ConsultaOpcion pollOption, Usuario user);
}
