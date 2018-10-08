package es.udc.fic.decisionsystem.model.usuarioasamblea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.udc.fic.decisionsystem.model.common.AuditModel;

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

	public UsuarioAsamblea() {
		super();
	}

	public UsuarioAsamblea(Long idUsuarioAsamblea, Boolean esAdministrador) {
		super();
		this.idUsuarioAsamblea = idUsuarioAsamblea;
		this.esAdministrador = esAdministrador;
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

	@Override
	public String toString() {
		return "UsuarioAsamblea [idUsuarioAsamblea=" + idUsuarioAsamblea + ", esAdministrador=" + esAdministrador + "]";
	}

}
