package es.udc.fic.decisionsystem.payload.consulta;

import java.util.List;

import es.udc.fic.decisionsystem.payload.pollsystem.PollSystemResponse;

public class PollSummaryResponse {

	private Long pollId;
	private String title;
	private String description;
	private Long startsAt;
	private Long endsAt;
	private PollStatusResponse status;
	private boolean votedByUser;
	private PollSystemResponse pollSystem;
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

	public boolean isVotedByUser() {
		return votedByUser;
	}

	public void setVotedByUser(boolean votedByUser) {
		this.votedByUser = votedByUser;
	}

	public PollSystemResponse getPollSystem() {
		return pollSystem;
	}

	public void setPollSystem(PollSystemResponse pollSystem) {
		this.pollSystem = pollSystem;
	}

	public List<PollOptionResponse> getPollOptions() {
		return pollOptions;
	}

	public void setPollOptions(List<PollOptionResponse> pollOptions) {
		this.pollOptions = pollOptions;
	}

	public PollStatusResponse getStatus() {
		return status;
	}

	public void setStatus(PollStatusResponse status) {
		this.status = status;
	}

}
