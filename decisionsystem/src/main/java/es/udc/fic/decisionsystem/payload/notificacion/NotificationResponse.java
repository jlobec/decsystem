package es.udc.fic.decisionsystem.payload.notificacion;

public class NotificationResponse {

	private Long notificationId;
	private String content;
	private boolean checkedByUser;
	private boolean sentToUser;

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

	public boolean isCheckedByUser() {
		return checkedByUser;
	}

	public void setCheckedByUser(boolean checkedByUser) {
		this.checkedByUser = checkedByUser;
	}

	public boolean isSentToUser() {
		return sentToUser;
	}

	public void setSentToUser(boolean sentToUser) {
		this.sentToUser = sentToUser;
	}

}
