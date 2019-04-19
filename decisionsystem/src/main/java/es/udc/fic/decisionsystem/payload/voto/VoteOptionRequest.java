package es.udc.fic.decisionsystem.payload.voto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VoteOptionRequest {

	@NotNull
	@Positive
	private Long optionId;

	private Integer preferenceValue;

	private String motivation;

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
