package es.udc.fic.decisionsystem.service.consulta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.udc.fic.decisionsystem.exception.BadRequestException;
import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsultaEnum;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.consulta.PollOptionResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollOptionVotedResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.payload.pollsystem.PollSystemResponse;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaopcion.ConsultaOpcionRepository;
import es.udc.fic.decisionsystem.repository.voto.VotoRepository;

@Service
public class ConsultaServiceImpl implements ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private ConsultaOpcionRepository consultaOpcionRepository;

	@Autowired
	private VotoRepository votoRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PollSummaryResponse getPollById(Long consultaId, Usuario user) {
		return consultaRepository.findById(consultaId).map(poll -> {
			return buildPollSummaryResponse(poll, user);
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<PollSummaryResponse> getUserPolls(Pageable pageable, Usuario user) {
		return consultaRepository.findByUser(pageable, user.getIdUsuario()).map(poll -> {
			return buildPollSummaryResponse(poll, user);
		});
	}

	private PollSummaryResponse buildPollSummaryResponse(Consulta poll, Usuario user) {

		// Get options
		List<ConsultaOpcion> options = consultaOpcionRepository.findByConsulta(poll);

		// Check for voted options
		List<Voto> optionVotes = new ArrayList<>();
		for (ConsultaOpcion pollOption : options) {
			Optional<Voto> optionalVote = votoRepository.findByConsultaOpcionAndUsuario(pollOption, user);
			if (optionalVote.isPresent()) {
				optionVotes.add(optionalVote.get());
			}
		}

		// Build response
		List<PollOptionResponse> pollOptions = options.stream().map(opt -> {
			PollOptionResponse option = new PollOptionResponse();
			option.setPollOptionId(opt.getIdConsultaOpcion());
			option.setName(opt.getNombre());
			option.setDescription(opt.getDescripcion());

			PollOptionVotedResponse votedOption = new PollOptionVotedResponse();
			votedOption.setVoted(false);
			votedOption.setPreferenceValue(0);
			votedOption.setMotivation("");
			for (Voto vote : optionVotes) {
				if (opt.getIdConsultaOpcion().equals(vote.getConsultaOpcion().getIdConsultaOpcion())) {
					votedOption.setVoted(true);
					votedOption.setPreferenceValue(0); // field non existent yet
					votedOption.setMotivation(vote.getMotivacion());
					break;
				}
			}

			option.setUserVote(votedOption);

			return option;
		}).collect(Collectors.toList());

		// Check if the poll can be considered voted by user
		// It will depend on poll system
		// so this block must be placed in a separate service by pollsystem
		SistemaConsultaEnum pollSystemEnum = SistemaConsultaEnum.getByName(poll.getSistemaConsulta().getNombre());
		boolean votedByUser = false;
		switch (pollSystemEnum) {
		case SINGLE_OPTION:
		case MULTIPLE_OPTION:
			for (PollOptionResponse pollOption : pollOptions) {
				if (pollOption.getUserVote().isVoted()) {
					votedByUser = true;
					break;
				}
			}
			break;
		case SCORE_VOTE:
			votedByUser = false;
			break;
		default:
			throw new BadRequestException("Poll system not found");
		}

		PollSystemResponse pollSystem = new PollSystemResponse();
		pollSystem.setPollTypeId(poll.getSistemaConsulta().getIdSistemaConsulta());
		pollSystem.setName(poll.getSistemaConsulta().getNombre());
		pollSystem.setDescription(poll.getSistemaConsulta().getDescripcion());

		PollSummaryResponse pollSummary = new PollSummaryResponse();
		pollSummary.setPollId(poll.getIdConsulta());
		pollSummary.setTitle(poll.getTitulo());
		pollSummary.setDescription(poll.getDescripcion());
		pollSummary.setStartsAt(poll.getFechaHoraInicio().getTime());
		pollSummary.setEndsAt(poll.getFechaHoraFin().getTime());
		pollSummary.setVotedByUser(votedByUser);
		pollSummary.setPollSystem(pollSystem);
		pollSummary.setPollOptions(pollOptions);

		return pollSummary;
	}

}
