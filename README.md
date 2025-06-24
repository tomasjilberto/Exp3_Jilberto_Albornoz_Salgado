# Proyecto: EcoMarket - Microservicios CRUD (Experiencia 3)

Este proyecto implementa una solución basada en microservicios usando **Spring Boot** para la gestión de productos, clientes y ventas en el contexto de una tienda ecológica.
Forma parte de la Evaluación 3 de la asignatura, centrada en pruebas unitarias e integración, documentación con OpenAPI y buenas prácticas en el desarrollo backend.

## Tecnologías y herramientas utilizadas

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- JUnit 5
- Maven
- Swagger / OpenAPI 3 (Springdoc)
- Git & GitHub

---

## Endpoints REST disponibles

###  Servicio Cliente
- `GET /clientes` - Listar todos los clientes
- `GET /clientes/{id}` - Obtener cliente por ID
- `POST /clientes` - Crear nuevo cliente
- `PUT /clientes/{id}` - Actualizar cliente
- `DELETE /clientes/{id}` - Eliminar cliente

###  Servicio Producto
- `GET /productos` - Listar productos
- `GET /productos/{id}` - Obtener producto por ID
- `POST /productos` - Crear producto
- `PUT /productos/{id}` - Actualizar producto
- `DELETE /productos/{id}` - Eliminar producto

###  Servicio Venta
- `GET /ventas` - Listar ventas
- `GET /ventas/{id}` - Obtener venta
- `POST /ventas` - Crear venta
- `PUT /ventas/{id}` - Actualizar venta
- `DELETE /ventas/{id}` - Eliminar venta
