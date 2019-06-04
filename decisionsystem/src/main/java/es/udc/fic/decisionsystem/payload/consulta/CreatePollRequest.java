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
package es.udc.fic.decisionsystem.payload.consulta;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CreatePollRequest {

	@NotBlank
	@Size(max = 100)
	private String title;

	@NotBlank
	@Size(max = 500)
	private String description;

	@NotNull
	private Timestamp startTime;

	@NotNull
	private Timestamp endTime;

	@NotNull
	@Positive
	private Integer pollSystemId;

	@NotNull
	@Positive
	private Integer assemblyId;

	@NotNull
	@Positive
	private Integer resultsVisibilityId;

	private List<AddPollOptionRequest> pollOptions;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getPollSystemId() {
		return pollSystemId;
	}

	public void setPollSystemId(Integer pollSystemId) {
		this.pollSystemId = pollSystemId;
	}

	public Integer getAssemblyId() {
		return assemblyId;
	}

	public void setAssemblyId(Integer assemblyId) {
		this.assemblyId = assemblyId;
	}

	public Integer getResultsVisibilityId() {
		return resultsVisibilityId;
	}

	public void setResultsVisibilityId(Integer resultsVisibilityId) {
		this.resultsVisibilityId = resultsVisibilityId;
	}

	public List<AddPollOptionRequest> getPollOptions() {
		return pollOptions;
	}

	public void setPollOptions(List<AddPollOptionRequest> pollOptions) {
		this.pollOptions = pollOptions;
	}

}
