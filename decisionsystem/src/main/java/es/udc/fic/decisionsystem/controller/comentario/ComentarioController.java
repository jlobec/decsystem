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
package es.udc.fic.decisionsystem.controller.comentario;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.BadRequestException;
import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.comentarioasociacion.ComentarioAsociacion;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.reaccion.Reaccion;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.tiporeaccion.TipoReaccion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.comentario.AddCommentReactionRequest;
import es.udc.fic.decisionsystem.payload.comentario.AddCommentReplyRequest;
import es.udc.fic.decisionsystem.payload.comentario.AddCommentRequest;
import es.udc.fic.decisionsystem.payload.comentario.CommentResponse;
import es.udc.fic.decisionsystem.payload.comentario.RemoveCommentReactionRequest;
import es.udc.fic.decisionsystem.payload.usuario.UserDto;
import es.udc.fic.decisionsystem.repository.comentario.ComentarioRepository;
import es.udc.fic.decisionsystem.repository.comentarioasociacion.ComentarioAsociacionRepository;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.reaccion.ReaccionRepository;
import es.udc.fic.decisionsystem.repository.tiporeaccion.TipoReaccionRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;
import es.udc.fic.decisionsystem.service.comentario.ComentarioUtil;

@RestController
public class ComentarioController {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private ComentarioAsociacionRepository comentarioAsociacionRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ReaccionRepository reaccionRepository;

	@Autowired
	private TipoReaccionRepository tipoReaccionRepository;

	@GetMapping("/api/comment/{comentarioId}")
	public CommentResponse getComentario(@PathVariable Long comentarioId) {
		return comentarioRepository.findById(comentarioId).map(comentario -> {
			CommentResponse response = new CommentResponse();

			UserDto user = new UserDto();
			Set<String> roles = new HashSet<>();
			user.setUserId(comentario.getUsuario().getIdUsuario());
			user.setName(comentario.getUsuario().getNombre());
			user.setLastName(comentario.getUsuario().getApellido());
			user.setEmail(comentario.getUsuario().getEmail());
			user.setNickname(comentario.getUsuario().getNickname());
			for (Rol r : comentario.getUsuario().getRoles()) {
				roles.add(r.getNombre().name());
			}
			user.setRoles(roles);

			response.setCommentId(comentario.getIdComentario());
			response.setPollId(comentario.getConsulta().getIdConsulta());
			response.setUser(user);
			response.setContent(comentario.getContenido());
			response.setRemoved(comentario.getEliminado());
			return response;
		}).orElseThrow(() -> new ResourceNotFoundException("Comment und with id " + comentarioId));
	}

	@GetMapping("/api/comment")
	public Page<CommentResponse> getComentario(Pageable pageable) {
		return comentarioRepository.findAll(pageable).map(comentario -> {
			CommentResponse response = new CommentResponse();

			UserDto user = new UserDto();
			Set<String> roles = new HashSet<>();
			user.setUserId(comentario.getUsuario().getIdUsuario());
			user.setName(comentario.getUsuario().getNombre());
			user.setLastName(comentario.getUsuario().getApellido());
			user.setEmail(comentario.getUsuario().getEmail());
			user.setNickname(comentario.getUsuario().getNickname());
			for (Rol r : comentario.getUsuario().getRoles()) {
				roles.add(r.getNombre().name());
			}
			user.setRoles(roles);

			response.setCommentId(comentario.getIdComentario());
			response.setPollId(comentario.getConsulta().getIdConsulta());
			response.setUser(user);
			response.setContent(comentario.getContenido());
			response.setRemoved(comentario.getEliminado());
			return response;
		});
	}

	@PostMapping("/api/comment")
	public CommentResponse createComentario(@Valid @RequestBody AddCommentRequest addCommentRequest) {
		Consulta consulta = consultaRepository.findById(addCommentRequest.getPollId())
				.orElseThrow(() -> new ResourceNotFoundException("Poll not found"));
		Usuario user = usuarioRepository.findById(addCommentRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Comentario commentToAdd = new Comentario();
		commentToAdd.setUsuario(user);
		commentToAdd.setConsulta(consulta);
		commentToAdd.setContenido(addCommentRequest.getContent());
		commentToAdd.setEliminado(false);

		// Add comment entity, then add comment relationship
		Comentario commentAdded = comentarioRepository.save(commentToAdd);

		ComentarioAsociacion commentRelationship = new ComentarioAsociacion();
		commentRelationship.setComentarioPadre(commentAdded);
		commentRelationship.setComentarioHijo(commentAdded);
		commentRelationship.setProfundidadNodo(0);

		comentarioAsociacionRepository.save(commentRelationship);

		// Return result
		CommentResponse response = new CommentResponse();

		UserDto userResponse = new UserDto();
		Set<String> roles = new HashSet<>();
		userResponse.setUserId(user.getIdUsuario());
		userResponse.setName(user.getNombre());
		userResponse.setLastName(user.getApellido());
		userResponse.setEmail(user.getEmail());
		userResponse.setNickname(user.getNickname());
		for (Rol r : user.getRoles()) {
			roles.add(r.getNombre().name());
		}
		userResponse.setRoles(roles);

		response.setCommentId(commentAdded.getIdComentario());
		response.setPollId(commentAdded.getConsulta().getIdConsulta());
		response.setUser(userResponse);
		response.setContent(commentAdded.getContenido());
		response.setRemoved(commentAdded.getEliminado());
		response.setReactions(new ArrayList<>());
		response.setReactedByUser(false);
		return response;

	}

	@PostMapping("/api/comment/{comentarioId}/reply")
	public CommentResponse replyComment(@Valid @RequestBody AddCommentReplyRequest addCommentRequest,
			@PathVariable Long comentarioId) {
		Comentario replyTo = comentarioRepository.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
		Consulta consulta = consultaRepository.findById(replyTo.getConsulta().getIdConsulta())
				.orElseThrow(() -> new ResourceNotFoundException("Poll not found"));
		Usuario user = usuarioRepository.findById(addCommentRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Comentario commentToAdd = new Comentario();
		commentToAdd.setUsuario(user);
		commentToAdd.setConsulta(consulta);
		commentToAdd.setEliminado(false);
		commentToAdd.setContenido(addCommentRequest.getContent());

		// Add comment entity, then add comment relationship
		Comentario commentAdded = comentarioRepository.save(commentToAdd);

		ComentarioAsociacion commentRelationship = new ComentarioAsociacion();
		commentRelationship.setComentarioPadre(replyTo);
		commentRelationship.setComentarioHijo(commentAdded);
		commentRelationship.setProfundidadNodo(0);

		comentarioAsociacionRepository.save(commentRelationship);

		// Return result
		CommentResponse response = new CommentResponse();

		UserDto userResponse = new UserDto();
		Set<String> roles = new HashSet<>();
		userResponse.setUserId(user.getIdUsuario());
		userResponse.setName(user.getNombre());
		userResponse.setLastName(user.getApellido());
		userResponse.setEmail(user.getEmail());
		userResponse.setNickname(user.getNickname());
		for (Rol r : user.getRoles()) {
			roles.add(r.getNombre().name());
		}
		userResponse.setRoles(roles);

		response.setCommentId(commentAdded.getIdComentario());
		response.setPollId(commentAdded.getConsulta().getIdConsulta());
		response.setUser(userResponse);
		response.setContent(commentAdded.getContenido());
		response.setRemoved(commentAdded.getEliminado());
		return response;

	}

	@PostMapping("/api/comment/{comentarioId}/reaction/add")
	public CommentResponse addReaction(@Valid @RequestBody AddCommentReactionRequest addReactionRequest,
			@PathVariable Long comentarioId, Principal principal) {

		Comentario comment = comentarioRepository.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
		Usuario usuario = usuarioRepository.findByNicknameOrEmail(principal.getName(), principal.getName())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		TipoReaccion reactionType = tipoReaccionRepository.findById(addReactionRequest.getReactionTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("Reaction type not found"));

		Boolean alreadyAdded = reaccionRepository.findByComentarioAndUsuario(comment, usuario).isPresent();
		if (alreadyAdded) {
			throw new BadRequestException("Reaction already added");
		}

		Reaccion reaction = new Reaccion();
		reaction.setComentario(comment);
		reaction.setTipoReaccion(reactionType);
		reaction.setUsuario(usuario);

		reaccionRepository.save(reaction);

		// Build response
		List<Reaccion> reactions = reaccionRepository.findByComentario(comment);
		return ComentarioUtil.buildCommentResponse(comment, usuario, reactions);
	}

	@PostMapping("/api/comment/{comentarioId}/reaction/remove")
	public CommentResponse removeReaction(@Valid @RequestBody RemoveCommentReactionRequest removeReactionRequest,
			@PathVariable Long comentarioId, Principal principal) {

		Comentario comment = comentarioRepository.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
		Usuario usuario = usuarioRepository.findByNicknameOrEmail(principal.getName(), principal.getName())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		TipoReaccion reactionType = tipoReaccionRepository.findById(removeReactionRequest.getReactionTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("Reaction type not found"));

		Optional<Reaccion> reaction = reaccionRepository.findByComentarioAndUsuario(comment, usuario);
		if (!reaction.isPresent()) {
			throw new BadRequestException("Not reacted yet");
		}

		reaccionRepository.delete(reaction.get());

		// Build response
		List<Reaccion> reactions = reaccionRepository.findByComentario(comment);
		return ComentarioUtil.buildCommentResponse(comment, usuario, reactions);
	}

//	@PutMapping("/api/comment/{comentarioId}")
//	public ResponseEntity<?> updateComentario(@Valid @RequestBody Comentario comentario,
//			@PathVariable Long comentarioId) {
//		return comentarioRepository.findById(comentarioId).map(foundComentario -> {
//			comentario.setIdComentario(comentarioId);
//			comentarioRepository.save(comentario);
//			return ResponseEntity.ok().build();
//		}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
//	}

	@DeleteMapping("/api/comment/{comentarioId}")
	public CommentResponse deleteComentario(@PathVariable Long comentarioId) {
		// Logical remove in case the comment has responses and the responses have
		// responses and so on
		CommentResponse res = comentarioRepository.findById(comentarioId).map(comentario -> {
			comentario.setEliminado(true);
			Comentario updated = comentarioRepository.save(comentario);
			Usuario user = updated.getUsuario();

			// Return result
			CommentResponse response = new CommentResponse();

			UserDto userResponse = new UserDto();
			Set<String> roles = new HashSet<>();
			userResponse.setUserId(user.getIdUsuario());
			userResponse.setName(user.getNombre());
			userResponse.setLastName(user.getApellido());
			userResponse.setEmail(user.getEmail());
			userResponse.setNickname(user.getNickname());
			for (Rol r : user.getRoles()) {
				roles.add(r.getNombre().name());
			}
			userResponse.setRoles(roles);

			response.setCommentId(updated.getIdComentario());
			response.setPollId(updated.getConsulta().getIdConsulta());
			response.setUser(userResponse);
			response.setContent(updated.getContenido());
			response.setRemoved(updated.getEliminado());
			return response;
		}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
		return res;
	}
}
