package es.udc.fic.decisionsystem.service.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;

public interface ConsultaService {

	public PollSummaryResponse getPollById(Long consultaId);

	public Page<PollSummaryResponse> getUserPolls(Pageable pageable, Usuario user);

}
