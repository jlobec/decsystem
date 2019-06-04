package es.udc.fic.decisionsystem.payload.comentario;

public class CommentReactionResponse {

	private Long reactionId;
	private String reactionType;

	public Long getReactionId() {
		return reactionId;
	}

	public void setReactionId(Long reactionId) {
		this.reactionId = reactionId;
	}

	public String getReactionType() {
		return reactionType;
	}

	public void setReactionType(String reactionType) {
		this.reactionType = reactionType;
	}

}
