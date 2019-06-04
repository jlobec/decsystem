/**
 * Copyright © 2019 Jesus Lopez Becerra (jesus.lopez.becerra@udc.es)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import es.udc.fic.decisionsystem.payload.pollsystem.PollSystemResponse;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;

@RestController
public class SistemaConsultaController {

	@Autowired
	private SistemaConsultaRepository sistemaConsultaRepository;

	@GetMapping("/api/pollsystem")
	public Page<PollSystemResponse> getSistemaConsulta(Pageable pageable) {
		return sistemaConsultaRepository.findAll(pageable).map(ps -> {
			PollSystemResponse res = new PollSystemResponse();
			res.setPollTypeId(ps.getIdSistemaConsulta());
			res.setName(ps.getNombre());
			res.setDescription(ps.getDescripcion());
			return res;
		});
	}

	@PostMapping("/api/pollsystem")
	public SistemaConsulta createPollSystem(@Valid @RequestBody SistemaConsulta sistemaConsulta) {
		return sistemaConsultaRepository.save(sistemaConsulta);
	}

	@DeleteMapping("/api/pollsystem/{sistConsultaId}")
	public ResponseEntity<?> deleteAsamblea(@PathVariable Integer sistConsultaId) {
		return sistemaConsultaRepository.findById(sistConsultaId).map(sistConsulta -> {
			sistemaConsultaRepository.delete(sistConsulta);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Sistema consulta not found with id " + sistConsultaId));
	}
}
