package es.udc.fic.decisionsystem.payload.consulta;

public class PollOptionResponse {

	private Long pollOptionId;
	private String name;
	private String description;

	public Long getPollOptionId() {
		return pollOptionId;
	}

	public void setPollOptionId(Long pollOptionId) {
		this.pollOptionId = pollOptionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
