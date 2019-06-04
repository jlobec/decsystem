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
package es.udc.fic.decisionsystem.payload.notificacion;

public class NotificationResponse {

	private Long notificationId;
	private String content;
	private boolean checkedByUser;
	private boolean sentToUser;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isCheckedByUser() {
		return checkedByUser;
	}

	public void setCheckedByUser(boolean checkedByUser) {
		this.checkedByUser = checkedByUser;
	}

	public boolean isSentToUser() {
		return sentToUser;
	}

	public void setSentToUser(boolean sentToUser) {
		this.sentToUser = sentToUser;
	}

}
