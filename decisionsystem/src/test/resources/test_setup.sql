INSERT INTO estado_consulta (id_estado_consulta, fecha_hora_alta, fecha_hora_modificacion, nombre) 
	values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Open');
INSERT INTO estado_consulta (id_estado_consulta, fecha_hora_alta, fecha_hora_modificacion, nombre) 
	values (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Closed');
	
INSERT INTO sistema_consulta (id_sistema_consulta, fecha_hora_alta, fecha_hora_modificacion, descripcion, nombre)
	values (1,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Simple single option poll', 'Single Option');
INSERT INTO sistema_consulta (id_sistema_consulta, fecha_hora_alta, fecha_hora_modificacion, descripcion, nombre)
	values (2,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Multiple choice poll', 'Multiple Option');
INSERT INTO sistema_consulta (id_sistema_consulta, fecha_hora_alta, fecha_hora_modificacion, descripcion, nombre)
	values (3,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Score vote poll', 'Score vote');
	

INSERT INTO visibilidad_resultado_consulta (id_visibilidad_resultado_consulta, fecha_hora_alta, fecha_hora_modificacion, nombre) 
	values (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Public');
INSERT INTO visibilidad_resultado_consulta (id_visibilidad_resultado_consulta, fecha_hora_alta, fecha_hora_modificacion, nombre) 
	values (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Private');
INSERT INTO visibilidad_resultado_consulta (id_visibilidad_resultado_consulta, fecha_hora_alta, fecha_hora_modificacion, nombre) 
	values (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'PrivateUntilClosed');
	
INSERT INTO rol (id_rol, nombre)
	values (1, 'ROLE_USER');
INSERT INTO rol (id_rol, nombre)
	values (2, 'ROLE_ADMIN');