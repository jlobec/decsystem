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
package es.udc.fic.decisionsystem.controller.voto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.BadRequestException;
import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsultaEnum;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.voto.VoteOptionRequest;
import es.udc.fic.decisionsystem.payload.voto.VoteOptionResponse;
import es.udc.fic.decisionsystem.payload.voto.VoteRequest;
import es.udc.fic.decisionsystem.payload.voto.VoteResponse;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaopcion.ConsultaOpcionRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;
import es.udc.fic.decisionsystem.repository.voto.VotoRepository;

@RestController
public class VotoController {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private ConsultaOpcionRepository consultaOpcionRepository;

	@GetMapping("/api/vote/{votoId}")
	public Voto getVoto(@PathVariable Long votoId) {
		return votoRepository.findById(votoId)
				.orElseThrow(() -> new ResourceNotFoundException("Vote not found with id " + votoId));
	}

	@GetMapping("/api/vote")
	public Page<Voto> getVoto(Pageable pageable) {
		return votoRepository.findAll(pageable);
	}
	
	@PostMapping("/api/vote/undo")
	public void undoVote(Principal principal) {
		// Check votes for the logged user
	}

	@PostMapping("/api/vote")
	public VoteResponse createVoto(@Valid @RequestBody VoteRequest voteRequest, Principal principal) {
		// Get current user
		String username = principal.getName();
		Usuario user = usuarioRepository.findByNickname(username)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", username)));

		// Get poll
		Consulta consulta = consultaRepository.findById(voteRequest.getPollId()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Poll %d not found", voteRequest.getPollId())));

		// Options to get may be different by pollSystem
		SistemaConsultaEnum pollSystem = SistemaConsultaEnum.getByName(consulta.getSistemaConsulta().getNombre());
		switch (pollSystem) {
		case SINGLE_OPTION:
			return voteSingleOption(voteRequest, user);
		case MULTIPLE_OPTION: 
			return voteMultipleOption(voteRequest, user);
		case SCORE_VOTE:
			return voteScoreOption(voteRequest, user);
		default:
			throw new BadRequestException("Poll system not found");
		}

	}

	/**
	 * Vote single option.
	 *
	 * @param voteRequest the vote request
	 * @param user        the user
	 * @return the vote response
	 */
	private VoteResponse voteSingleOption(VoteRequest voteRequest, Usuario user) {

		// Size of options must be 1
		if (voteRequest.getOptions().size() != 1) {
			throw new BadRequestException("Size of voted options invalid");
		}

		VoteOptionRequest votedOption = voteRequest.getOptions().stream().findFirst().get();

		ConsultaOpcion pollOption = consultaOpcionRepository.findById(votedOption.getOptionId()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Option %d not found", votedOption.getOptionId())));

		// TODO check the option belongs to a poll which is added to an assembly the
		// user belongs to.

		// TODO check if the user already has voted this option (not the poll: bear in
		// mind multiple choice allowed)

		Voto v = new Voto();
		v.setConsultaOpcion(pollOption);
		v.setUsuario(user);
		v.setMotivacion(votedOption.getMotivation());

		Voto savedVote = votoRepository.save(v);

		VoteResponse response = new VoteResponse();
		List<VoteOptionResponse> responseOptions = new ArrayList<>();
		VoteOptionResponse responseOption = new VoteOptionResponse();

		responseOption.setOptionId(savedVote.getConsultaOpcion().getIdConsultaOpcion());
		responseOption.setMotivation(savedVote.getMotivacion());
		responseOption.setVoteId(savedVote.getIdVoto());
		responseOption.setPreferenceValue(0); // no aplica
		responseOptions.add(responseOption);

		response.setPollId(pollOption.getConsulta().getIdConsulta());
		response.setOptions(responseOptions);

		return response;
	}
	
	/**
	 * Vote single option.
	 *
	 * @param voteRequest the vote request
	 * @param user        the user
	 * @return the vote response
	 */
	private VoteResponse voteMultipleOption(VoteRequest voteRequest, Usuario user) {

		List<VoteOptionRequest> votedOptions = voteRequest.getOptions();
		
		// Validations
		// - Size of options must be greater than 0
		// - Poll must exist
		if (votedOptions != null && votedOptions.size() < 1) {
			throw new BadRequestException("Size of voted options invalid");
		}
		Consulta poll = consultaRepository.findById(voteRequest.getPollId())
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Poll %d not found", voteRequest.getPollId())));

		// Vote and build response
		VoteResponse response = new VoteResponse();
		List<VoteOptionResponse> responseOptions = new ArrayList<>();
		for (VoteOptionRequest votedOption : votedOptions) {
			
			ConsultaOpcion pollOption = consultaOpcionRepository.findById(votedOption.getOptionId()).orElseThrow(
					() -> new ResourceNotFoundException(String.format("Option %d not found", votedOption.getOptionId())));

			// TODO check the option belongs to a poll which is added to an assembly the
			// user belongs to.

			// TODO check if the user already has voted this option (not the poll: bear in
			// mind multiple choice allowed)

			Voto v = new Voto();
			v.setConsultaOpcion(pollOption);
			v.setUsuario(user);
			v.setMotivacion(votedOption.getMotivation());

			Voto savedVote = votoRepository.save(v);
			
			VoteOptionResponse responseOption = new VoteOptionResponse();
			responseOption.setOptionId(savedVote.getConsultaOpcion().getIdConsultaOpcion());
			responseOption.setMotivation(savedVote.getMotivacion());
			responseOption.setVoteId(savedVote.getIdVoto());
			responseOption.setPreferenceValue(0); // no aplica
			responseOptions.add(responseOption);
			
		}
		
		response.setPollId(poll.getIdConsulta());
		response.setOptions(responseOptions);

		return response;
	}
	
	private VoteResponse voteScoreOption(VoteRequest voteRequest, Usuario user) { 
		List<VoteOptionRequest> votedOptions = voteRequest.getOptions();
		
		// Validations
		// - Size of options must be greater than 0
		// - Poll must exist
		if (votedOptions != null && votedOptions.size() < 1) {
			throw new BadRequestException("Size of voted options invalid");
		}
		Consulta poll = consultaRepository.findById(voteRequest.getPollId())
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Poll %d not found", voteRequest.getPollId())));
		List<ConsultaOpcion> pollOptions = consultaOpcionRepository.findByConsulta(poll);
		
		Map<Long, VoteOptionRequest> votedOptionIds = votedOptions.stream().collect(Collectors.toMap(VoteOptionRequest::getOptionId, item -> item));
		
		// Vote and build response
		// Fetch all options and match with the voted ones. 
		// The non-voted should ve voted with the minimum score (1) as well.
		VoteResponse response = new VoteResponse();
		List<VoteOptionResponse> responseOptions = new ArrayList<>();
		for (ConsultaOpcion  pollOption : pollOptions) {
			// If match, vote this option with the score setted
			// Otherwise set default value (1)
			Integer score = 1;
			String motivation = "";
			if (votedOptionIds.containsKey(pollOption.getIdConsultaOpcion())) {
				score = votedOptionIds.get(pollOption.getIdConsultaOpcion()).getPreferenceValue();
				motivation = votedOptionIds.get(pollOption.getIdConsultaOpcion()).getMotivation();
			} 
			
			Voto v = new Voto();
			v.setConsultaOpcion(pollOption);
			v.setUsuario(user);
			v.setMotivacion(motivation);
			v.setPuntuacion(score);

			Voto savedVote = votoRepository.save(v);
			
			VoteOptionResponse responseOption = new VoteOptionResponse();
			responseOption.setOptionId(savedVote.getConsultaOpcion().getIdConsultaOpcion());
			responseOption.setMotivation(savedVote.getMotivacion());
			responseOption.setVoteId(savedVote.getIdVoto());
			responseOption.setPreferenceValue(score);
			responseOptions.add(responseOption);
		}
		
		
		
		
		response.setPollId(poll.getIdConsulta());
		response.setOptions(responseOptions);

		return response;
	}


}
