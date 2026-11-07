package com.laboratorio.proveedores.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String CABECERA_API_KEY = "X-API-KEY";

    private final byte[] apiKeyEsperada;

    public ApiKeyFilter(@Value("${app.api-key}") String apiKey) {
        this.apiKeyEsperada = apiKey.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String apiKeyRecibida = request.getHeader(CABECERA_API_KEY);
        if (esValida(apiKeyRecibida)) {
            filterChain.doFilter(request, response);
            return;
        }
        responderNoAutorizado(response);
    }

    private boolean esValida(String apiKeyRecibida) {
        return apiKeyRecibida != null
                && MessageDigest.isEqual(apiKeyEsperada, apiKeyRecibida.getBytes(StandardCharsets.UTF_8));
    }

    private void responderNoAutorizado(HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("""
                {"timestamp":"%s","status":%d,"error":"%s","mensaje":"API Key inválida o ausente","detalles":{}}"""
                .formatted(Instant.now(), status.value(), status.getReasonPhrase()));
    }
}
