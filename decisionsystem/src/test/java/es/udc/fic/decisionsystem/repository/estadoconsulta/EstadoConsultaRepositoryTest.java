package es.udc.fic.decisionsystem.repository.estadoconsulta;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fic.decisionsystem.model.consulta.EstadoConsulta;
import es.udc.fic.decisionsystem.model.consulta.EstadoConsultaEnum;
import es.udc.fic.decisionsystem.repository.consulta.EstadoConsultaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test_setup.sql")
public class EstadoConsultaRepositoryTest {

	@Autowired
	private EstadoConsultaRepository estadoConsultaRepository;
	
	@Test
	@Transactional
	public void shouldFindPollStatusById(){
	
		Optional<EstadoConsulta> openState = estadoConsultaRepository.findById(EstadoConsultaEnum.Open.getIdEstadoConsulta());
		assertTrue(openState.isPresent());
	}
	
}
