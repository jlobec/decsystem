package es.udc.fic.decisionsystem.model.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Clase de utilidades para manejar fechas.
 */
public class DateUtil {

	public static Date toDate(LocalDateTime dateToConvert) {
		return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date toDate(Timestamp dateToConvert) {
		return new Date(dateToConvert.getTime());
	}
}
