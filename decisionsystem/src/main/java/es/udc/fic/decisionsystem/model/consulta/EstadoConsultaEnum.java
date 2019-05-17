package es.udc.fic.decisionsystem.model.consulta;

public enum EstadoConsultaEnum {

	Open(1), Closed(2);

	private Integer idEstadoConsulta;

	private EstadoConsultaEnum(Integer idEstadoConsulta) {
		this.idEstadoConsulta = idEstadoConsulta;
	}

	public Integer getIdEstadoConsulta() {
		return idEstadoConsulta;
	}

}
