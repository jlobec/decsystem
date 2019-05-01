package es.udc.fic.decisionsystem.payload.comentario;

import es.udc.fic.decisionsystem.payload.usuario.UserDto;

public class CommentResponse {

	private Long commentId;
	private Long pollId;
	private UserDto user;
	private String content;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getPollId() {
		return pollId;
	}

	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
