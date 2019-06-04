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
@Table(name = "estado_consulta")
public class EstadoConsulta extends AuditModel {

	private static final long serialVersionUID = 3541225514959793058L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_estado_consulta")
	private Integer idEstadoConsulta;

	@Column(name = "nombre")
	@NotNull
	@Size(max = 100)
	private String nombre;

	public EstadoConsulta() {
		super();
	}

	public EstadoConsulta(Integer idEstadoConsulta, @NotNull @Size(max = 100) String nombre) {
		super();
		this.idEstadoConsulta = idEstadoConsulta;
		this.nombre = nombre;
	}

	public Integer getIdEstadoConsulta() {
		return idEstadoConsulta;
	}

	public void setIdEstadoConsulta(Integer idEstadoConsulta) {
		this.idEstadoConsulta = idEstadoConsulta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "EstadoConsulta [idEstadoConsulta=" + idEstadoConsulta + ", nombre=" + nombre + "]";
	}

}
