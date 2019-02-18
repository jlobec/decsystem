package es.udc.fic.decisionsystem.payload.consulta;

public class PollSummaryResponse {

	private String pollTitle;
	private Long startedAt;
	private Long endsAt;

	public String getPollTitle() {
		return pollTitle;
	}

	public void setPollTitle(String pollTitle) {
		this.pollTitle = pollTitle;
	}

	public Long getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Long startedAt) {
		this.startedAt = startedAt;
	}

	public Long getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(Long endsAt) {
		this.endsAt = endsAt;
	}

}
