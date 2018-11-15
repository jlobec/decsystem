package es.udc.fic.decisionsystem.payload.asamblea;

import javax.validation.constraints.NotNull;

public class AssemblyPollRequest {

	@NotNull
	private Long pollId;

	public AssemblyPollRequest() {
		super();
	}

	public AssemblyPollRequest(@NotNull Long pollId) {
		super();
		this.pollId = pollId;
	}

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

}
