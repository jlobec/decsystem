package es.udc.fic.decisionsystem.model.consultaopcion;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.consulta.Consulta;

@Entity
@Table(name = "consulta_opcion")
public class ConsultaOpcion extends AuditModel {

	private static final long serialVersionUID = 9031688277005396701L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_consulta_opcion")
	private Long idConsultaOpcion;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_consulta", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Consulta consulta;

	@Column(name = "nombre")
	@Size(max = 100)
	@NotNull
	private String nombre;

	@Column(name = "descripcion")
	@Size(max = 500)
	@NotNull
	private String descripcion;

	public ConsultaOpcion() {
		super();
	}

	public ConsultaOpcion(Long idConsultaOpcion, Consulta consulta, @Size(max = 100) @NotNull String nombre,
			@Size(max = 500) @NotNull String descripcion) {
		super();
		this.idConsultaOpcion = idConsultaOpcion;
		this.consulta = consulta;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Long getIdConsultaOpcion() {
		return idConsultaOpcion;
	}

	public void setIdConsultaOpcion(Long idConsultaOpcion) {
		this.idConsultaOpcion = idConsultaOpcion;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
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

}
