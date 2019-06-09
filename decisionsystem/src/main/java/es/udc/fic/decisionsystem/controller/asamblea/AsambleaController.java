/**
 * Copyright © 2019 Jesus Lopez Becerra (jesus.lopez.becerra@udc.es)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.udc.fic.decisionsystem.controller.asamblea;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consultaasamblea.ConsultaAsamblea;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.usuarioasamblea.UsuarioAsamblea;
import es.udc.fic.decisionsystem.payload.ApiResponse;
import es.udc.fic.decisionsystem.payload.asamblea.AssemblyPollRequest;
import es.udc.fic.decisionsystem.payload.asamblea.AssemblyResponse;
import es.udc.fic.decisionsystem.payload.asamblea.AssemblyUserRequest;
import es.udc.fic.decisionsystem.payload.asamblea.AssemblyUserResponse;
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

//	@GetMapping("/api/assembly")
//	public Page<Asamblea> getAsamblea(Pageable pageable) {
//		return asambleaRepository.findAll(pageable);
//	}

	@GetMapping("/api/assembly/{asambleaId}/users")
	public Page<AssemblyUserResponse> getAsambleaUsers(Pageable pageable, @PathVariable Integer asambleaId) {
		return usuarioRepository.findByIdAsamblea(pageable, asambleaId).map(u -> {
			AssemblyUserResponse user = new AssemblyUserResponse();
			user.setUserId(u.getIdUsuario());
			user.setName(u.getNombre());
			user.setLastName(u.getApellido());
			user.setNickname(u.getNickname());
			user.setEmail(u.getEmail());
			return user;
		});
	}

	@GetMapping("/api/assembly")
	public AssemblyResponse getPollAssembly(Pageable pageable,
			@RequestParam(value = "pollId", required = false) Long pollId) {
		List<AssemblyResponse> foundAssemblies = asambleaRepository.findByIdConsulta(pollId).stream().map(a -> {
			AssemblyResponse assembly = new AssemblyResponse();
			assembly.setAssemblyId(a.getIdAsamblea());
			assembly.setName(a.getNombre());
			assembly.setDescription(a.getDescripcion());
			assembly.setTimecreated(a.getFechaHoraAlta().getTime());
			long pollCount = consultaRepository.findByIdAsamblea(pageable, a.getIdAsamblea()).getTotalElements();
			long usersCount = usuarioRepository.findByIdAsamblea(pageable, a.getIdAsamblea()).getTotalElements();
			assembly.setPollCount(Math.toIntExact(pollCount));
			assembly.setMembersCount(Math.toIntExact(usersCount));
			return assembly;
		}).collect(Collectors.toList());

		if (foundAssemblies.size() == 1) {
			return foundAssemblies.get(0);
		}

		throw new ResourceNotFoundException("More than one assembly for poll not supported so far");
	}

	@GetMapping("/api/assembly/{asambleaId}/polls")
	public Page<Consulta> getAssemblyPolls(Pageable pageable, @PathVariable Integer asambleaId) {
		return consultaRepository.findByIdAsamblea(pageable, asambleaId);
	}

	@GetMapping("/api/assembly/mine")
	public Page<AssemblyResponse> getLoggedUserAssemblies(Pageable pageable, Principal principal) {
		Optional<Usuario> loggedUser = usuarioRepository.findByNickname(principal.getName());
		if (loggedUser.isPresent()) {
			Long userId = loggedUser.get().getIdUsuario();
			return asambleaRepository.findByUser(pageable, userId).map(a -> {
				AssemblyResponse assembly = new AssemblyResponse();
				assembly.setAssemblyId(a.getIdAsamblea());
				assembly.setName(a.getNombre());
				assembly.setDescription(a.getDescripcion());
				assembly.setTimecreated(a.getFechaHoraAlta().getTime());
				long pollCount = consultaRepository.findByIdAsamblea(pageable, a.getIdAsamblea()).getTotalElements();
				long usersCount = usuarioRepository.findByIdAsamblea(pageable, a.getIdAsamblea()).getTotalElements();
				assembly.setPollCount(Math.toIntExact(pollCount));
				assembly.setMembersCount(Math.toIntExact(usersCount));
				return assembly;
			});
		}
		// Actually unlikely
		throw new ResourceNotFoundException("No logged user");
	}

	@GetMapping("/api/assembly/{asambleaId}/user/{userId}/permissions")
	public String getUserPermissionsOnAssembly(Principal principal, @PathVariable Integer asambleaId,
			@PathVariable Long userId) {
		String role = "ROLE_USER";

		Usuario user = usuarioRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

		Asamblea assembly = asambleaRepository.findById(asambleaId)
				.orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));

		Optional<UsuarioAsamblea> userAssembly = usuarioAsambleaRepository.findByUsuarioAndAsamblea(user, assembly);

		if (userAssembly.isPresent()) {
			UsuarioAsamblea relation = userAssembly.get();
			if (relation.getEsAdministrador())
				return "ROLE_ASSEMBLY_ADMIN";
		}

		return role;

	}

//	@PostMapping("/api/assembly")
//	public Asamblea createAsamblea(@Valid @RequestBody Asamblea asamblea) {
//		return asambleaRepository.save(asamblea);
//	}

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

	@PostMapping("/api/assembly/{asambleaId}/makeuseradmin")
	public ResponseEntity<?> makeUserAdmin(@Valid @RequestBody AssemblyUserRequest addUserRequest,
			@PathVariable Integer asambleaId) {
		Asamblea assembly = asambleaRepository.findById(asambleaId)
				.orElseThrow(() -> new ResourceNotFoundException("Assembly not found with id " + asambleaId));

		Usuario user = usuarioRepository.findById(addUserRequest.getUserId()).orElseThrow(
				() -> new ResourceNotFoundException("User not found with id " + addUserRequest.getUserId()));

		return usuarioAsambleaRepository.findByUsuarioAndAsamblea(user, assembly).map(userAssembly -> {
			userAssembly.setEsAdministrador(Boolean.TRUE);
			usuarioAsambleaRepository.save(userAssembly);
			return ResponseEntity.ok().body(new ApiResponse(true,
					String.format("User %d is now an admin over assembly", addUserRequest.getUserId())));
		}).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + addUserRequest.getUserId()));
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

//	@PutMapping("/api/assembly/{asambleaId}")
//	public ResponseEntity<?> updateAsamblea(@Valid @RequestBody Asamblea asamblea, @PathVariable Integer asambleaId) {
//		return asambleaRepository.findById(asambleaId).map(foundAsamblea -> {
//			asamblea.setIdAsamblea(asambleaId);
//			asambleaRepository.save(asamblea);
//			return ResponseEntity.ok().build();
//		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));
//	}

	@DeleteMapping("/api/assembly/{asambleaId}")
	public ResponseEntity<?> deleteAsamblea(@PathVariable Integer asambleaId) {
		return asambleaRepository.findById(asambleaId).map(asamblea -> {
			asambleaRepository.delete(asamblea);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Asamblea not found with id " + asambleaId));
	}

}
