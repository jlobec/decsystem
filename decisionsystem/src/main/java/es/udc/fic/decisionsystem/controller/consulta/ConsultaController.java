package es.udc.fic.decisionsystem.controller.consulta;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consulta.EstadoConsulta;
import es.udc.fic.decisionsystem.model.consulta.EstadoConsultaEnum;
import es.udc.fic.decisionsystem.model.consulta.VisibilidadResultadoConsulta;
import es.udc.fic.decisionsystem.model.consultaasamblea.ConsultaAsamblea;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.util.DateUtil;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.ApiResponse;
import es.udc.fic.decisionsystem.payload.comentario.CommentResponse;
import es.udc.fic.decisionsystem.payload.consulta.AddPollOptionRequest;
import es.udc.fic.decisionsystem.payload.consulta.CreatePollRequest;
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
import es.udc.fic.decisionsystem.repository.asamblea.AsambleaRepository;
import es.udc.fic.decisionsystem.repository.comentario.ComentarioRepository;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consulta.EstadoConsultaRepository;
import es.udc.fic.decisionsystem.repository.consulta.VisibilidadResultadoConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaasamblea.ConsultaAsambleaRepository;
import es.udc.fic.decisionsystem.repository.consultaopcion.ConsultaOpcionRepository;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;
import es.udc.fic.decisionsystem.repository.voto.VotoRepository;
import es.udc.fic.decisionsystem.service.consulta.ConsultaService;

@RestController
public class ConsultaController {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private SistemaConsultaRepository sistemaConsultaRepository;

	@Autowired
	private ConsultaOpcionRepository consultaOpcionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AsambleaRepository asambleaRepository;

	@Autowired
	private ConsultaAsambleaRepository consultaAsambleaRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private EstadoConsultaRepository estadoConsultaRepository;

	@Autowired
	private VisibilidadResultadoConsultaRepository visibilidadResultadoConsultaRepository;

	@Autowired
	private ConsultaService consultaService;

	@GetMapping("/api/poll/{consultaId}")
	public PollSummaryResponse getConsulta(@PathVariable Long consultaId, Principal principal) {
		Optional<Usuario> loggedUser = usuarioRepository.findByNickname(principal.getName());
		if (loggedUser.isPresent()) {
			return consultaService.getPollById(consultaId, loggedUser.get());
		}
		// Actually unlikely
		throw new ResourceNotFoundException("No logged user");
	}

	@GetMapping("/api/poll")
	public Page<Consulta> getConsulta(Pageable pageable) {
		return consultaRepository.findAll(pageable);
	}

	@GetMapping("/api/poll/{consultaId}/options")
	public List<PollOptionResponse> getPollOptions(@PathVariable Long consultaId) {
		Consulta consulta = consultaRepository.findById(consultaId).map(c -> {
			return c;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));

		return consultaOpcionRepository.findByConsulta(consulta).stream().map(opt -> {
			PollOptionResponse option = new PollOptionResponse();
			option.setPollOptionId(opt.getIdConsultaOpcion());
			option.setName(opt.getNombre());
			option.setDescription(opt.getDescripcion());
			return option;
		}).collect(Collectors.toList());
	}

	@GetMapping("/api/poll/status")
	public List<PollStatusResponse> getStatuses() {
		return estadoConsultaRepository.findAll().stream().map(status -> {
			PollStatusResponse result = new PollStatusResponse();
			result.setName(status.getNombre());
			result.setStatusId(status.getIdEstadoConsulta());
			return result;
		}).collect(Collectors.toList());
	}

	@GetMapping("/api/poll/resultsvisibility")
	public List<PollResultsVisibilityResponse> getResultsVisibilityOptions() {
		return visibilidadResultadoConsultaRepository.findAll().stream().map(resultsVisibility -> {
			PollResultsVisibilityResponse result = new PollResultsVisibilityResponse();
			result.setName(resultsVisibility.getNombre());
			result.setResultsVisibilityId(resultsVisibility.getIdVisibilidadResultadoConsulta());
			return result;
		}).collect(Collectors.toList());
	}

	@GetMapping("/api/poll/open")
	public Page<PollSummaryResponse> getOpenPolls(Pageable pageable, Principal principal,
			@RequestParam(value = "pollTypeId", required = false) Integer pollTypeId,
			@RequestParam(value = "pollStatusId", required = false) Integer pollStatusId) {

		Optional<Usuario> loggedUser = usuarioRepository.findByNickname(principal.getName());
		if (loggedUser.isPresent()) {
			return consultaService.getUserPolls(pageable, loggedUser.get(), pollTypeId, pollStatusId);
		}
		// Actually unlikely
		throw new ResourceNotFoundException("No logged user");
	}

	@GetMapping("/api/poll/{consultaId}/comments")
	public Page<CommentResponse> getPollComments(Pageable pageable, @PathVariable Long consultaId,
			Principal principal) {

		Consulta consulta = consultaRepository.findById(consultaId).map(c -> {
			return c;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));

		return comentarioRepository.findByConsulta(pageable, consulta).map(comentario -> {
			CommentResponse response = new CommentResponse();

			UserDto user = new UserDto();
			Set<String> roles = new HashSet<>();
			user.setUserId(comentario.getUsuario().getIdUsuario());
			user.setName(comentario.getUsuario().getNombre());
			user.setLastName(comentario.getUsuario().getApellido());
			user.setEmail(comentario.getUsuario().getEmail());
			user.setNickname(comentario.getUsuario().getNickname());
			for (Rol r : comentario.getUsuario().getRoles()) {
				roles.add(r.getNombre().name());
			}
			user.setRoles(roles);

			response.setCommentId(comentario.getIdComentario());
			response.setPollId(comentario.getConsulta().getIdConsulta());
			response.setUser(user);
			response.setContent(comentario.getContenido());
			return response;
		});
	}

	@GetMapping("/api/poll/{consultaId}/results")
	public List<PollResults> getPollResults(@PathVariable Long consultaId) {
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

	@PostMapping("/api/poll")
	public PollSummaryResponse createConsulta(@Valid @RequestBody CreatePollRequest poll) {

		Optional<SistemaConsulta> pollSystem = sistemaConsultaRepository.findById(poll.getPollSystemId());
		if (pollSystem.isPresent()) {

			EstadoConsulta statusOpen = estadoConsultaRepository.findById(EstadoConsultaEnum.Open.getIdEstadoConsulta())
					.orElseThrow(() -> new ResourceNotFoundException("Status not found"));

			VisibilidadResultadoConsulta resultsVisibility = visibilidadResultadoConsultaRepository
					.findById(poll.getResultsVisibilityId())
					.orElseThrow(() -> new ResourceNotFoundException("Results visibility option not found"));

			// Save poll
			Consulta newPoll = new Consulta();
			newPoll.setTitulo(poll.getTitle());
			newPoll.setDescripcion(poll.getDescription());
			newPoll.setFechaHoraInicio(DateUtil.toDate(poll.getStartTime()));
			newPoll.setFechaHoraFin(DateUtil.toDate(poll.getEndTime()));
			newPoll.setSistemaConsulta(pollSystem.get());
			newPoll.setEstadoConsulta(statusOpen);
			newPoll.setVisibilidadResultadoConsulta(resultsVisibility);
			Consulta savedPoll = consultaRepository.save(newPoll);

			// Save options
			List<ConsultaOpcion> pollOptionsSaved = new ArrayList<>();
			for (AddPollOptionRequest pollOption : poll.getPollOptions()) {
				ConsultaOpcion option = new ConsultaOpcion();
				option.setConsulta(savedPoll);
				option.setNombre(pollOption.getName());
				option.setDescripcion(pollOption.getDescription());
				pollOptionsSaved.add(consultaOpcionRepository.save(option));
			}
			List<PollOptionResponse> pollOptions = pollOptionsSaved.stream().map(opt -> {
				PollOptionResponse option = new PollOptionResponse();
				PollOptionVotedResponse userVote = new PollOptionVotedResponse();
				userVote.setVoted(false);
				userVote.setMotivation("");
				userVote.setPreferenceValue(0);

				option.setPollOptionId(opt.getIdConsultaOpcion());
				option.setName(opt.getNombre());
				option.setDescription(opt.getDescripcion());
				option.setUserVote(userVote);
				return option;
			}).collect(Collectors.toList());

			// Relate to assembly
			Asamblea asamblea = asambleaRepository.findById(poll.getAssemblyId()).map(a -> {
				return a;
			}).orElseThrow(() -> new ResourceNotFoundException("Assembly not found with id " + poll.getAssemblyId()));

			if (consultaAsambleaRepository.findByConsultaAndAsamblea(savedPoll, asamblea).isPresent()) {
				throw new ResourceNotFoundException("Relation already exists");
			}

			ConsultaAsamblea toAdd = new ConsultaAsamblea();
			toAdd.setAsamblea(asamblea);
			toAdd.setConsulta(savedPoll);
			consultaAsambleaRepository.save(toAdd);

			PollSystemResponse pollSystemResponse = new PollSystemResponse();
			pollSystemResponse.setPollTypeId(pollSystem.get().getIdSistemaConsulta());
			pollSystemResponse.setName(pollSystem.get().getNombre());
			pollSystemResponse.setDescription(pollSystem.get().getDescripcion());

			PollStatusResponse pollStatus = new PollStatusResponse();
			pollStatus.setStatusId(savedPoll.getEstadoConsulta().getIdEstadoConsulta());
			pollStatus.setName(savedPoll.getEstadoConsulta().getNombre());

			PollResultsVisibilityResponse pollResultsVisibility = new PollResultsVisibilityResponse();
			pollResultsVisibility.setResultsVisibilityId(
					savedPoll.getVisibilidadResultadoConsulta().getIdVisibilidadResultadoConsulta());
			pollResultsVisibility.setName(savedPoll.getVisibilidadResultadoConsulta().getNombre());

			PollSummaryResponse response = new PollSummaryResponse();
			response.setPollId(savedPoll.getIdConsulta());
			response.setTitle(savedPoll.getTitulo());
			response.setDescription(savedPoll.getDescripcion());
			response.setStartsAt(savedPoll.getFechaHoraInicio().getTime());
			response.setEndsAt(savedPoll.getFechaHoraFin().getTime());
			response.setVotedByUser(false);
			response.setPollSystem(pollSystemResponse);
			response.setStatus(pollStatus);
			response.setResultsVisibility(pollResultsVisibility);
			response.setPollOptions(pollOptions);

			return response;
		} else {
			throw new ResourceNotFoundException("Poll system not found");
		}
	}

	@PostMapping("/api/poll/{consultaId}/addoption")
	public ResponseEntity<?> addPollOption(@Valid @RequestBody AddPollOptionRequest addPollOptionRequest,
			@PathVariable Long consultaId) {
		Consulta consulta = consultaRepository.findById(consultaId).map(c -> {
			return c;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));

		ConsultaOpcion option = new ConsultaOpcion();
		option.setConsulta(consulta);
		option.setNombre(addPollOptionRequest.getName());
		option.setDescripcion(addPollOptionRequest.getDescription());
		consultaOpcionRepository.save(option);

		return ResponseEntity.ok().body(new ApiResponse(true, "Option added"));

	}

	@PutMapping("/api/poll/{consultaId}")
	public ResponseEntity<?> updateConsulta(@Valid @RequestBody Consulta consulta, @PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(foundConsulta -> {
			consulta.setIdConsulta(consultaId);
			consultaRepository.save(consulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));
	}

	@DeleteMapping("/api/poll/{consultaId}")
	public ResponseEntity<?> deleteConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			consultaRepository.delete(consulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

}
