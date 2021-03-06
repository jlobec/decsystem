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
package es.udc.fic.decisionsystem.repository.assembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fic.decisionsystem.model.asamblea.Asamblea;
import es.udc.fic.decisionsystem.repository.asamblea.AsambleaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssemblyRepositoryTest {

	@Autowired
	private AsambleaRepository asambleaRepository;

	@Test
	@Transactional
	public void shouldSaveAndFindAssembly() {
		Asamblea asamblea = new Asamblea();
		asamblea.setNombre("Test assembly");
		asamblea.setDescripcion("Test description");

		Asamblea saved = asambleaRepository.save(asamblea);

		Optional<Asamblea> optional = asambleaRepository.findById(saved.getIdAsamblea());

		// Should be present
		assertTrue(optional.isPresent());

		Asamblea fetched = optional.get();

		// Should be equals the previous saved
		assertEquals(saved.getIdAsamblea(), fetched.getIdAsamblea());
		assertEquals(saved.getNombre(), fetched.getNombre());
		assertEquals(saved.getDescripcion(), fetched.getDescripcion());
	}

}
