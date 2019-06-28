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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.udc.fic.decisionsystem.model.common.AuditModel;

@Entity
@Table(name = "sistema_consulta")
public class SistemaConsulta extends AuditModel {

	private static final long serialVersionUID = 8185651462412085881L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_sistema_consulta")
	private Integer idSistemaConsulta;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	public SistemaConsulta() {
		super();
	}

	public SistemaConsulta(Integer idSistemaConsulta, String nombre, String descripcion) {
		super();
		this.idSistemaConsulta = idSistemaConsulta;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Integer getIdSistemaConsulta() {
		return idSistemaConsulta;
	}

	public void setIdSistemaConsulta(Integer idSistemaConsulta) {
		this.idSistemaConsulta = idSistemaConsulta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "SistemaConsulta [idSistemaConsulta=" + idSistemaConsulta + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}

}
