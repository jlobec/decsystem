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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.consulta.EstadoConsulta;
import es.udc.fic.decisionsystem.model.consulta.EstadoConsultaEnum;
import es.udc.fic.decisionsystem.model.consulta.VisibilidadResultadoConsulta;
import es.udc.fic.decisionsystem.model.consulta.VisibilidadResultadoConsultaEnum;
import es.udc.fic.decisionsystem.model.consultaasamblea.ConsultaAsamblea;
import es.udc.fic.decisionsystem.model.consultaopcion.ConsultaOpcion;
import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.model.rol.RoleName;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsulta;
import es.udc.fic.decisionsystem.model.sistemaconsulta.SistemaConsultaEnum;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.model.usuarioasamblea.UsuarioAsamblea;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.payload.consulta.resultados.PollResults;
import es.udc.fic.decisionsystem.repository.asamblea.AsambleaRepository;
import es.udc.fic.decisionsystem.repository.consulta.ConsultaRepository;
import es.udc.fic.decisionsystem.repository.consulta.EstadoConsultaRepository;
import es.udc.fic.decisionsystem.repository.consulta.VisibilidadResultadoConsultaRepository;
import es.udc.fic.decisionsystem.repository.consultaasamblea.ConsultaAsambleaRepository;
import es.udc.fic.decisionsystem.repository.consultaopcion.ConsultaOpcionRepository;
import es.udc.fic.decisionsystem.repository.rol.RolRepository;
import es.udc.fic.decisionsystem.repository.sistemaconsulta.SistemaConsultaRepository;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;
import es.udc.fic.decisionsystem.repository.usuarioasamblea.UsuarioAsambleaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test_setup.sql")
public class ConsultaServiceTest {
	
	private static final Integer SINGLE_OPTION_SYSTEM_ID = 1;
	

	@Autowired
	private ConsultaService consultaService;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private EstadoConsultaRepository estadoConsultaRepository;

	@Autowired
	private ConsultaOpcionRepository consultaOpcionRepository;

	@Autowired
	private SistemaConsultaRepository sistemaConsultaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioAsambleaRepository usuarioAsambleaRepository;

	@Autowired
	private AsambleaRepository asambleaRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private VisibilidadResultadoConsultaRepository visibilidadResultadoConsultaRepository;

	@Autowired
	private ConsultaAsambleaRepository consultaAsambleaRepository;

	@Test
	@Transactional
	public void shouldSaveAndReturnPollById() {
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());

		PollSummaryResponse pollResponse = consultaService.getPollById(savedPoll.getIdConsulta(), null);

		// Check data
		assertEquals(savedPoll.getIdConsulta(), pollResponse.getPollId());
	}

	@Test
	@Transactional
	public void shouldReturnUserPollsWithNoFilters() {
		// Prepare data
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());
		Usuario savedUser = this.saveUsuario("name", "lastName", "email@email.com", "nickname", "passwd",
				RoleName.ROLE_USER);
		Asamblea savedAssembly = this.saveAssembly("testAssembly", "description");
		this.addUserToAssembly(savedUser, savedAssembly);
		this.addPollToAssembly(savedPoll, savedAssembly);

		// Execute operation
		Pageable p = PageRequest.of(0, 20);
		Integer pollTypeId = null;
		Integer pollStatusId = null;
		Page<PollSummaryResponse> pagedUserPolls = consultaService.getUserPolls(p, savedUser, pollTypeId, pollStatusId);
		List<PollSummaryResponse> userPolls = pagedUserPolls.getContent();

		// Check results
		// Only one poll for the user and it must be the one we have just saved
		assertTrue(userPolls.size() == 1);
		
		// The poll retrieved should be the one saved
		PollSummaryResponse userPoll = userPolls.get(0);
		assertEquals(userPoll.getPollId(), savedPoll.getIdConsulta());
		assertEquals(userPoll.getTitle(), savedPoll.getTitulo());
	}
	
	@Test
	@Transactional
	public void shouldReturnUserPollsWithOnlySystemFilter() {
		// Prepare data
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());
		Usuario savedUser = this.saveUsuario("name", "lastName", "email@email.com", "nickname", "passwd",
				RoleName.ROLE_USER);
		Asamblea savedAssembly = this.saveAssembly("testAssembly", "description");
		this.addUserToAssembly(savedUser, savedAssembly);
		this.addPollToAssembly(savedPoll, savedAssembly);

		// Execute operation
		Pageable p = PageRequest.of(0, 20);
		Integer pollTypeId = SINGLE_OPTION_SYSTEM_ID;
		Integer pollStatusId = null;
		Page<PollSummaryResponse> pagedUserPolls = consultaService.getUserPolls(p, savedUser, pollTypeId, pollStatusId);
		List<PollSummaryResponse> userPolls = pagedUserPolls.getContent();

		// Check results
		// Only one poll for the user and it must be the one we have just saved
		assertTrue(userPolls.size() == 1);
		
		// The poll retrieved should be the one saved
		PollSummaryResponse userPoll = userPolls.get(0);
		assertEquals(userPoll.getPollId(), savedPoll.getIdConsulta());
		assertEquals(userPoll.getTitle(), savedPoll.getTitulo());
	}
	
	@Test
	@Transactional
	public void shouldReturnUserPollsWithOnlyStatusFilter() {
		// Prepare data
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());
		Usuario savedUser = this.saveUsuario("name", "lastName", "email@email.com", "nickname", "passwd",
				RoleName.ROLE_USER);
		Asamblea savedAssembly = this.saveAssembly("testAssembly", "description");
		this.addUserToAssembly(savedUser, savedAssembly);
		this.addPollToAssembly(savedPoll, savedAssembly);

		// Execute operation
		Pageable p = PageRequest.of(0, 20);
		Integer pollTypeId = null;
		Integer pollStatusId = EstadoConsultaEnum.Open.getIdEstadoConsulta();
		Page<PollSummaryResponse> pagedUserPolls = consultaService.getUserPolls(p, savedUser, pollTypeId, pollStatusId);
		List<PollSummaryResponse> userPolls = pagedUserPolls.getContent();

		// Check results
		// Only one poll for the user and it must be the one we have just saved
		assertTrue(userPolls.size() == 1);
		
		// The poll retrieved should be the one saved
		PollSummaryResponse userPoll = userPolls.get(0);
		assertEquals(userPoll.getPollId(), savedPoll.getIdConsulta());
		assertEquals(userPoll.getTitle(), savedPoll.getTitulo());
	}

	@Test
	@Transactional
	public void emptyPollShouldNotGetResults() {
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());

		List<PollResults> results = consultaService.getResults(savedPoll.getIdConsulta());
		assertTrue(results.size() == 0);

	}
	
	@Test
	@Transactional
	public void pollWithNoVotedOptionsShouldGetEmptyResults() {
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());

		this.addOptionToPoll(savedPoll, "Option1", "option 1 description");
		this.addOptionToPoll(savedPoll, "Option2", "option 2 description");
		
		List<PollResults> results = consultaService.getResults(savedPoll.getIdConsulta());
		assertTrue(results.size() == 0);
	}
	
	
	private void addOptionToPoll(Consulta poll, String optionName, String optionDesc) {
		ConsultaOpcion option = new ConsultaOpcion();
		option.setConsulta(poll);
		option.setNombre(optionName);
		option.setDescripcion(optionDesc);
		consultaOpcionRepository.save(option);
	}

	private void addPollToAssembly(Consulta poll, Asamblea assembly) {
		ConsultaAsamblea assemblyPollRelation = new ConsultaAsamblea();
		assemblyPollRelation.setAsamblea(assembly);
		assemblyPollRelation.setConsulta(poll);
		consultaAsambleaRepository.save(assemblyPollRelation);
	}

	private void addUserToAssembly(Usuario user, Asamblea assembly) {
		UsuarioAsamblea userAssemblyRelation = new UsuarioAsamblea();
		userAssemblyRelation.setUsuario(user);
		userAssemblyRelation.setAsamblea(assembly);
		userAssemblyRelation.setEsAdministrador(Boolean.FALSE);
		usuarioAsambleaRepository.save(userAssemblyRelation);
	}

	private Asamblea saveAssembly(String name, String desc) {
		// Create assembly
		Asamblea as = new Asamblea();
		as.setNombre(name);
		as.setDescripcion(desc);
		return asambleaRepository.save(as);
	}

	private Usuario saveUsuario(String name, String lastName, String email, String nickname, String passwd,
			RoleName role) {
		// Create user
		Set<Rol> rol = new HashSet<>();

		Optional<Rol> rolUser = rolRepository.findByNombre(role);
		rol.add(rolUser.get());

		Usuario us = new Usuario();
		us.setNombre(name);
		us.setApellido(lastName);
		us.setEmail(email);
		us.setNickname(nickname);
		us.setPassword(passwd);
		us.setRoles(rol);

		return usuarioRepository.save(us);
	}

	private Consulta savePoll(EstadoConsultaEnum status, SistemaConsultaEnum pollSystem,
			VisibilidadResultadoConsultaEnum visibility, String title, String description, Date start, Date end) {
		Optional<EstadoConsulta> openState = estadoConsultaRepository.findById(status.getIdEstadoConsulta());
		Optional<SistemaConsulta> singleOptionSystem = sistemaConsultaRepository.findByNombre(pollSystem.getName());
		Optional<VisibilidadResultadoConsulta> publicVisibility = visibilidadResultadoConsultaRepository
				.findById(visibility.getIdVisibilidadResultadoConsulta());

		Consulta poll = new Consulta();
		poll.setTitulo(title);
		poll.setDescripcion(description);
		poll.setEstadoConsulta(openState.get());
		poll.setFechaHoraInicio(start);
		poll.setFechaHoraFin(end);
		poll.setSistemaConsulta(singleOptionSystem.get());
		poll.setVisibilidadResultadoConsulta(publicVisibility.get());

		return consultaRepository.save(poll);
	}

}
