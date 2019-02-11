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
import es.udc.fic.decisionsystem.model.comentarioasociacion.ComentarioAsociacion;
import es.udc.fic.decisionsystem.model.reaccion.Reaccion;
import es.udc.fic.decisionsystem.model.tiporeaccion.TipoReaccion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.ApiResponse;
import es.udc.fic.decisionsystem.payload.comentario.AddCommentReactionRequest;
import es.udc.fic.decisionsystem.payload.comentario.AddCommentReplyRequest;
import es.udc.fic.decisionsystem.payload.comentario.AddCommentRequest;
import es.udc.fic.decisionsystem.repository.comentario.ComentarioRepository;
import es.udc.fic.decisionsystem.repository.comentarioasociacion.ComentarioAsociacionRepository;
import es.udc.fic.decisionsystem.repository.reaccion.ReaccionRepository;
import es.udc.fic.decisionsystem.repository.tiporeaccion.TipoReaccionRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;
import es.udc.fic.decisionsystem.repository.voto.VotoRepository;

@RestController
public class ComentarioController {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private ComentarioAsociacionRepository comentarioAsociacionRepository;

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ReaccionRepository reaccionRepository;

	@Autowired
	private TipoReaccionRepository tipoReaccionRepository;

	@GetMapping("/api/comment/{comentarioId}")
	public Comentario getComentario(@PathVariable Long comentarioId) {
		return comentarioRepository.findById(comentarioId).map(comentario -> {
			return comentario;
		}).orElseThrow(() -> new ResourceNotFoundException("Comment und with id " + comentarioId));
	}

	@GetMapping("/api/comment")
	public Page<Comentario> getComentario(Pageable pageable) {
		return comentarioRepository.findAll(pageable);
	}

	@PostMapping("/api/comment")
	public Comentario createComentario(@Valid @RequestBody AddCommentRequest addCommentRequest) {
		Voto vote = votoRepository.findById(addCommentRequest.getVoteId())
				.orElseThrow(() -> new ResourceNotFoundException("Vote not found"));
		Usuario user = usuarioRepository.findById(addCommentRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Comentario commentToAdd = new Comentario();
		commentToAdd.setUsuario(user);
		commentToAdd.setVoto(vote);
		commentToAdd.setContenido(addCommentRequest.getContent());

		// Add comment entity, then add comment relationship
		Comentario commentAdded = comentarioRepository.save(commentToAdd);

		ComentarioAsociacion commentRelationship = new ComentarioAsociacion();
		commentRelationship.setComentarioPadre(commentAdded);
		commentRelationship.setComentarioHijo(commentAdded);
		commentRelationship.setProfundidadNodo(0);

		comentarioAsociacionRepository.save(commentRelationship);

		return commentAdded;
	}

	@PostMapping("/api/comment/{comentarioId}/reply")
	public Comentario replyComment(@Valid @RequestBody AddCommentReplyRequest addCommentRequest,
			@PathVariable Long comentarioId) {
		Comentario replyTo = comentarioRepository.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
		Voto vote = votoRepository.findById(replyTo.getVoto().getIdVoto())
				.orElseThrow(() -> new ResourceNotFoundException("Vote not found"));
		Usuario user = usuarioRepository.findById(addCommentRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Comentario commentToAdd = new Comentario();
		commentToAdd.setUsuario(user);
		commentToAdd.setVoto(vote);
		commentToAdd.setContenido(addCommentRequest.getContent());

		// Add comment entity, then add comment relationship
		Comentario commentAdded = comentarioRepository.save(commentToAdd);

		ComentarioAsociacion commentRelationship = new ComentarioAsociacion();
		commentRelationship.setComentarioPadre(replyTo);
		commentRelationship.setComentarioHijo(commentAdded);
		commentRelationship.setProfundidadNodo(0);

		comentarioAsociacionRepository.save(commentRelationship);

		return commentAdded;
	}

	@PostMapping("/api/comment/{comentarioId}/reaction")
	public ResponseEntity<?> addReaction(@Valid @RequestBody AddCommentReactionRequest addReactionRequest,
			@PathVariable Long comentarioId) {
		Comentario comment = comentarioRepository.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
		Usuario user = usuarioRepository.findById(addReactionRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		TipoReaccion reactionType = tipoReaccionRepository.findById(addReactionRequest.getReactionTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("Reaction type not found"));

		Boolean alreadyAdded = reaccionRepository.findByComentarioAndUsuario(comment, user).isPresent();
		if (alreadyAdded) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Reaction already added"));
		}

		Reaccion reaction = new Reaccion();
		reaction.setComentario(comment);
		reaction.setTipoReaccion(reactionType);
		reaction.setUsuario(user);

		reaccionRepository.save(reaction);

		return ResponseEntity.ok().body(new ApiResponse(true, "Reaction added"));
	}

	@PutMapping("/api/comment/{comentarioId}")
	public ResponseEntity<?> updateComentario(@Valid @RequestBody Comentario comentario,
			@PathVariable Long comentarioId) {
		return comentarioRepository.findById(comentarioId).map(foundComentario -> {
			comentario.setIdComentario(comentarioId);
			comentarioRepository.save(comentario);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
	}

	@DeleteMapping("/api/comment/{comentarioId}")
	public ResponseEntity<?> deleteComentario(@PathVariable Long comentarioId) {
		return comentarioRepository.findById(comentarioId).map(comentario -> {
			comentarioRepository.delete(comentario);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + comentarioId));
	}
}
