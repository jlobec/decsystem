package es.udc.fic.decisionsystem.controller.voto;

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

import es.udc.fic.decisionsystem.controller.common.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.repository.voto.VotoRepository;

@RestController
public class VotoController {

	@Autowired
	private VotoRepository votoRepository;

	@GetMapping("/api/voto/{votoId}")
	public Voto getVoto(@PathVariable Long votoId) {
		return votoRepository.findById(votoId).map(voto -> {
			return voto;
		}).orElseThrow(() -> new ResourceNotFoundException("Voto not found with id " + votoId));
	}

	@GetMapping("/api/voto")
	public Page<Voto> getVoto(Pageable pageable) {
		return votoRepository.findAll(pageable);
	}

	@PostMapping("/api/voto")
	public Voto createVoto(@Valid @RequestBody Voto voto) {
		return votoRepository.save(voto);
	}

	@PutMapping("/api/voto/{votoId}")
	public ResponseEntity<?> updateVoto(@Valid @RequestBody Voto voto, @PathVariable Long votoId) {
		return votoRepository.findById(votoId).map(foundVoto -> {
			voto.setIdVoto(votoId);
			votoRepository.save(voto);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Voto not found with id " + votoId));
	}

	@DeleteMapping("/api/voto/{votoId}")
	public ResponseEntity<?> deleteVoto(@PathVariable Long votoId) {
		return votoRepository.findById(votoId).map(voto -> {
			votoRepository.delete(voto);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Voto not found with id " + votoId));
	}

}
