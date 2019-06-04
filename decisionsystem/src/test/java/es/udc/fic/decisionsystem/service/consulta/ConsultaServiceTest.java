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
package es.udc.fic.decisionsystem.service.consulta;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consulta.EstadoConsulta;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consulta.EstadoConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaopcion.ConsultaOpcionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsultaServiceTest {

	@Autowired
	private ConsultaService consultaService;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private EstadoConsultaRepository estadoConsultaRepository;
	
	@Autowired
	private ConsultaOpcionRepository consultaOpcionRepository;
	
	@Test
	@Transactional
	public void shouldReturnPollById(){
		
		EstadoConsulta estadoConsulta = new EstadoConsulta();
		estadoConsulta.setNombre("Open");
		
		EstadoConsulta pollStatus = estadoConsultaRepository.save(estadoConsulta);
		
		Consulta poll = new Consulta();
		poll.setTitulo("test poll");
		poll.setDescripcion("test description");
		poll.setEstadoConsulta(pollStatus);
		
//		PollSummaryResponse poll = consultaService.getPollById(consultaId, user)
	}
	
}
