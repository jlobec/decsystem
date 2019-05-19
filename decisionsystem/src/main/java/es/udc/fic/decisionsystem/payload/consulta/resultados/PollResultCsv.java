package es.udc.fic.decisionsystem.payload.consulta.resultados;

public class PollResultCsv {

	private String optionName;
	private String optionDescription;
	private String userName;
	private String userLastname;
	private String userEmail;
	private String userNickname;
	private Integer optionScore;

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionDescription() {
		return optionDescription;
	}

	public void setOptionDescription(String optionDescription) {
		this.optionDescription = optionDescription;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Integer getOptionScore() {
		return optionScore;
	}

	public void setOptionScore(Integer optionScore) {
		this.optionScore = optionScore;
	}

}
