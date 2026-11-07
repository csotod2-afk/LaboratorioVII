package com.laboratorio.profesores.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.laboratorio.profesores.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProfesorNoEncontradoException.class)
	public ResponseEntity<ErrorResponse> manejarNoEncontrado(ProfesorNoEncontradoException ex) {
		return respuesta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
		Map<String, String> detalles = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> detalles.putIfAbsent(error.getField(), error.getDefaultMessage()));
		return respuesta(HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos", detalles);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> manejarJsonIlegible(HttpMessageNotReadableException ex) {
		return respuesta(HttpStatus.BAD_REQUEST,
				"El cuerpo de la petición es inválido o contiene un estado distinto de ACTIVO o INACTIVO", Map.of());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
		return respuesta(HttpStatus.BAD_REQUEST, "El parámetro '" + ex.getName() + "' no tiene un formato válido",
				Map.of());
	}

	private ResponseEntity<ErrorResponse> respuesta(HttpStatus status, String mensaje, Map<String, String> detalles) {
		return ResponseEntity.status(status)
				.body(ErrorResponse.de(status.value(), status.getReasonPhrase(), mensaje, detalles));
	}
}
