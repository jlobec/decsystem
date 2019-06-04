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
package es.udc.fic.decisionsystem.repository.notificacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.decisionsystem.model.notificacion.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

	@Query("SELECT n FROM Notificacion n "
			+ "INNER JOIN Usuario u ON n.usuario = u "
			+ "WHERE u.idUsuario = :idUser AND n.enviada = false")
	List<Notificacion> findNotSentToUser(@Param("idUser") Long idUser);
	
	@Query("SELECT n FROM Notificacion n "
			+ "INNER JOIN Usuario u ON n.usuario = u "
			+ "WHERE u.idUsuario = :idUser AND n.vista = false")
	List<Notificacion> findPendingToSee(@Param("idUser") Long idUser);
	
	@Query("SELECT n FROM Notificacion n "
			+ "INNER JOIN Usuario u ON n.usuario = u "
			+ "WHERE u.idUsuario = :idUser ")
	List<Notificacion> findAllByUser(@Param("idUser") Long idUser);
	
}
