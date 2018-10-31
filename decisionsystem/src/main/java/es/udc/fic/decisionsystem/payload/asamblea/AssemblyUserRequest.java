package es.udc.fic.decisionsystem.payload.asamblea;

import javax.validation.constraints.NotNull;

public class AssemblyUserRequest {

	@NotNull
	private Long userId;

	public AssemblyUserRequest() {
		super();
	}

	public AssemblyUserRequest(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	};

}
