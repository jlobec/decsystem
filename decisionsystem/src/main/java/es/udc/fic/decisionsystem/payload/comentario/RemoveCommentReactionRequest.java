package es.udc.fic.decisionsystem.payload.comentario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RemoveCommentReactionRequest {

	@NotNull
	@Positive
	private Integer reactionTypeId;

	private Long userId;

	public Integer getReactionTypeId() {
		return reactionTypeId;
	}

	public void setReactionTypeId(Integer reactionTypeId) {
		this.reactionTypeId = reactionTypeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
