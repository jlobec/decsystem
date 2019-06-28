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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;

@Entity
@Table(name = "consulta")
public class Consulta extends AuditModel {

	private static final long serialVersionUID = 1247628372306219970L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_consulta")
	private Long idConsulta;

	@Column(name = "titulo")
	@NotNull
	@Size(max = 100)
	private String titulo;

	@Column(name = "descripcion")
	@Size(max = 500)
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_inicio")
	@NotNull
	private Date fechaHoraInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_fin")
	private Date fechaHoraFin;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_sistema_consulta", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private SistemaConsulta sistemaConsulta;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_estado_consulta", nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private EstadoConsulta estadoConsulta;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_visibilidad_resultado_consulta", nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private VisibilidadResultadoConsulta visibilidadResultadoConsulta;

	public Consulta() {
		super();
	}

	public Consulta(Long idConsulta, String titulo, String descripcion, Date fechaHoraInicio, Date fechaHoraFin,
			SistemaConsulta sistemaConsulta) {
		super();
		this.idConsulta = idConsulta;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.sistemaConsulta = sistemaConsulta;
	}

	public Long getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public SistemaConsulta getSistemaConsulta() {
		return sistemaConsulta;
	}

	public void setSistemaConsulta(SistemaConsulta sistemaConsulta) {
		this.sistemaConsulta = sistemaConsulta;
	}

	public EstadoConsulta getEstadoConsulta() {
		return estadoConsulta;
	}

	public void setEstadoConsulta(EstadoConsulta estadoConsulta) {
		this.estadoConsulta = estadoConsulta;
	}

	public VisibilidadResultadoConsulta getVisibilidadResultadoConsulta() {
		return visibilidadResultadoConsulta;
	}

	public void setVisibilidadResultadoConsulta(VisibilidadResultadoConsulta visibilidadResultadoConsulta) {
		this.visibilidadResultadoConsulta = visibilidadResultadoConsulta;
	}

	@Override
	public String toString() {
		return "Consulta [idConsulta=" + idConsulta + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", fechaHoraInicio=" + fechaHoraInicio + ", fechaHoraFin=" + fechaHoraFin + ", sistemaConsulta="
				+ sistemaConsulta + ", estadoConsulta=" + estadoConsulta + ", visibilidadResultadoConsulta="
				+ visibilidadResultadoConsulta + "]";
	}

}
