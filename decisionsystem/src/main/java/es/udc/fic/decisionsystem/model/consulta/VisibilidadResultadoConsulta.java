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
package es.udc.fic.decisionsystem.model.consulta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.udc.fic.decisionsystem.model.common.AuditModel;

@Entity
@Table(name = "visibilidad_resultado_consulta")
public class VisibilidadResultadoConsulta extends AuditModel {

	private static final long serialVersionUID = -8632880425570817366L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_visibilidad_resultado_consulta")
	private Integer idVisibilidadResultadoConsulta;

	@Column(name = "nombre")
	@NotNull
	@Size(max = 100)
	private String nombre;

	public Integer getIdVisibilidadResultadoConsulta() {
		return idVisibilidadResultadoConsulta;
	}

	public void setIdVisibilidadResultadoConsulta(Integer idVisibilidadResultadoConsulta) {
		this.idVisibilidadResultadoConsulta = idVisibilidadResultadoConsulta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "VisibilidadResultadoConsulta [idVisibilidadResultadoConsulta=" + idVisibilidadResultadoConsulta
				+ ", nombre=" + nombre + "]";
	}

}
