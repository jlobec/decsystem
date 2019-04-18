package es.udc.fic.decisionsystem.payload.consulta;

import java.util.List;

public class PollSummaryResponse {

	private Long pollId;
	private String title;
	private String description;
	private Long startsAt;
	private Long endsAt;
	private String pollSystem;
	private List<PollOptionResponse> pollOptions;

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(Long startsAt) {
		this.startsAt = startsAt;
	}

	public Long getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(Long endsAt) {
		this.endsAt = endsAt;
	}

	public String getPollSystem() {
		return pollSystem;
	}

	public void setPollSystem(String pollSystem) {
		this.pollSystem = pollSystem;
	}

	public List<PollOptionResponse> getPollOptions() {
		return pollOptions;
	}

	public void setPollOptions(List<PollOptionResponse> pollOptions) {
		this.pollOptions = pollOptions;
	}

}
