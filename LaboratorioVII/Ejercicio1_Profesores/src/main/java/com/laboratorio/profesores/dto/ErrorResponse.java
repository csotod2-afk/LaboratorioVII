package com.laboratorio.profesores.dto;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(Instant timestamp, int status, String error, String mensaje,
		Map<String, String> detalles) {

	public static ErrorResponse de(int status, String error, String mensaje, Map<String, String> detalles) {
		return new ErrorResponse(Instant.now(), status, error, mensaje, detalles);
	}
}
