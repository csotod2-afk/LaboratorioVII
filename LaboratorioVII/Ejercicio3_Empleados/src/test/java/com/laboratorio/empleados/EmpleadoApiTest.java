package com.laboratorio.empleados;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(properties = "app.api-key=clave-de-prueba")
@AutoConfigureMockMvc
class EmpleadoApiTest {

	private static final String CABECERA = "X-API-KEY";
	private static final String CLAVE = "clave-de-prueba";
	private static final String RUTA = "/api/empleados";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void sinApiKeyRetorna401() throws Exception {
		mockMvc.perform(get(RUTA)).andExpect(status().isUnauthorized());
	}

	@Test
	void conApiKeyIncorrectaRetorna401() throws Exception {
		mockMvc.perform(get(RUTA).header(CABECERA, "otra-clave")).andExpect(status().isUnauthorized());
	}

	@Test
	void crearEmpleadoValidoRetorna201ConLocation() throws Exception {
		crear("""
				{"nombre":"Ana","apellido":"López","correo":"ana@empresa.com","especialidad":"Contabilidad"}
				""")
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString(RUTA + "/")))
				.andExpect(jsonPath("$.estado").value("ACTIVO"));
	}

	@Test
	void correoInvalidoRetorna400() throws Exception {
		crear("""
				{"nombre":"Ana","apellido":"López","correo":"ana@empresa","especialidad":"Contabilidad"}
				""")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detalles.correo").exists());
	}

	@Test
	void nombreVacioRetorna400() throws Exception {
		crear("""
				{"nombre":" ","apellido":"López","correo":"ana@empresa.com","especialidad":"Contabilidad"}
				""")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detalles.nombre").exists());
	}

	@Test
	void estadoInvalidoRetorna400() throws Exception {
		crear("""
				{"nombre":"Ana","apellido":"López","correo":"ana@empresa.com","especialidad":"Contabilidad","estado":"X"}
				""")
				.andExpect(status().isBadRequest());
	}

	@Test
	void idNoNumericoRetorna400() throws Exception {
		mockMvc.perform(get(RUTA + "/abc").header(CABECERA, CLAVE)).andExpect(status().isBadRequest());
	}

	@Test
	void empleadoInexistenteRetorna404() throws Exception {
		mockMvc.perform(get(RUTA + "/9999").header(CABECERA, CLAVE))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	private ResultActions crear(String cuerpo) throws Exception {
		return mockMvc.perform(post(RUTA).header(CABECERA, CLAVE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(cuerpo));
	}
}
