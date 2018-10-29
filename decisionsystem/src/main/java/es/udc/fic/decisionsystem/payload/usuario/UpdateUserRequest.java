package es.udc.fic.decisionsystem.payload.usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserRequest {

	@Size(max = 100)
	@NotBlank
	private String name;

	@Size(max = 100)
	@NotBlank
	private String lastname;

	public UpdateUserRequest() {
		super();
	}

	public UpdateUserRequest(@Size(max = 100) @NotBlank String name, @Size(max = 100) @NotBlank String lastname) {
		super();
		this.name = name;
		this.lastname = lastname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
