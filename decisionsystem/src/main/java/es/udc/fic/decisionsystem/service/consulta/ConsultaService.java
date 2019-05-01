package es.udc.fic.decisionsystem.service.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;

/**
 * The Interface ConsultaService.
 */
public interface ConsultaService {

	/**
	 * Gets the poll by id.
	 *
	 * @param consultaId the consulta id
	 * @param user       the user
	 * @return the poll by id
	 */
	public PollSummaryResponse getPollById(Long consultaId, Usuario user);

	/**
	 * Gets the user polls.
	 *
	 * @param pageable the pageable
	 * @param user     the user
	 * @return the user polls
	 */
	public Page<PollSummaryResponse> getUserPolls(Pageable pageable, Usuario user);

}