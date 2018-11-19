package es.udc.fic.decisionsystem.controller.consulta;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.model.util.DateUtil;
import es.udc.fic.decisionsystem.payload.consulta.CreatePollRequest;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;

@RestController
public class ConsultaController {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private SistemaConsultaRepository sistemaConsultaRepository;

	@GetMapping("/api/poll/{consultaId}")
	public Consulta getConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			return consulta;
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

	@GetMapping("/api/poll")
	public Page<Consulta> getConsulta(Pageable pageable) {
		return consultaRepository.findAll(pageable);
	}

	@PostMapping("/api/poll")
	public Consulta createConsulta(@Valid @RequestBody CreatePollRequest poll) {

		Optional<SistemaConsulta> pollSystem = sistemaConsultaRepository.findById(poll.getIdPollSystem());
		if (pollSystem.isPresent()) {
			Consulta newPoll = new Consulta();
			newPoll.setTitulo(poll.getTitle());
			newPoll.setDescripcion(poll.getDescription());
			newPoll.setFechaHoraInicio(DateUtil.toDate(poll.getStartsAt()));
			newPoll.setFechaHoraFin(DateUtil.toDate(poll.getFinishesAt()));
			newPoll.setSistemaConsulta(pollSystem.get());

			return consultaRepository.save(newPoll);
		} else {
			throw new ResourceNotFoundException("Poll system not found");
		}

	}

	@PutMapping("/api/poll/{consultaId}")
	public ResponseEntity<?> updateConsulta(@Valid @RequestBody Consulta consulta, @PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(foundConsulta -> {
			consulta.setIdConsulta(consultaId);
			consultaRepository.save(consulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

	@DeleteMapping("/api/poll/{consultaId}")
	public ResponseEntity<?> deleteConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			consultaRepository.delete(consulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

}
