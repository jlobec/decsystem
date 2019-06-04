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
package es.udc.fic.decisionsystem.service.comentario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.reaccion.Reaccion;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.comentario.CommentReactionResponse;
import es.udc.fic.decisionsystem.payload.comentario.CommentResponse;
import es.udc.fic.decisionsystem.payload.usuario.UserDto;

public class ComentarioUtil {

	
	public static CommentResponse buildCommentResponse(Comentario comment, Usuario loggedUser, List<Reaccion> reactions) {
		CommentResponse response = new CommentResponse();
		
		UserDto user = new UserDto();
		Set<String> roles = new HashSet<>();
		user.setUserId(comment.getUsuario().getIdUsuario());
		user.setName(comment.getUsuario().getNombre());
		user.setLastName(comment.getUsuario().getApellido());
		user.setEmail(comment.getUsuario().getEmail());
		user.setNickname(comment.getUsuario().getNickname());
		for (Rol r : comment.getUsuario().getRoles()) {
			roles.add(r.getNombre().name());
		}
		user.setRoles(roles);
		
		List<CommentReactionResponse> commentReactions = reactions.stream().map(r -> {
			CommentReactionResponse reactionResponse = new CommentReactionResponse();
			reactionResponse.setReactionId(r.getIdReaccion());
			reactionResponse.setReactionType(r.getTipoReaccion().getNombre());
			return reactionResponse;
		}).collect(Collectors.toList());
		
		// Logged user reactions
		boolean reactedByUser = reactions.stream().anyMatch(r -> {
			return (r.getUsuario().getIdUsuario() == loggedUser.getIdUsuario());
		});

		response.setCommentId(comment.getIdComentario());
		response.setPollId(comment.getConsulta().getIdConsulta());
		response.setUser(user);
		response.setContent(comment.getContenido());
		response.setRemoved(comment.getEliminado());
		response.setReactions(commentReactions);
		response.setReactedByUser(reactedByUser);
		return response;

	}
}
