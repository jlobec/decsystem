package es.udc.fic.decisionsystem.controller.asamblea;

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
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.usuarioasamblea.UsuarioAsamblea;
import es.udc.fic.decisionsystem.payload.ApiResponse;
import es.udc.fic.decisionsystem.payload.asamblea.AssemblyPollRequest;
import es.udc.fic.decisionsystem.payload.asamblea.AssemblyUserRequest;
import es.udc.fic.decisionsystem.repository.asamblea.AsambleaRepository;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaasamblea.ConsultaAsambleaRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;
import es.udc.fic.decisionsystem.repository.usuarioasamblea.UsuarioAsambleaRepository;

@RestController
public class AsambleaController {

	@Autowired
	private AsambleaRepository asambleaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioAsambleaRepository usuarioAsambleaRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private ConsultaAsambleaRepository consultaAsambleaRepository;

	@GetMapping("/api/assembly/{asambleaId}")
	public Asamblea getAsamblea(@PathVariable Integer asambleaId) {
		return asambleaRepository.findById(asambleaId).map(asamblea -> {
			return asamblea;
		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));
	}

	@GetMapping("/api/assembly")
	public Page<Asamblea> getAsamblea(Pageable pageable) {
		return asambleaRepository.findAll(pageable);
	}

	@GetMapping("/api/assembly/{asambleaId}/users")
	public Page<Usuario> getAsambleaUsers(Pageable pageable, @PathVariable Integer asambleaId) {
		return usuarioRepository.findByIdAsamblea(pageable, asambleaId);
	}

	@GetMapping("/api/assembly/{asambleaId}/polls")
	public Page<Consulta> getAssemblyPolls(Pageable pageable, @PathVariable Integer asambleaId) {
		return consultaRepository.findByIdAsamblea(pageable, asambleaId);
	}

	@PostMapping("/api/assembly")
	public Asamblea createAsamblea(@Valid @RequestBody Asamblea asamblea) {
		return asambleaRepository.save(asamblea);
	}

	@PostMapping("/api/assembly/{asambleaId}/adduser")
	public ResponseEntity<?> addUser(@Valid @RequestBody AssemblyUserRequest addUserRequest,
			@PathVariable Integer asambleaId) {
		return asambleaRepository.findById(asambleaId).map(foundAsamblea -> {
			return usuarioRepository.findById(addUserRequest.getUserId()).map(user -> {

				if (!usuarioAsambleaRepository.findByUsuarioAndAsamblea(user, foundAsamblea).isPresent()) {
					UsuarioAsamblea userAssemblyRelation = new UsuarioAsamblea();
					userAssemblyRelation.setUsuario(user);
					userAssemblyRelation.setAsamblea(foundAsamblea);
					userAssemblyRelation.setEsAdministrador(Boolean.FALSE);
					usuarioAsambleaRepository.save(userAssemblyRelation);

					return ResponseEntity.ok()
							.body(new ApiResponse(true, String.format("Added user %d ", addUserRequest.getUserId())));
				} else {
					return ResponseEntity.badRequest().body(new ApiResponse(false,
							String.format("User %d already added to assembly", addUserRequest.getUserId())));
				}

			}).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + addUserRequest.getUserId()));
		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));

	}

	@PostMapping("/api/assembly/{asambleaId}/deleteuser")
	public ResponseEntity<?> deleteUser(@Valid @RequestBody AssemblyUserRequest deleteUserRequest,
			@PathVariable Integer asambleaId) {
		return asambleaRepository.findById(asambleaId).map(foundAsamblea -> {
			return usuarioRepository.findById(deleteUserRequest.getUserId()).map(user -> {
				return usuarioAsambleaRepository.findByUsuarioAndAsamblea(user, foundAsamblea).map(relation -> {
					usuarioAsambleaRepository.delete(relation);
					return ResponseEntity.ok().body(new ApiResponse(true,
							String.format("Deleted user %d from assembly", deleteUserRequest.getUserId())));
				}).orElseThrow(() -> new ResourceNotFoundException("User not added previously"));
			}).orElseThrow(
					() -> new ResourceNotFoundException("User not found with id " + deleteUserRequest.getUserId()));
		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));

	}

	@PostMapping("/api/assembly/{asambleaId}/addpoll")
	public ResponseEntity<?> addPoll(@Valid @RequestBody AssemblyPollRequest addPollRequest,
			@PathVariable Integer asambleaId) {

		Asamblea asamblea = asambleaRepository.findById(asambleaId).map(a -> {
			return a;
		}).orElseThrow(() -> new ResourceNotFoundException("Assembly not found with id " + asambleaId));

		Consulta consulta = consultaRepository.findById(addPollRequest.getPollId()).map(c -> {
			return c;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + addPollRequest.getPollId()));

		if (consultaAsambleaRepository.findByConsultaAndAsamblea(consulta, asamblea).isPresent()) {
			return ResponseEntity.badRequest().body(new ApiResponse(false,
					String.format("Poll %d already added to assembly", addPollRequest.getPollId())));
		}

		ConsultaAsamblea toAdd = new ConsultaAsamblea();
		toAdd.setAsamblea(asamblea);
		toAdd.setConsulta(consulta);
		consultaAsambleaRepository.save(toAdd);

		return ResponseEntity.ok()
				.body(new ApiResponse(true, String.format("Added poll %d ", addPollRequest.getPollId())));
	}

	@PostMapping("/api/assembly/{asambleaId}/deletepoll")
	public ResponseEntity<?> deletePoll(@Valid @RequestBody AssemblyPollRequest deletePollRequest,
			@PathVariable Integer asambleaId) {

		Asamblea asamblea = asambleaRepository.findById(asambleaId).map(a -> {
			return a;
		}).orElseThrow(() -> new ResourceNotFoundException("Assembly not found with id " + asambleaId));

		Consulta consulta = consultaRepository.findById(deletePollRequest.getPollId()).map(c -> {
			return c;
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not found with id " + deletePollRequest.getPollId()));

		return consultaAsambleaRepository.findByConsultaAndAsamblea(consulta, asamblea).map(ca -> {
			consultaAsambleaRepository.delete(ca);
			return ResponseEntity.ok().body(new ApiResponse(true,
					String.format("Deleted poll %d from assembly", deletePollRequest.getPollId())));
		}).orElseThrow(() -> new ResourceNotFoundException("Poll not added"));

	}

	@PutMapping("/api/assembly/{asambleaId}")
	public ResponseEntity<?> updateAsamblea(@Valid @RequestBody Asamblea asamblea, @PathVariable Integer asambleaId) {
		return asambleaRepository.findById(asambleaId).map(foundAsamblea -> {
			asamblea.setIdAsamblea(asambleaId);
			asambleaRepository.save(asamblea);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));
	}

	@DeleteMapping("/api/assembly/{asambleaId}")
	public ResponseEntity<?> deleteAsamblea(@PathVariable Integer asambleaId) {
		return asambleaRepository.findById(asambleaId).map(asamblea -> {
			asambleaRepository.delete(asamblea);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));
	}

}
