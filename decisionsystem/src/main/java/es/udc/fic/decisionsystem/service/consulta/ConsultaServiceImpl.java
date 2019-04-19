package es.udc.fic.decisionsystem.service.consulta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
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
		return consultaRepository.findById(consultaId).map(consulta -> {
			PollSummaryResponse response = new PollSummaryResponse();

			List<ConsultaOpcion> options = consultaOpcionRepository.findByConsulta(consulta);

			// Check for voted options
			List<Voto> optionVotes = new ArrayList<>();
			for (ConsultaOpcion pollOption : options) {
				Optional<Voto> optionalVote = votoRepository.findByConsultaOpcionAndUsuario(pollOption, user);
				if (optionalVote.isPresent()) {
					optionVotes.add(optionalVote.get());
				}
			}

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
			boolean votedByUser = false;
			for (PollOptionResponse pollOption : pollOptions) {
				if (pollOption.getUserVote().isVoted()) {
					votedByUser = true;
					break;
				}
			}

			PollSystemResponse pollSystem = new PollSystemResponse();
			pollSystem.setPollTypeId(consulta.getSistemaConsulta().getIdSistemaConsulta());
			pollSystem.setName(consulta.getSistemaConsulta().getNombre());
			pollSystem.setDescription(consulta.getSistemaConsulta().getDescripcion());

			response.setPollId(consulta.getIdConsulta());
			response.setTitle(consulta.getTitulo());
			response.setDescription(consulta.getDescripcion());
			response.setStartsAt(consulta.getFechaHoraInicio().getTime());
			response.setEndsAt(consulta.getFechaHoraFin().getTime());
			response.setVotedByUser(votedByUser);
			response.setPollSystem(pollSystem);
			response.setPollOptions(pollOptions);

			return response;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<PollSummaryResponse> getUserPolls(Pageable pageable, Usuario user) {

		return consultaRepository.findByUser(pageable, user.getIdUsuario()).map(poll -> {

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
			boolean votedByUser = false;
			for (PollOptionResponse pollOption : pollOptions) {
				if (pollOption.getUserVote().isVoted()) {
					votedByUser = true;
					break;
				}
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
		});

	}

}
