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
package es.udc.fic.decisionsystem.model.notificacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Entity
@Table(name = "notificacion")
public class Notificacion extends AuditModel {

	private static final long serialVersionUID = 4686465374120161447L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_notificacion")
	private Long idNotificacion;

	@Column(name = "contenido")
	@Size(max = 300)
	private String contenido;

	@Column(name = "enviada")
	private boolean enviada;

	@Column(name = "vista")
	private boolean vista;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id_usuario", name = "idUsuario")
	private Usuario usuario;

	public Notificacion() {
		super();
	}

	public Notificacion(Long idNotificacion, @Size(max = 300) String contenido, boolean enviada, boolean vista,
			Usuario usuario) {
		super();
		this.idNotificacion = idNotificacion;
		this.contenido = contenido;
		this.enviada = enviada;
		this.vista = vista;
		this.usuario = usuario;
	}

	public boolean isEnviada() {
		return enviada;
	}

	public void setEnviada(boolean enviada) {
		this.enviada = enviada;
	}

	public Long getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isVista() {
		return vista;
	}

	public void setVista(boolean vista) {
		this.vista = vista;
	}

}
