package com.laboratorio.profesores.dto;

import com.laboratorio.profesores.model.Estado;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfesorRequest(
		@NotBlank(message = "El nombre es obligatorio") String nombre,
		@NotBlank(message = "El apellido es obligatorio") String apellido,
		@NotBlank(message = "El correo es obligatorio")
		@Email(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "El correo no es válido") String correo,
		@NotBlank(message = "La especialidad es obligatoria") String especialidad,
		Estado estado) {
}
