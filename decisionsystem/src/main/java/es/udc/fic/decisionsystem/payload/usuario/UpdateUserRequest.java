package es.udc.fic.decisionsystem.payload.usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserRequest {

	@Size(max = 100)
	@NotBlank
	private String name;

	@Size(max = 100)
	@NotBlank
	private String lastName;

	@Size(max = 100)
	@NotBlank
	private String nickname;

	@Size(max = 100)
	@NotBlank
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
