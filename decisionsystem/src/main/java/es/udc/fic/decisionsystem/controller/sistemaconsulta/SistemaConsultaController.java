package es.udc.fic.decisionsystem.controller.sistemaconsulta;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.exception.ResourceNotFoundException;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;

@RestController
public class SistemaConsultaController {

	@Autowired
	private SistemaConsultaRepository sistemaConsultaRepository;
	
	@GetMapping("/api/pollsystem")
    public Page<SistemaConsulta> getSistemaConsulta(Pageable pageable) {
        return sistemaConsultaRepository.findAll(pageable);
    }
	
	@PostMapping("/api/pollsystem")
	public SistemaConsulta createAsamblea(@Valid @RequestBody SistemaConsulta sistemaConsulta) {
		return sistemaConsultaRepository.save(sistemaConsulta);
	}
	
	@DeleteMapping("/api/pollsystem/{sistConsultaId}")
	public ResponseEntity<?> deleteAsamblea(@PathVariable Integer sistConsultaId) {
        return sistemaConsultaRepository.findById(sistConsultaId)
                .map(sistConsulta -> {
                	sistemaConsultaRepository.delete(sistConsulta);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Sistema consulta not found with id " + sistConsultaId));
    }
}
