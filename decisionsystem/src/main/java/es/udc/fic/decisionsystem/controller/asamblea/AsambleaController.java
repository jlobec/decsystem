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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.controller.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.repository.asamblea.AsambleaRepository;

@RestController
public class AsambleaController {

	@Autowired
	private AsambleaRepository asambleaRepository;

	@GetMapping
	public Page<Asamblea> getAsamblea(Pageable pageable) {
		return asambleaRepository.findAll(pageable);
	}

	@PostMapping("/api/asamblea")
	public Asamblea createAsamblea(@Valid @RequestBody Asamblea asamblea) {
		return asambleaRepository.save(asamblea);
	}
	
	@DeleteMapping("/api/asamblea/{asambleaId}")
	public ResponseEntity<?> deleteAsamblea(@PathVariable Integer asambleaId) {
        return asambleaRepository.findById(asambleaId)
                .map(asamblea -> {
                	asambleaRepository.delete(asamblea);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));
    }
}
