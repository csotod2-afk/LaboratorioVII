package com.laboratorio.proveedores;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.laboratorio.proveedores.security.ApiKeyFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.api-key=clave-de-prueba")
class ProveedorApiTest {

    private static final String API_KEY = "clave-de-prueba";
    private static final String RUTA = "/api/proveedores";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sinApiKeyRetorna401() throws Exception {
        mockMvc.perform(get(RUTA))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void conApiKeyIncorrectaRetorna401() throws Exception {
        mockMvc.perform(get(RUTA).header(ApiKeyFilter.CABECERA_API_KEY, "clave-falsa"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void crearProveedorValidoRetorna201() throws Exception {
        String cuerpo = """
                {"nombre":"Ana","apellido":"Lopez","correo":"ana@correo.com","categoria":"Papeleria"}""";

        mockMvc.perform(post(RUTA)
                        .header(ApiKeyFilter.CABECERA_API_KEY, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    void crearProveedorConDatosInvalidosRetorna400() throws Exception {
        String cuerpo = """
                {"nombre":"","apellido":"Lopez","correo":"correo-invalido","categoria":"Papeleria"}""";

        mockMvc.perform(post(RUTA)
                        .header(ApiKeyFilter.CABECERA_API_KEY, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detalles.nombre").exists())
                .andExpect(jsonPath("$.detalles.correo").exists());
    }

    @Test
    void consultarProveedorInexistenteRetorna404() throws Exception {
        mockMvc.perform(get(RUTA + "/9999").header(ApiKeyFilter.CABECERA_API_KEY, API_KEY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
