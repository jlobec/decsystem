package es.udc.fic.decisionsystem.payload.asamblea;

public class AssemblyResponse {

	private Integer assemblyId;
	private String name;
	private String description;
	private Long timecreated;
	private Integer pollCount;
	private Integer membersCount;

	public Integer getAssemblyId() {
		return assemblyId;
	}

	public void setAssemblyId(Integer assemblyId) {
		this.assemblyId = assemblyId;
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

	public Long getTimecreated() {
		return timecreated;
	}

	public void setTimecreated(Long timecreated) {
		this.timecreated = timecreated;
	}

	public Integer getPollCount() {
		return pollCount;
	}

	public void setPollCount(Integer pollCount) {
		this.pollCount = pollCount;
	}

	public Integer getMembersCount() {
		return membersCount;
	}

	public void setMembersCount(Integer membersCount) {
		this.membersCount = membersCount;
	}

}
