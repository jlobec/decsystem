package es.udc.fic.decisionsystem.model.usuario;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.common.AuditModel;

@Entity
@Table(name = "usuario")
public class Usuario extends AuditModel {

	private static final long serialVersionUID = 472204311282263993L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "nombre")
	@Size(max = 100)
	private String nombre;

	@Column(name = "apellido")
	@Size(max = 100)
	private String apellido;

	@Column(name = "email")
	@Size(max = 200)
	private String email;

	@Column(name = "nickname")
	@Size(max = 100)
	@NotNull
	private String nickname;

	@Column(name = "salt")
	@NotNull
	private String salt;

	@ManyToMany
	@JoinTable(name = "usuario_asamblea", joinColumns = @JoinColumn(referencedColumnName = "id_usuario", name = "idUsuario"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id_asamblea", name = "idAsamblea"))
	private List<Asamblea> asambleas;

	public Usuario() {
		super();
	}

	public Usuario(Long idUsuario, @Size(max = 100) String nombre, @Size(max = 100) String apellido,
			@Size(max = 200) String email, @Size(max = 100) String nickname, @NotNull String salt) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.nickname = nickname;
		this.salt = salt;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Asamblea> getAsambleas() {
		return asambleas;
	}

	public void setAsambleas(List<Asamblea> asambleas) {
		this.asambleas = asambleas;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", nickname=" + nickname + ", salt=" + salt + "]";
	}

}
