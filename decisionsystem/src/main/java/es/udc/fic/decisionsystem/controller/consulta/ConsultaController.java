package es.udc.fic.decisionsystem.controller.consulta;

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
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;

@RestController
public class ConsultaController {

	@Autowired
	private ConsultaRepository consultaRepository;

	@GetMapping("/api/consulta/{consultaId}")
	public Consulta getConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			return consulta;
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

	@GetMapping("/api/consulta")
	public Page<Consulta> getConsulta(Pageable pageable) {
		return consultaRepository.findAll(pageable);
	}

	@PostMapping("/api/consulta")
	public Consulta createAsamblea(@Valid @RequestBody Consulta consulta) {
		return consultaRepository.save(consulta);
	}

	@PutMapping("/api/consulta/{consultaId}")
	public ResponseEntity<?> updateConsulta(@Valid @RequestBody Consulta consulta, @PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(foundConsulta -> {
			consulta.setIdConsulta(consultaId);
			consultaRepository.save(consulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

	@DeleteMapping("/api/consulta/{consultaId}")
	public ResponseEntity<?> deleteConsulta(@PathVariable Long consultaId) {
		return consultaRepository.findById(consultaId).map(consulta -> {
			consultaRepository.delete(consulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Consulta not found with id " + consultaId));
	}

}
