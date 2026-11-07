# API de Profesores

API REST con Java y Spring Boot para administrar profesores en memoria.

## Ejecución

```bash
./mvnw spring-boot:run
./mvnw test
```

La API corre en `http://localhost:8081`. Todas las peticiones requieren la cabecera `X-API-KEY`.
La clave se toma de la variable de entorno `API_KEY`; si no existe, se usa `clave-laboratorio-2026`.

## Endpoints

| Método | Ruta | Descripción | Respuestas |
| --- | --- | --- | --- |
| POST | `/api/profesores` | Crea un profesor | 201, 400, 401 |
| GET | `/api/profesores` | Lista profesores | 200, 401 |
| GET | `/api/profesores?especialidad=Matematica` | Filtra por especialidad | 200, 401 |
| GET | `/api/profesores/{id}` | Consulta por id | 200, 400, 401, 404 |

## Ejemplos

```bash
curl -i -X POST http://localhost:8081/api/profesores \
  -H "X-API-KEY: clave-laboratorio-2026" -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","apellido":"Lopez","correo":"ana@correo.com","especialidad":"Matematica","estado":"ACTIVO"}'

curl -i -H "X-API-KEY: clave-laboratorio-2026" "http://localhost:8081/api/profesores?especialidad=Matematica"

curl -i -H "X-API-KEY: clave-laboratorio-2026" http://localhost:8081/api/profesores/1
```
