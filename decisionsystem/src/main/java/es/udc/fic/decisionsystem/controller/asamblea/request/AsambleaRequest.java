package es.udc.fic.decisionsystem.controller.asamblea.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsambleaRequest {

	@JsonProperty(value = "nombre")
	@Size(max = 100)
	@NotNull
	private String nombre;

	public AsambleaRequest() {
		super();
	}

	public AsambleaRequest(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
