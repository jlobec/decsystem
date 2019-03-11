package es.udc.fic.decisionsystem.controller.consulta;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.util.DateUtil;
import es.udc.fic.decisionsystem.payload.ApiResponse;
import es.udc.fic.decisionsystem.payload.consulta.AddPollOptionRequest;
import es.udc.fic.decisionsystem.payload.consulta.CreatePollRequest;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
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

	@GetMapping("/api/poll/{consultaId}")
	public Consulta getConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			return consulta;
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

	@GetMapping("/api/poll")
	public Page<Consulta> getConsulta(Pageable pageable) {
		return consultaRepository.findAll(pageable);
	}

	@GetMapping("/api/poll/{consultaId}/options")
	public List<ConsultaOpcion> getPollOptions(@PathVariable Long consultaId) {
		Consulta consulta = consultaRepository.findById(consultaId).map(c -> {
			return c;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + consultaId));

		return consultaOpcionRepository.findByConsulta(consulta);
	}
	
	@GetMapping("/api/poll/open")
	public Page<PollSummaryResponse> getOpenPolls(Pageable pageable, Principal principal){
		
		Optional<Usuario> loggedUser = usuarioRepository.findByNickname(principal.getName());
		if (loggedUser.isPresent()) {
			Long userId = loggedUser.get().getIdUsuario();
			return consultaRepository.findByUser(pageable, userId).map(poll -> {
				PollSummaryResponse pollSummary = new PollSummaryResponse();
				pollSummary.setPollTitle(poll.getTitulo());
				pollSummary.setStartedAt(poll.getFechaHoraInicio().getTime());
				pollSummary.setEndsAt(poll.getFechaHoraFin().getTime());
				return pollSummary;
			});
		}
		// Actually unlikely
		throw new ResourceNotFoundException("No logged user");
	}

	@PostMapping("/api/poll")
	public Consulta createConsulta(@Valid @RequestBody CreatePollRequest poll) {

		Optional<SistemaConsulta> pollSystem = sistemaConsultaRepository.findById(poll.getIdPollSystem());
		if (pollSystem.isPresent()) {
			Consulta newPoll = new Consulta();
			newPoll.setTitulo(poll.getTitle());
			newPoll.setDescripcion(poll.getDescription());
			newPoll.setFechaHoraInicio(DateUtil.toDate(poll.getStartsAt()));
			newPoll.setFechaHoraFin(DateUtil.toDate(poll.getFinishesAt()));
			newPoll.setSistemaConsulta(pollSystem.get());

			return consultaRepository.save(newPoll);
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
