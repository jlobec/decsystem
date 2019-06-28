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
package es.udc.fic.decisionsystem.model.usuarioasamblea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Entity
@Table(name = "usuario_asamblea")
public class UsuarioAsamblea extends AuditModel {

	private static final long serialVersionUID = -7334724343015369023L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario_asamblea")
	private Long idUsuarioAsamblea;

	@Column(name = "es_administrador")
	private Boolean esAdministrador;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id_usuario", name = "idUsuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id_asamblea", name = "idAsamblea")
	private Asamblea asamblea;

	public UsuarioAsamblea() {
		super();
	}

	public UsuarioAsamblea(Long idUsuarioAsamblea, Boolean esAdministrador, Usuario usuario, Asamblea asamblea) {
		super();
		this.idUsuarioAsamblea = idUsuarioAsamblea;
		this.esAdministrador = esAdministrador;
		this.usuario = usuario;
		this.asamblea = asamblea;
	}

	public Long getIdUsuarioAsamblea() {
		return idUsuarioAsamblea;
	}

	public void setIdUsuarioAsamblea(Long idUsuarioAsamblea) {
		this.idUsuarioAsamblea = idUsuarioAsamblea;
	}

	public Boolean getEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(Boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Asamblea getAsamblea() {
		return asamblea;
	}

	public void setAsamblea(Asamblea asamblea) {
		this.asamblea = asamblea;
	}

}
