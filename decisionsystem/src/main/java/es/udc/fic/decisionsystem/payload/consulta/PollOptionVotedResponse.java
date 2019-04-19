package es.udc.fic.decisionsystem.payload.consulta;

public class PollOptionVotedResponse {

	private boolean voted;
	private Integer preferenceValue;
	private String motivation;

	public boolean isVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
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
