/**
 * Copyright © 2019 Jesus Lopez Becerra (jesus.lopez.becerra@udc.es)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.udc.fic.decisionsystem.service.consulta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.udc.fic.decisionsystem.exception.BadRequestException;
import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsultaEnum;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.consulta.PollOptionResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollOptionVotedResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollResultsVisibilityResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollStatusResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.payload.consulta.resultados.PollResultOption;
import es.udc.fic.decisionsystem.payload.consulta.resultados.PollResults;
import es.udc.fic.decisionsystem.payload.consulta.resultados.PollResultsItem;
import es.udc.fic.decisionsystem.payload.pollsystem.PollSystemResponse;
import es.udc.fic.decisionsystem.payload.usuario.UserDto;
import es.udc.fic.decisionsystem.repository.comentario.ComentarioRepository;
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
	
	@Autowired
	private ComentarioRepository comentarioRepository;

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
	public Page<PollSummaryResponse> getUserPolls(Pageable pageable, Usuario user, Integer pollTypeId,
			Integer pollStatusId) {
		if (pollTypeId != null && pollStatusId != null) {
			return consultaRepository
					.findByUserAndPollTypeAndPollStatus(pageable, user.getIdUsuario(), pollTypeId, pollStatusId)
					.map(poll -> {
						return buildPollSummaryResponse(poll, user);
					});
		}
		if (pollTypeId != null) {
			return consultaRepository.findByUserAndPollType(pageable, user.getIdUsuario(), pollTypeId).map(poll -> {
				return buildPollSummaryResponse(poll, user);
			});
		}
		if (pollStatusId != null) {
			return consultaRepository.findByUserAndPollStatus(pageable, user.getIdUsuario(), pollStatusId).map(poll -> {
				return buildPollSummaryResponse(poll, user);
			});
		}
		return consultaRepository.findByUser(pageable, user.getIdUsuario()).map(poll -> {
			return buildPollSummaryResponse(poll, user);
		});
	}

	@Override
	public PollSummaryResponse buildPollSummaryResponse(Consulta poll, Usuario user) {

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
					votedOption.setPreferenceValue(vote.getPuntuacion());
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
		case SCORE_VOTE:
			for (PollOptionResponse pollOption : pollOptions) {
				if (pollOption.getUserVote().isVoted()) {
					votedByUser = true;
					break;
				}
			}
			break;
		default:
			throw new BadRequestException("Poll system not found");
		}
		
		List<Comentario> comments = comentarioRepository.findAllByConsulta(poll);
		Integer commentNumber = 0;
		if (comments != null && !comments.isEmpty()) {
			commentNumber = comments.size();
		}

		PollSystemResponse pollSystem = new PollSystemResponse();
		pollSystem.setPollTypeId(poll.getSistemaConsulta().getIdSistemaConsulta());
		pollSystem.setName(poll.getSistemaConsulta().getNombre());
		pollSystem.setDescription(poll.getSistemaConsulta().getDescripcion());

		PollStatusResponse pollStatus = new PollStatusResponse();
		pollStatus.setStatusId(poll.getEstadoConsulta().getIdEstadoConsulta());
		pollStatus.setName(poll.getEstadoConsulta().getNombre());

		PollResultsVisibilityResponse pollResultsVisibility = new PollResultsVisibilityResponse();
		pollResultsVisibility
				.setResultsVisibilityId(poll.getVisibilidadResultadoConsulta().getIdVisibilidadResultadoConsulta());
		pollResultsVisibility.setName(poll.getVisibilidadResultadoConsulta().getNombre());

		PollSummaryResponse pollSummary = new PollSummaryResponse();
		pollSummary.setPollId(poll.getIdConsulta());
		pollSummary.setTitle(poll.getTitulo());
		pollSummary.setDescription(poll.getDescripcion());
		pollSummary.setStartsAt(poll.getFechaHoraInicio().getTime());
		pollSummary.setEndsAt(poll.getFechaHoraFin().getTime());
		pollSummary.setVotedByUser(votedByUser);
		pollSummary.setPollSystem(pollSystem);
		pollSummary.setStatus(pollStatus);
		pollSummary.setResultsVisibility(pollResultsVisibility);
		pollSummary.setPollOptions(pollOptions);
		pollSummary.setCommentNumber(commentNumber);

		return pollSummary;
	}

	@Override
	public List<PollResults> getResults(Long consultaId) {
		List<PollResults> pollResults = new ArrayList<>();
		List<Voto> pollVotes = new ArrayList<>();
		pollVotes = votoRepository.findByConsulta(consultaId);
		Map<ConsultaOpcion, List<Voto>> groupByPollOption = pollVotes.stream()
				.collect(Collectors.groupingBy(v -> v.getConsultaOpcion()));

		for (Map.Entry<ConsultaOpcion, List<Voto>> entry : groupByPollOption.entrySet()) {
			PollResults results = new PollResults();
			ConsultaOpcion pollOption = entry.getKey();
			List<Voto> pollOptionVotes = entry.getValue();

			// Option
			PollResultOption resultsOption = new PollResultOption();
			resultsOption.setOptionId(pollOption.getIdConsultaOpcion());
			resultsOption.setName(pollOption.getNombre());
			resultsOption.setDescription(pollOption.getDescripcion());

			// Vote info
			List<PollResultsItem> resultsItems = new ArrayList<>();
			for (Voto v : pollOptionVotes) {
				PollResultsItem resultsItem = new PollResultsItem();
				UserDto resultsItemUser = new UserDto();
				Set<String> roles = new HashSet<>();

				resultsItemUser.setUserId(v.getUsuario().getIdUsuario());
				resultsItemUser.setName(v.getUsuario().getNombre());
				resultsItemUser.setLastName(v.getUsuario().getApellido());
				resultsItemUser.setNickname(v.getUsuario().getNickname());
				resultsItemUser.setEmail(v.getUsuario().getEmail());
				resultsItemUser.setRoles(roles);
				for (Rol r : v.getUsuario().getRoles()) {
					roles.add(r.getNombre().name());
				}
				resultsItemUser.setRoles(roles);

				resultsItem.setMotivation(v.getMotivacion());
				resultsItem.setUser(resultsItemUser);
				resultsItem.setScore(v.getPuntuacion());
				resultsItems.add(resultsItem);
			}

			results.setOption(resultsOption);
			results.setItems(resultsItems);
			pollResults.add(results);
		}

		return pollResults;
	}

}
