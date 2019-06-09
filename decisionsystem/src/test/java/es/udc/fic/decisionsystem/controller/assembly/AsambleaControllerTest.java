package es.udc.fic.decisionsystem.controller.assembly;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import es.udc.fic.decisionsystem.model.voto.Voto;
import es.udc.fic.decisionsystem.payload.usuario.LoginRequest;
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
import es.udc.fic.decisionsystem.repository.voto.VotoRepository;
import es.udc.fic.decisionsystem.service.consulta.ConsultaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test_setup.sql")
public class AsambleaControllerTest {

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

	@Autowired
	private VotoRepository voteRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	@Transactional
	public void givenTokenWhenPostGetPollAssemblyThenOk() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Consulta savedPoll = this.savePoll(EstadoConsultaEnum.Open, SistemaConsultaEnum.SINGLE_OPTION,
				VisibilidadResultadoConsultaEnum.Public, "test", "test desc", new Date(), new Date());
		this.addOptionToPoll(savedPoll, "Option1", "option 1 description");
		this.addOptionToPoll(savedPoll, "Option2", "option 2 description");
		Usuario savedUser = this.saveUsuario("name", "lastName", "email@email.com", "nickname", "passwd",
				RoleName.ROLE_USER);
		Asamblea savedAssembly = this.saveAssembly("testAssembly", "description");
		this.addUserToAssembly(savedUser, savedAssembly);
		this.addPollToAssembly(savedPoll, savedAssembly);

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setNicknameOrEmail("nickname");
		loginRequest.setPassword("passwd");

//		MvcResult result = mvc
//				.perform(MockMvcRequestBuilders.post("/api/user/auth/signin")
//						.content(mapper.writeValueAsString(loginRequest)))
//				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
//
//		String content = result.getResponse().getContentAsString();
//		JwtAuthenticationResponse authResponse = mapper.readValue(content, JwtAuthenticationResponse.class);
//
//		Pageable p = PageRequest.of(0, 20);
//		mvc.perform(MockMvcRequestBuilders.get(String.format("/api/assembly?pollId=%d", savedPoll.getIdConsulta()))
//				.header("Authorization", "Bearer " + authResponse.getAccessToken()).accept(MediaType.APPLICATION_JSON))
//				.andDo(print()).andExpect(status().isOk()).andExpect(status().isOk())
//				.andExpect(jsonPath("$.assemblyId", is(savedAssembly.getIdAsamblea())))
//				.andExpect(jsonPath("$.name", is(savedAssembly.getNombre())))
//				.andExpect(jsonPath("$.description", is(savedAssembly.getDescripcion())));

	}

	private void addVote(ConsultaOpcion option, Usuario user) {
		Voto v = new Voto();
		v.setConsultaOpcion(option);
		v.setUsuario(user);
		v.setMotivacion("");

		Voto savedVote = voteRepository.save(v);
	}

	private ConsultaOpcion addOptionToPoll(Consulta poll, String optionName, String optionDesc) {
		ConsultaOpcion option = new ConsultaOpcion();
		option.setConsulta(poll);
		option.setNombre(optionName);
		option.setDescripcion(optionDesc);
		return consultaOpcionRepository.save(option);
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
