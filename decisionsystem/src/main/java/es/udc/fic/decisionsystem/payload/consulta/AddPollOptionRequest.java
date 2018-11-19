package es.udc.fic.decisionsystem.payload.consulta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddPollOptionRequest {

	@Size(max = 100)
	@NotBlank
	private String name;

	@Size(max = 500)
	@NotNull
	private String description;

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

}
