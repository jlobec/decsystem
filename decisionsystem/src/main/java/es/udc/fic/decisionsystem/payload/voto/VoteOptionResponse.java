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
package es.udc.fic.decisionsystem.payload.voto;

public class VoteOptionResponse {

	private Long voteId;
	private Long optionId;
	private Integer preferenceValue;
	private String motivation;

	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public Integer getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(Integer preferenceValue) {
		this.preferenceValue = preferenceValue;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

}
