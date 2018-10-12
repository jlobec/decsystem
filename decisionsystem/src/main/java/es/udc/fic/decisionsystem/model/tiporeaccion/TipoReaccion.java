package es.udc.fic.decisionsystem.model.tiporeaccion;

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
@Table(name = "tipo_reaccion")
public class TipoReaccion extends AuditModel {

	private static final long serialVersionUID = 2142376211107103985L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_tipo_reaccion")
	private Integer idTipoReaccion;

	@Column(name = "nombre")
	@Size(max = 100)
	@NotNull
	private String nombre;

	@Column(name = "descripcion")
	@Size(max = 200)
	private String descripcion;

	public TipoReaccion() {
		super();
	}

	public TipoReaccion(Integer idTipoReaccion, @Size(max = 100) @NotNull String nombre,
			@Size(max = 200) String descripcion) {
		super();
		this.idTipoReaccion = idTipoReaccion;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Integer getIdTipoReaccion() {
		return idTipoReaccion;
	}

	public void setIdTipoReaccion(Integer idTipoReaccion) {
		this.idTipoReaccion = idTipoReaccion;
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
