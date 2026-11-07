# API de Proveedores

API REST con Spring Boot para administrar proveedores en memoria. Puerto: `8082`.

## Ejecutar

```bash
./mvnw spring-boot:run
./mvnw test
```

Todas las peticiones requieren la cabecera `X-API-KEY`. La clave se define con la variable de entorno `API_KEY`; sin ella se usa el valor de laboratorio `clave-laboratorio-2026`.

## Endpoints

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| POST | `/api/proveedores` | Crear proveedor | 201, 400, 401 |
| GET | `/api/proveedores` | Listar proveedores | 200, 401 |
| GET | `/api/proveedores?categoria=X` | Filtrar por categoria | 200, 401 |
| GET | `/api/proveedores/{id}` | Consultar por id | 200, 400, 401, 404 |

## Ejemplos

```bash
curl -i -X POST http://localhost:8082/api/proveedores \
  -H "X-API-KEY: clave-laboratorio-2026" -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","apellido":"Lopez","correo":"ana@correo.com","categoria":"Papeleria","estado":"ACTIVO"}'

curl -i -H "X-API-KEY: clave-laboratorio-2026" "http://localhost:8082/api/proveedores?categoria=papeleria"

curl -i -H "X-API-KEY: clave-laboratorio-2026" http://localhost:8082/api/proveedores/1
```
