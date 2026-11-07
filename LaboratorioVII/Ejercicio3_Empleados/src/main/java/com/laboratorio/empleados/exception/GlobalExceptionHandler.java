package com.laboratorio.empleados.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.laboratorio.empleados.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String SOLICITUD_INVALIDA = "Solicitud inválida";

	@ExceptionHandler(EmpleadoNoEncontradoException.class)
	public ResponseEntity<ErrorResponse> manejarNoEncontrado(EmpleadoNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.de(404, "No encontrado", ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
		Map<String, String> detalles = new LinkedHashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			detalles.merge(error.getField(), error.getDefaultMessage(), (actual, nuevo) -> actual + "; " + nuevo);
		}
		return ResponseEntity.badRequest()
				.body(ErrorResponse.de(400, SOLICITUD_INVALIDA, "Los datos enviados no son válidos", detalles));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> manejarJsonIlegible(HttpMessageNotReadableException ex) {
		return ResponseEntity.badRequest().body(ErrorResponse.de(400, SOLICITUD_INVALIDA,
				"El cuerpo no es un JSON válido o el estado no es ACTIVO ni INACTIVO"));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
		return ResponseEntity.badRequest().body(ErrorResponse.de(400, SOLICITUD_INVALIDA,
				"El parámetro '" + ex.getName() + "' tiene un valor no válido"));
	}
}
