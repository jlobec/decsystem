package es.udc.fic.decisionsystem.model.comentario;

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

import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Entity
@Table(name = "comentario")
public class Comentario extends AuditModel {

	private static final long serialVersionUID = 4712815544116394643L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_comentario")
	private Long idComentario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_consulta", nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Consulta consulta;

	@Column(name = "contenido")
	private String contenido;

	@Column(name = "eliminado")
	private Boolean eliminado;

	public Comentario() {
		super();
	}

	public Comentario(Long idComentario, Usuario usuario, String contenido) {
		super();
		this.idComentario = idComentario;
		this.usuario = usuario;
		this.contenido = contenido;
	}

	public Long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

}
