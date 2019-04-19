package es.udc.fic.decisionsystem.payload.voto;

import java.util.List;

public class VoteRequest {

	private List<VoteOptionRequest> options;
	private Long pollId;

	public List<VoteOptionRequest> getOptions() {
		return options;
	}

	public void setOptions(List<VoteOptionRequest> options) {
		this.options = options;
	}

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

}
