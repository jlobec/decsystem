package es.udc.fic.decisionsystem.payload.voto;

public class VoteOptionResponse {

	private Long voteId;
	private Long optionId;
	private Integer preferenceValue;
	private String motivation;

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public Integer getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(Integer preferenceValue) {
		this.preferenceValue = preferenceValue;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

}
