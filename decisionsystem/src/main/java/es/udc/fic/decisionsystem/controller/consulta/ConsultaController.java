package es.udc.fic.decisionsystem.controller.consulta;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaasamblea.ConsultaAsamblea;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.util.DateUtil;
import es.udc.fic.decisionsystem.payload.ApiResponse;
import es.udc.fic.decisionsystem.payload.consulta.AddPollOptionRequest;
import es.udc.fic.decisionsystem.payload.consulta.CreatePollRequest;
import es.udc.fic.decisionsystem.payload.consulta.PollOptionResponse;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.payload.pollsystem.PollSystemResponse;
import es.udc.fic.decisionsystem.repository.asamblea.AsambleaRepository;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaasamblea.ConsultaAsambleaRepository;
import es.udc.fic.decisionsystem.repository.consultaopcion.ConsultaOpcionRepository;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;

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

	@GetMapping("/api/poll/{consultaId}")
	public PollSummaryResponse getConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			PollSummaryResponse response = new PollSummaryResponse();

			PollSystemResponse pollSystem = new PollSystemResponse();
			pollSystem.setPollTypeId(consulta.getSistemaConsulta().getIdSistemaConsulta());
			pollSystem.setName(consulta.getSistemaConsulta().getNombre());
			pollSystem.setDescription(consulta.getSistemaConsulta().getDescripcion());

			List<PollOptionResponse> pollOptions = consultaOpcionRepository.findByConsulta(consulta).stream()
					.map(opt -> {
						PollOptionResponse option = new PollOptionResponse();
						option.setPollOptionId(opt.getIdConsultaOpcion());
						option.setName(opt.getNombre());
						option.setDescription(opt.getDescripcion());
						return option;
					}).collect(Collectors.toList());

			response.setPollId(consulta.getIdConsulta());
			response.setTitle(consulta.getTitulo());
			response.setDescription(consulta.getDescripcion());
			response.setStartsAt(consulta.getFechaHoraInicio().getTime());
			response.setEndsAt(consulta.getFechaHoraFin().getTime());
			response.setPollSystem(pollSystem);
			response.setPollOptions(pollOptions);

			return response;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));
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

	@GetMapping("/api/poll/open")
	public Page<PollSummaryResponse> getOpenPolls(Pageable pageable, Principal principal) {

		Optional<Usuario> loggedUser = usuarioRepository.findByNickname(principal.getName());
		if (loggedUser.isPresent()) {
			Long userId = loggedUser.get().getIdUsuario();
			return consultaRepository.findByUser(pageable, userId).map(poll -> {

				// Get options
				List<PollOptionResponse> pollOptions = consultaOpcionRepository.findByConsulta(poll).stream()
						.map(opt -> {
							PollOptionResponse option = new PollOptionResponse();
							option.setPollOptionId(opt.getIdConsultaOpcion());
							option.setName(opt.getNombre());
							option.setDescription(opt.getDescripcion());
							return option;
						}).collect(Collectors.toList());

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
				pollSummary.setPollSystem(pollSystem);
				pollSummary.setPollOptions(pollOptions);

				return pollSummary;
			});
		}
		// Actually unlikely
		throw new ResourceNotFoundException("No logged user");
	}

	@PostMapping("/api/poll")
	public PollSummaryResponse createConsulta(@Valid @RequestBody CreatePollRequest poll) {

		Optional<SistemaConsulta> pollSystem = sistemaConsultaRepository.findById(poll.getPollSystemId());
		if (pollSystem.isPresent()) {
			// Save poll
			Consulta newPoll = new Consulta();
			newPoll.setTitulo(poll.getTitle());
			newPoll.setDescripcion(poll.getDescription());
			newPoll.setFechaHoraInicio(DateUtil.toDate(poll.getStartTime()));
			newPoll.setFechaHoraFin(DateUtil.toDate(poll.getEndTime()));
			newPoll.setSistemaConsulta(pollSystem.get());
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
				option.setPollOptionId(opt.getIdConsultaOpcion());
				option.setName(opt.getNombre());
				option.setDescription(opt.getDescripcion());
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

			PollSummaryResponse response = new PollSummaryResponse();
			response.setPollId(savedPoll.getIdConsulta());
			response.setTitle(savedPoll.getTitulo());
			response.setDescription(savedPoll.getDescripcion());
			response.setStartsAt(savedPoll.getFechaHoraInicio().getTime());
			response.setEndsAt(savedPoll.getFechaHoraFin().getTime());
			response.setPollSystem(pollSystemResponse);
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
