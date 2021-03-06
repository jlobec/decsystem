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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.udc.fic.decisionsystem.model.consulta.Consulta;
import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.payload.consulta.PollSummaryResponse;
import es.udc.fic.decisionsystem.payload.consulta.resultados.PollResults;

/**
 * The Interface ConsultaService.
 */
public interface ConsultaService {

	/**
	 * Gets the poll by id.
	 *
	 * @param consultaId the consulta id
	 * @param user       the user
	 * @return the poll by id
	 */
	public PollSummaryResponse getPollById(Long consultaId, Usuario user);

	/**
	 * Gets the user polls.
	 *
	 * @param pageable the pageable
	 * @param user     the user
	 * @return the user polls
	 */
	public Page<PollSummaryResponse> getUserPolls(Pageable pageable, Usuario user, Integer pollTypeId, Integer pollStatusId);
	
	
	public PollSummaryResponse buildPollSummaryResponse(Consulta poll, Usuario user); 
	
	public List<PollResults> getResults(Long consultaId); 
}
