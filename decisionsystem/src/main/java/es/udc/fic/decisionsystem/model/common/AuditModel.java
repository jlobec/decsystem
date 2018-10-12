package es.udc.fic.decisionsystem.model.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class AuditModel implements Serializable {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_alta", nullable = false, updatable = false)
	@CreatedDate
	private Date fechaHoraAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_modificacion", nullable = false)
	@LastModifiedDate
	private Date fechaHoraModificacion;

	public Date getFechaHoraAlta() {
		return fechaHoraAlta;
	}

	public void setFechaHoraAlta(Date fechaHoraAlta) {
		this.fechaHoraAlta = fechaHoraAlta;
	}

	public Date getFechaHoraModificacion() {
		return fechaHoraModificacion;
	}

	public void setFechaHoraModificacion(Date fechaHoraModificacion) {
		this.fechaHoraModificacion = fechaHoraModificacion;
	}

}
