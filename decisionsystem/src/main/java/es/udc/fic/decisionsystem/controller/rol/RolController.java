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
package es.udc.fic.decisionsystem.controller.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fic.decisionsystem.model.rol.Rol;
import es.udc.fic.decisionsystem.repository.rol.RolRepository;

@RestController
public class RolController {

	@Autowired
	private RolRepository roleRepository;

	@GetMapping("/api/rol")
	public Page<Rol> getRol(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}
//
//	@PostMapping("/api/rol")
//	public Rol createRol(@Valid @RequestBody Rol rol) {
//		return roleRepository.save(rol);
//	}

//	@DeleteMapping("/api/rol/{rolId}")
//	public ResponseEntity<?> deleteRol(@PathVariable Long rolId) {
//		return roleRepository.findById(rolId).map(rol -> {
//			roleRepository.delete(rol);
//			return ResponseEntity.ok().build();
//		}).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + rolId));
//	}
}
