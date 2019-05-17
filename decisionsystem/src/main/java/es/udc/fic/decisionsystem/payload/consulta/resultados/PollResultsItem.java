package es.udc.fic.decisionsystem.payload.consulta.resultados;

import es.udc.fic.decisionsystem.payload.usuario.UserDto;

public class PollResultsItem {

	private UserDto user;
	private String motivation;
	private Integer score;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
