package es.udc.fic.decisionsystem.controller.voto;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.voto.VoteRequest;
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

	@PostMapping("/api/vote")
	public Voto createVoto(@Valid @RequestBody VoteRequest voteRequest, Principal principal) {
		// Get current user
		String username = principal.getName();
		Usuario user = usuarioRepository.findByNickname(username)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", username)));

		ConsultaOpcion option = consultaOpcionRepository.findById(voteRequest.getOptionId()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Option %d not found", voteRequest.getOptionId())));

		// TODO check the option belongs to a poll which is added to an assembly the
		// user belongs to.

		// TODO check if the user already has voted this option (not the poll: bear in
		// mind multiple choice allowed)

		Voto v = new Voto();
		v.setConsultaOpcion(option);
		v.setUsuario(user);
		v.setMotivacion(voteRequest.getMotivation());
		return votoRepository.save(v);
	}

//	@PutMapping("/api/vote/{votoId}")
//	public ResponseEntity<?> updateVoto(@Valid @RequestBody Voto voto, @PathVariable Long votoId) {
//		return votoRepository.findById(votoId).map(foundVoto -> {
//			voto.setIdVoto(votoId);
//			votoRepository.save(voto);
//			return ResponseEntity.ok().build();
//		}).orElseThrow(() -> new ResourceNotFoundException("Vote not found with id " + votoId));
//	}

//	@DeleteMapping("/api/vote/{votoId}")
//	public ResponseEntity<?> deleteVoto(@PathVariable Long votoId) {
//		return votoRepository.findById(votoId).map(voto -> {
//			votoRepository.delete(voto);
//			return ResponseEntity.ok().build();
//		}).orElseThrow(() -> new ResourceNotFoundException("Vote not found with id " + votoId));
//	}

}
