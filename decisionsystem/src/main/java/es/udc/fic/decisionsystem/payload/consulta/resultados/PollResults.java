package es.udc.fic.decisionsystem.payload.consulta.resultados;

import java.util.List;

public class PollResults {

	private PollResultOption option;
	private List<PollResultsItem> items;

	public PollResultOption getOption() {
		return option;
	}

	public void setOption(PollResultOption option) {
		this.option = option;
	}

	public List<PollResultsItem> getItems() {
		return items;
	}

	public void setItems(List<PollResultsItem> items) {
		this.items = items;
	}

}
