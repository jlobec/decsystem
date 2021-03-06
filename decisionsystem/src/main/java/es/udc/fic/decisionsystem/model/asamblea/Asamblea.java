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
package es.udc.fic.decisionsystem.model.asamblea;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.usuarioasamblea.UsuarioAsamblea;

@Entity
@Table(name = "asamblea")
public class Asamblea extends AuditModel {

	private static final long serialVersionUID = -6191819484277658740L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_asamblea")
	private Integer idAsamblea;

	@Column(name = "nombre")
	@Size(max = 100)
	@NotNull
	private String nombre;

	@Column(name = "descripcion")
	@Size(max = 300)
	private String descripcion;

	public Asamblea() {
		super();
	}

	public Asamblea(Integer idAsamblea, @Size(max = 100) @NotNull String nombre) {
		super();
		this.idAsamblea = idAsamblea;
		this.nombre = nombre;
	}

	public Integer getIdAsamblea() {
		return idAsamblea;
	}

	public void setIdAsamblea(Integer idAsamblea) {
		this.idAsamblea = idAsamblea;
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
		return "Asamblea [idAsamblea=" + idAsamblea + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

}
