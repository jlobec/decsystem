package es.udc.fic.decisionsystem.payload.consulta;

public class PollResultsVisibilityResponse {

	private Integer resultsVisibilityId;
	private String name;

	public Integer getResultsVisibilityId() {
		return resultsVisibilityId;
	}

	public void setResultsVisibilityId(Integer resultsVisibilityId) {
		this.resultsVisibilityId = resultsVisibilityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
