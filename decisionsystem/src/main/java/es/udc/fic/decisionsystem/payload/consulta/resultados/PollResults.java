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
package es.udc.fic.decisionsystem.payload.consulta.resultados;

import java.util.List;

public class PollResults {

	private PollResultOption option;
	private List<PollResultsItem> items;

	public PollResultOption getOption() {
		return option;
	}

	public void setOption(PollResultOption option) {
		this.option = option;
	}

	public List<PollResultsItem> getItems() {
		return items;
	}

	public void setItems(List<PollResultsItem> items) {
		this.items = items;
	}

}
