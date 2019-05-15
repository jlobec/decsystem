package es.udc.fic.decisionsystem.model.sistemaconsulta;

public enum SistemaConsultaEnum {

	SINGLE_OPTION("Single Option"),
	MULTIPLE_OPTION("Multiple Option"),
	SCORE_VOTE("Score vote");

	private String name;

	private SistemaConsultaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static SistemaConsultaEnum getByName(String name) {
		for (SistemaConsultaEnum el : values()) {
			if (el.getName().equalsIgnoreCase(name)) {
				return el;
			}
		}
		return null;
	}

}
