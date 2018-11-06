package es.udc.fic.decisionsystem.payload.consulta;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CreatePollRequest {

	@NotBlank
	@Size(max = 100)
	private String title;

	@NotBlank
	@Size(max = 500)
	private String description;

	@NotNull
	@FutureOrPresent
	private Timestamp startsAt;

	private Timestamp finishesAt;

	@NotNull
	@Positive
	private Integer idPollSystem;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(Timestamp startsAt) {
		this.startsAt = startsAt;
	}

	public Timestamp getFinishesAt() {
		return finishesAt;
	}

	public void setFinishesAt(Timestamp finishesAt) {
		this.finishesAt = finishesAt;
	}

	public Integer getIdPollSystem() {
		return idPollSystem;
	}

	public void setIdPollSystem(Integer idPollSystem) {
		this.idPollSystem = idPollSystem;
	}

}
