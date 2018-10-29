package es.udc.fic.decisionsystem.model.usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.rol.Rol;

@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "nickname" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class Usuario extends AuditModel {

	private static final long serialVersionUID = 472204311282263993L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "nombre")
	@Size(max = 100)
	@NotBlank
	private String nombre;

	@Column(name = "apellido")
	@Size(max = 100)
	private String apellido;

	@Column(name = "email")
	@Size(max = 200)
	@NotBlank
	@Email
	private String email;

	@Column(name = "nickname")
	@Size(max = 100)
	@NotBlank
	private String nickname;

	@Column(name = "password")
	@NotBlank
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_asamblea", joinColumns = @JoinColumn(referencedColumnName = "id_usuario", name = "idUsuario"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id_asamblea", name = "idAsamblea"))
	private List<Asamblea> asambleas;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
	private Set<Rol> roles = new HashSet<>();

	public Usuario() {
		super();
	}

	public Usuario(@Size(max = 100) @NotBlank String nombre, @Size(max = 100) String apellido,
			@Size(max = 200) @NotBlank @Email String email, @Size(max = 100) @NotBlank String nickname,
			@NotBlank String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Asamblea> getAsambleas() {
		return asambleas;
	}

	public void setAsambleas(List<Asamblea> asambleas) {
		this.asambleas = asambleas;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", nickname=" + nickname + "]";
	}

}
