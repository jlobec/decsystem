package es.udc.fic.decisionsystem.model.consulta;

public enum VisibilidadResultadoConsultaEnum {

	Public(1), Private(2), PrivateUntilClosed(3);

	private Integer idVisibilidadResultadoConsulta;

	private VisibilidadResultadoConsultaEnum(Integer idVisibilidadResultadoConsulta) {
		this.idVisibilidadResultadoConsulta = idVisibilidadResultadoConsulta;
	}

	public Integer getIdVisibilidadResultadoConsulta() {
		return idVisibilidadResultadoConsulta;
	}

}
