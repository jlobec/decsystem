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
package es.udc.fic.decisionsystem.model.sistemaconsulta;

public enum SistemaConsultaEnum {

	SINGLE_OPTION("Single Option"),
	MULTIPLE_OPTION("Multiple Option"),
	SCORE_VOTE("Score vote");

	private String name;

	private SistemaConsultaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static SistemaConsultaEnum getByName(String name) {
		for (SistemaConsultaEnum el : values()) {
			if (el.getName().equalsIgnoreCase(name)) {
				return el;
			}
		}
		return null;
	}

}
