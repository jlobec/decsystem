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
package es.udc.fic.decisionsystem.model.reaccion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.tiporeaccion.TipoReaccion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Entity
@Table(name = "reaccion")
public class Reaccion extends AuditModel {

	private static final long serialVersionUID = -4832761364134878842L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_reaccion")
	private Long idReaccion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_reaccion", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private TipoReaccion tipoReaccion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_comentario", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Comentario comentario;

	public Reaccion() {
		super();
	}

	public Reaccion(Long idReaccion, TipoReaccion tipoReaccion, Usuario usuario, Comentario comentario) {
		super();
		this.idReaccion = idReaccion;
		this.tipoReaccion = tipoReaccion;
		this.usuario = usuario;
		this.comentario = comentario;
	}

	public Long getIdReaccion() {
		return idReaccion;
	}

	public void setIdReaccion(Long idReaccion) {
		this.idReaccion = idReaccion;
	}

	public TipoReaccion getTipoReaccion() {
		return tipoReaccion;
	}

	public void setTipoReaccion(TipoReaccion tipoReaccion) {
		this.tipoReaccion = tipoReaccion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

}
