/**
 * Copyright © 2019 Jesus Lopez Becerra (jesus.lopez.becerra@udc.es)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.udc.fic.decisionsystem.payload.comentario;

import java.util.List;

import es.udc.fic.decisionsystem.payload.usuario.UserDto;

public class CommentResponse {

	private Long commentId;
	private Long pollId;
	private UserDto user;
	private String content;
	private Boolean removed;
	private List<CommentReactionResponse> reactions;
	private boolean reactedByUser;

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

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public List<CommentReactionResponse> getReactions() {
		return reactions;
	}

	public void setReactions(List<CommentReactionResponse> reactions) {
		this.reactions = reactions;
	}

	public boolean isReactedByUser() {
		return reactedByUser;
	}

	public void setReactedByUser(boolean reactedByUser) {
		this.reactedByUser = reactedByUser;
	}

}
