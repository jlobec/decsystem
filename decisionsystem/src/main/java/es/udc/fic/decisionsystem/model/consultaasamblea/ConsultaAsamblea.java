package es.udc.fic.decisionsystem.model.consultaasamblea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.common.AuditModel;
import es.udc.fic.decisionsystem.model.consulta.Consulta;

@Entity
@Table(name = "consulta_asamblea")
public class ConsultaAsamblea extends AuditModel {

	private static final long serialVersionUID = 548396886226767273L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_consulta_asamblea")
	private Long idConsultaAsamblea;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id_consulta", name = "idConsulta")
	private Consulta consulta;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id_asamblea", name = "idAsamblea")
	private Asamblea asamblea;

	public ConsultaAsamblea() {
		super();
	}

	public ConsultaAsamblea(Long idConsultaAsamblea, Consulta consulta, Asamblea asamblea) {
		super();
		this.idConsultaAsamblea = idConsultaAsamblea;
		this.consulta = consulta;
		this.asamblea = asamblea;
	}

	public Long getIdConsultaAsamblea() {
		return idConsultaAsamblea;
	}

	public void setIdConsultaAsamblea(Long idConsultaAsamblea) {
		this.idConsultaAsamblea = idConsultaAsamblea;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Asamblea getAsamblea() {
		return asamblea;
	}

	public void setAsamblea(Asamblea asamblea) {
		this.asamblea = asamblea;
	}

}
