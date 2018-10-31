package es.udc.fic.decisionsystem.controller.comentario;

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
import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.repository.comentario.ComentarioRepository;

@RestController
public class ComentarioController {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@GetMapping("/api/comentario/{comentarioId}")
	public Comentario getComentario(@PathVariable Long comentarioId) {
		return comentarioRepository.findById(comentarioId).map(comentario -> {
			return comentario;
		}).orElseThrow(() -> new ResourceNotFoundException("Comentario not found with id " + comentarioId));
	}
	
	@GetMapping("/api/comentario")
	public Page<Comentario> getComentario(Pageable pageable) {
		return comentarioRepository.findAll(pageable);
	}

	@PostMapping("/api/comentario")
	public Comentario createComentario(@Valid @RequestBody Comentario comentario) {
		return comentarioRepository.save(comentario);
	}
	
	@PutMapping("/api/comentario/{comentarioId}")
	public ResponseEntity<?> updateComentario(@Valid @RequestBody Comentario comentario, @PathVariable Long comentarioId) {
        return comentarioRepository.findById(comentarioId)
                .map(foundComentario -> {
                	comentario.setIdComentario(comentarioId);
                	comentarioRepository.save(comentario);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Comentario not found with id " + comentarioId));
    }
	
	@DeleteMapping("/api/comentario/{comentarioId}")
	public ResponseEntity<?> deleteComentario(@PathVariable Long comentarioId) {
        return comentarioRepository.findById(comentarioId)
                .map(comentario -> {
                	comentarioRepository.delete(comentario);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Comentario not found with id " + comentarioId));
    }
}
