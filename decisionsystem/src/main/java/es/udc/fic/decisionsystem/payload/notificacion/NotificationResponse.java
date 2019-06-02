package es.udc.fic.decisionsystem.payload.notificacion;

public class NotificationResponse {

	private Long notificationId;
	private String content;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
