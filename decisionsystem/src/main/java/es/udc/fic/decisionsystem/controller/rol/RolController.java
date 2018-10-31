package es.udc.fic.decisionsystem.controller.rol;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.repository.rol.RolRepository;

@RestController
public class RolController {

	@Autowired
	private RolRepository roleRepository;
	
	@GetMapping("/api/rol")
	public Page<Rol> getRol(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@PostMapping("/api/rol")
	public Rol createRol(@Valid @RequestBody Rol rol) {
		return roleRepository.save(rol);
	}
	
	@DeleteMapping("/api/rol/{rolId}")
	public ResponseEntity<?> deleteRol(@PathVariable Long rolId) {
		return roleRepository.findById(rolId).map(rol -> {
			roleRepository.delete(rol);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + rolId));
	}
}
