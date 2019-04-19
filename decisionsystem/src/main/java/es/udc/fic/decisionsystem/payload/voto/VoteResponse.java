package es.udc.fic.decisionsystem.payload.voto;

import java.util.List;

public class VoteResponse {

	private List<VoteOptionResponse> options;
	private Long pollId;

	public List<VoteOptionResponse> getOptions() {
		return options;
	}

	public void setOptions(List<VoteOptionResponse> options) {
		this.options = options;
	}

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

}
