package es.udc.fic.decisionsystem.controller.sistemaconsulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;

@RestController
@RequestMapping("/api/sistemaconsulta")
public class SistemaConsultaController {

	@Autowired
	private SistemaConsultaRepository sistemaConsultaRepository;
	
	@GetMapping
    public Page<SistemaConsulta> getSistemaConsulta(Pageable pageable) {
        return sistemaConsultaRepository.findAll(pageable);
    }
}
