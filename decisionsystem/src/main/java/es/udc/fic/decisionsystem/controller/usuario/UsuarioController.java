package es.udc.fic.decisionsystem.controller.usuario;

import javax.validation.Valid;

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

import es.udc.fic.decisionsystem.controller.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;

@RestController
public class UsuarioController {

	private UsuarioRepository usuarioRepository;

	@GetMapping("/api/usuario")
	public Page<Usuario> getUsuario(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	@PostMapping("/api/usuario")
	public Usuario createUsuario(@Valid @RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@PutMapping("/api/usuario/{usuarioId}")
	public Usuario updateUsuario(@Valid @RequestBody Usuario usuario, @PathVariable Long usuarioId) {
		return usuarioRepository.findById(usuarioId).map(foundUsuario -> {
			usuario.setIdUsuario(usuarioId);
			usuarioRepository.save(usuario);
			return usuario;
		}).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + usuarioId));
	}

	@DeleteMapping("/api/usuario/{usuarioId}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long usuarioId) {
		return usuarioRepository.findById(usuarioId).map(usuario -> {
			usuarioRepository.delete(usuario);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + usuarioId));
	}
	
}
