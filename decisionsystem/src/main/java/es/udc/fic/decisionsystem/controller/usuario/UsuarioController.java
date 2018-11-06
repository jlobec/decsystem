package es.udc.fic.decisionsystem.controller.usuario;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.usuario.UpdateUserRequest;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/api/user/{usuarioId}")
	public Usuario getUsuarioById(@PathVariable Long usuarioId) {
		return usuarioRepository.findById(usuarioId).map(usuario -> {
			return usuario;
		}).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + usuarioId));
	}

	@GetMapping("/api/user")
	public Page<Usuario> getUsuario(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	@PutMapping("/api/user/{usuarioId}")
	public Usuario updateUsuario(@Valid @RequestBody UpdateUserRequest request, @PathVariable Long usuarioId) {
		return usuarioRepository.findById(usuarioId).map(foundUsuario -> {
			foundUsuario.setNombre(request.getName());
			foundUsuario.setApellido(request.getLastname());
			return usuarioRepository.save(foundUsuario); 
		}).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + usuarioId));
	}

	@DeleteMapping("/api/user/{usuarioId}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long usuarioId) {
		return usuarioRepository.findById(usuarioId).map(usuario -> {
			usuarioRepository.delete(usuario);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + usuarioId));
	}
	
}
