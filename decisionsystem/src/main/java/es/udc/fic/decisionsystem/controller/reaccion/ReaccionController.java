package es.udc.fic.decisionsystem.controller.reaccion;

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
import es.udc.fic.decisionsystem.model.reaccion.Reaccion;
import es.udc.fic.decisionsystem.repository.reaccion.ReaccionRepository;

@RestController
public class ReaccionController {

	@Autowired
	private ReaccionRepository reaccionRepository;
	
	@GetMapping("/api/reaccion/{reaccionId}")
	public Reaccion getReaccion(@PathVariable Long reaccionId) {
		return reaccionRepository.findById(reaccionId).map(reaccion -> {
			return reaccion;
		}).orElseThrow(() -> new ResourceNotFoundException("Reaccion not found with id " + reaccionId));
	}
	
	@GetMapping("/api/reaccion")
	public Page<Reaccion> getReaccion(Pageable pageable) {
		return reaccionRepository.findAll(pageable);
	}

	@PostMapping("/api/reaccion")
	public Reaccion createReaccion(@Valid @RequestBody Reaccion reaccion) {
		return reaccionRepository.save(reaccion);
	}
	
	@PutMapping("/api/reaccion/{reaccionId}")
	public ResponseEntity<?> updateReaccion(@Valid @RequestBody Reaccion reaccion, @PathVariable Long reaccionId) {
        return reaccionRepository.findById(reaccionId)
                .map(foundReaccion -> {
                	reaccion.setIdReaccion(reaccionId);
                	reaccionRepository.save(reaccion);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Reaccion not found with id " + reaccionId));
    }
	
	@DeleteMapping("/api/reaccion/{reaccionId}")
	public ResponseEntity<?> deleteReaccion(@PathVariable Long reaccionId) {
        return reaccionRepository.findById(reaccionId)
                .map(reaccion -> {
                	reaccionRepository.delete(reaccion);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Reaccion not found with id " + reaccionId));
    }
}
