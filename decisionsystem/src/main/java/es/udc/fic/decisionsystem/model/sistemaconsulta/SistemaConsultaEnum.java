package es.udc.fic.decisionsystem.model.sistemaconsulta;

public enum SistemaConsultaEnum {

	SINGLE_OTPTION("Single Option");

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
