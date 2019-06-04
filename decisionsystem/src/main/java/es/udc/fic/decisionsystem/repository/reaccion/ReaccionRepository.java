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
package es.udc.fic.decisionsystem.repository.reaccion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.comentario.Comentario;
import es.udc.fic.decisionsystem.model.reaccion.Reaccion;
import es.udc.fic.decisionsystem.model.usuario.Usuario;

@Repository
public interface ReaccionRepository extends JpaRepository<Reaccion, Long> {

	List<Reaccion> findByComentario(Comentario comentario);
	
	Optional<Reaccion> findByComentarioAndUsuario(Comentario comentario, Usuario usuario);
	
	List<Reaccion> findAllByComentarioAndUsuario(Comentario comentario, Usuario usuario);
}
