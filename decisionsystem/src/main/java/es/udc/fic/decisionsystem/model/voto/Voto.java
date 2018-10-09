package es.udc.fic.decisionsystem.model.voto;

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
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Entity
@Table(name = "voto")
public class Voto extends AuditModel {

	private static final long serialVersionUID = 5584945742867261055L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_voto")
	private Long idVoto;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_consulta_opcion", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ConsultaOpcion consultaOpcion;

	public Voto() {
		super();
	}

	public Voto(Long idVoto, Usuario usuario, ConsultaOpcion consultaOpcion) {
		super();
		this.idVoto = idVoto;
		this.usuario = usuario;
		this.consultaOpcion = consultaOpcion;
	}

	public Voto(Long idVoto, Usuario usuario) {
		super();
		this.idVoto = idVoto;
		this.usuario = usuario;
	}

	public Long getIdVoto() {
		return idVoto;
	}

	public void setIdVoto(Long idVoto) {
		this.idVoto = idVoto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ConsultaOpcion getConsultaOpcion() {
		return consultaOpcion;
	}

	public void setConsultaOpcion(ConsultaOpcion consultaOpcion) {
		this.consultaOpcion = consultaOpcion;
	}

	@Override
	public String toString() {
		return "Voto [idVoto=" + idVoto + ", usuario=" + usuario + ", consultaOpcion=" + consultaOpcion + "]";
	}

}
