package es.udc.fic.decisionsystem.controller.notificacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.notificacion.Notificacion;
import es.udc.fic.decisionsystem.payload.notificacion.NotificationResponse;
import es.udc.fic.decisionsystem.repository.notificacion.NotificacionRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;

@RestController
public class NotificacionController {

	
	@Autowired
	private NotificacionRepository notificacionRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	@GetMapping("/api/notification")
	public List<NotificationResponse> getAsambleaUsers(@RequestParam(value = "userId", required = false) Long userId) {
		
		List<NotificationResponse> notifications = notificacionRepository.findUnSeenByUser(userId)
				.stream().map(n -> {
					NotificationResponse notification = new NotificationResponse();
					notification.setNotificationId(n.getIdNotificacion());
					notification.setContent(n.getContenido());
					return notification;
		}).collect(Collectors.toList());
		
		return notifications;
	}
	
	@PostMapping("/api/notification/{notificationId}/shown")
	public ResponseEntity<?> markNotificationAsShown(@PathVariable Long notificationId){
		
		Notificacion not = notificacionRepository.findById(notificationId)
		.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
		
		not.setVista(true);
		
		notificacionRepository.save(not);
		
		return ResponseEntity.ok().build();
	}
	
}
