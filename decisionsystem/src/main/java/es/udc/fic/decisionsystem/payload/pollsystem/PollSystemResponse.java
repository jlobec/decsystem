package es.udc.fic.decisionsystem.payload.pollsystem;

public class PollSystemResponse {

	private Integer pollTypeId;
	private String name;
	private String description;

	public Integer getPollTypeId() {
		return pollTypeId;
	}

	public void setPollTypeId(Integer pollTypeId) {
		this.pollTypeId = pollTypeId;
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
