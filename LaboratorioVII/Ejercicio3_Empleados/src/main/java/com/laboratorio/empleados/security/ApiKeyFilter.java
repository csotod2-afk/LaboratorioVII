package com.laboratorio.empleados.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.laboratorio.empleados.dto.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.json.JsonMapper;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

	private static final String CABECERA_API_KEY = "X-API-KEY";

	private final byte[] apiKeyEsperada;
	private final JsonMapper jsonMapper;

	public ApiKeyFilter(@Value("${app.api-key}") String apiKey, JsonMapper jsonMapper) {
		this.apiKeyEsperada = apiKey.getBytes(StandardCharsets.UTF_8);
		this.jsonMapper = jsonMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (esApiKeyValida(request.getHeader(CABECERA_API_KEY))) {
			filterChain.doFilter(request, response);
			return;
		}
		ErrorResponse error = ErrorResponse.de(HttpStatus.UNAUTHORIZED.value(), "No autorizado",
				"Debe enviar una API Key válida en la cabecera " + CABECERA_API_KEY);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getWriter().write(jsonMapper.writeValueAsString(error));
	}

	private boolean esApiKeyValida(String apiKeyRecibida) {
		return apiKeyRecibida != null
				&& MessageDigest.isEqual(apiKeyEsperada, apiKeyRecibida.getBytes(StandardCharsets.UTF_8));
	}
}
