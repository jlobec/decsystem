package es.udc.fic.decisionsystem.controller.usuario;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.usuario.UpdateUserRequest;
import es.udc.fic.decisionsystem.payload.usuario.UserDto;
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

//	@GetMapping("/api/user")
//	public Page<Usuario> getUsuario(Pageable pageable) {
//		return usuarioRepository.findAll(pageable);
//	}

	@PutMapping("/api/user/{usuarioId}")
	public Usuario updateUsuario(@Valid @RequestBody UpdateUserRequest request, @PathVariable Long usuarioId,
			Principal principal) {
		Long loggedUserId = usuarioRepository.findByNickname(principal.getName()).map(Usuario::getIdUsuario)
				.orElse(null);
		if (!usuarioId.equals(loggedUserId)) {
			throw new ResourceNotFoundException("Usuario not found with id " + usuarioId);
		}
		return usuarioRepository.findById(usuarioId).map(foundUsuario -> {
			foundUsuario.setNombre(request.getName());
			foundUsuario.setApellido(request.getLastName());
			foundUsuario.setEmail(request.getEmail());
			foundUsuario.setNickname(request.getNickname());
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

	@GetMapping("/api/user/me")
	public UserDto getUser(Principal principal) {
		return usuarioRepository.findByNickname(principal.getName()).map(user -> {
			UserDto toReturn = new UserDto();
			Set<String> roles = new HashSet<>();
			toReturn.setUserId(user.getIdUsuario());
			toReturn.setName(user.getNombre());
			toReturn.setLastName(user.getApellido());
			toReturn.setEmail(user.getEmail());
			toReturn.setNickname(user.getNickname());
			for (Rol r : user.getRoles()) {
				roles.add(r.getNombre().name());
			}
			toReturn.setRoles(roles);
			return toReturn;
		}).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id "));
	}

	@GetMapping("/api/user")
	public UserDto getByNameOrUserName(Pageable pageable,
			@RequestParam(value = "usernameOrEmail", required = false) String usernameOrEmail) {
		return usuarioRepository.findByNicknameOrEmail(usernameOrEmail, usernameOrEmail).map(user -> {
			UserDto toReturn = new UserDto();
			Set<String> roles = new HashSet<>();
			toReturn.setUserId(user.getIdUsuario());
			toReturn.setName(user.getNombre());
			toReturn.setLastName(user.getApellido());
			toReturn.setEmail(user.getEmail());
			toReturn.setNickname(user.getNickname());
			for (Rol r : user.getRoles()) {
				roles.add(r.getNombre().name());
			}
			toReturn.setRoles(roles);
			return toReturn;
		}).orElse(null);
	}

}
