# Ejercicio 3: API de empleados

API REST con Java y Spring Boot para administrar empleados. Los datos se guardan en una lista en memoria, por lo que se pierden al detener la aplicación.

## Ejecutar

```bash
./mvnw spring-boot:run   # inicia en http://localhost:8083
./mvnw test              # ejecuta las pruebas
```

## Autenticación

Todas las peticiones deben incluir la cabecera `X-API-KEY`. El valor por defecto para el laboratorio es `clave-laboratorio-2026` y se puede cambiar con la variable de entorno `API_KEY`. Sin una clave válida la API responde `401 Unauthorized`.

## Endpoints

| Método | Ruta | Descripción | Respuestas |
| --- | --- | --- | --- |
| POST | `/api/empleados` | Crear empleado | 201, 400, 401 |
| GET | `/api/empleados` | Listar empleados | 200, 401 |
| GET | `/api/empleados?especialidad=Sistemas` | Filtrar por especialidad | 200, 401 |
| GET | `/api/empleados/{id}` | Consultar por id | 200, 400, 401, 404 |

El campo `estado` acepta `ACTIVO` o `INACTIVO`; si se omite, se usa `ACTIVO`.

## Ejemplos

```bash
curl -i -X POST http://localhost:8083/api/empleados \
  -H "X-API-KEY: clave-laboratorio-2026" \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","apellido":"Lopez","correo":"ana@empresa.com","especialidad":"Sistemas"}'

curl -i -H "X-API-KEY: clave-laboratorio-2026" "http://localhost:8083/api/empleados?especialidad=Sistemas"

curl -i -H "X-API-KEY: clave-laboratorio-2026" http://localhost:8083/api/empleados/1
```
