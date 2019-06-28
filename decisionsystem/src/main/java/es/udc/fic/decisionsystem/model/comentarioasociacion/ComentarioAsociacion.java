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
package es.udc.fic.decisionsystem.model.comentarioasociacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.common.AuditModel;

@Entity
@Table(name = "comentario_asociacion")
public class ComentarioAsociacion extends AuditModel {

	private static final long serialVersionUID = 3067528582661775111L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_comentario_asociacion")
	private Long idComentarioAsociacion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_comentario_padre", referencedColumnName="id_comentario", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Comentario comentarioPadre;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_comentario_hijo", referencedColumnName="id_comentario", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Comentario comentarioHijo;

	@Column(name = "profundidad_nodo")
	@NotNull
	private Integer profundidadNodo;

	public ComentarioAsociacion() {
		super();
	}

	public ComentarioAsociacion(Long idComentarioAsociacion, Comentario comentarioPadre, Comentario comentarioHijo,
			@NotNull Integer profundidadNodo) {
		super();
		this.idComentarioAsociacion = idComentarioAsociacion;
		this.comentarioPadre = comentarioPadre;
		this.comentarioHijo = comentarioHijo;
		this.profundidadNodo = profundidadNodo;
	}

	public Long getIdComentarioAsociacion() {
		return idComentarioAsociacion;
	}

	public void setIdComentarioAsociacion(Long idComentarioAsociacion) {
		this.idComentarioAsociacion = idComentarioAsociacion;
	}

	public Comentario getComentarioPadre() {
		return comentarioPadre;
	}

	public void setComentarioPadre(Comentario comentarioPadre) {
		this.comentarioPadre = comentarioPadre;
	}

	public Comentario getComentarioHijo() {
		return comentarioHijo;
	}

	public void setComentarioHijo(Comentario comentarioHijo) {
		this.comentarioHijo = comentarioHijo;
	}

	public Integer getProfundidadNodo() {
		return profundidadNodo;
	}

	public void setProfundidadNodo(Integer profundidadNodo) {
		this.profundidadNodo = profundidadNodo;
	}

}
