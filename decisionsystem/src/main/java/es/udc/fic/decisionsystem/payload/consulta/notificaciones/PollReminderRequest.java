package es.udc.fic.decisionsystem.payload.consulta.notificaciones;

public class PollReminderRequest {

	private Long pollId;
	private String reminderType;

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}

}
