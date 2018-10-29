package es.udc.fic.decisionsystem.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank
	private String nicknameOrEmail;

	@NotBlank
	private String password;

	public String getNicknameOrEmail() {
		return nicknameOrEmail;
	}

	public void setNicknameOrEmail(String nicknameOrEmail) {
		this.nicknameOrEmail = nicknameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}