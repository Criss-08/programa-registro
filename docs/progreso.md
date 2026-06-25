# Programa Registro - Registro de Progreso

## Objetivo del proyecto

Desarrollar una aplicación de gestión de registros utilizando:

* Java 24
* Spring Boot 4
* PostgreSQL
* Spring Data JPA / Hibernate
* Maven
* Git y GitHub

---

# Sesión 01 - Creación del proyecto

## Infraestructura

* Instalación y configuración de Java 24.
* Creación del proyecto Spring Boot mediante Spring Initializr.
* Configuración de Maven.
* Importación del proyecto en IntelliJ.

## Conceptos aprendidos

* Estructura básica de un proyecto Spring Boot.
* Clase principal con `@SpringBootApplication`.
* Método `main`.
* Inicio del servidor Tomcat embebido.

---

# Sesión 02 - PostgreSQL

## Base de datos

Base creada:

```sql
programa_registro
```

## Problemas resueltos

* Error de DataSource sin URL configurada.
* Error de credenciales incorrectas.
* Configuración de conexión Spring ↔ PostgreSQL.

## application.properties

Configurado:

* datasource.url
* datasource.username
* datasource.password

---

# Sesión 03 - Hibernate y JPA

## Configuración

Configurado Hibernate:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Conceptos aprendidos

* ORM (Object Relational Mapping).
* Hibernate.
* JPA.
* Generación automática de tablas.

## Resultado

Hibernate crea tablas automáticamente en PostgreSQL.

---

# Sesión 04 - Entidad EstadoTrabajo

## Entidad creada

EstadoTrabajo

Campos:

* id
* nombre

## Anotaciones aprendidas

### @Entity

Convierte una clase Java en una tabla SQL.

### @Id

Define la clave primaria.

### @GeneratedValue

Genera automáticamente IDs en la base de datos.

### GenerationType.IDENTITY

Delega la generación del ID a PostgreSQL.

## Conceptos aprendidos

* Constructor vacío requerido por Hibernate.
* Encapsulamiento.
* Getters y Setters.
* Uso de Long como tipo objeto.

## Resultado

Tabla creada automáticamente:

estado_trabajo

---

# Sesión 05 - Repository

## Repository creado

EstadoTrabajoRepository

``` java
extends JpaRepository<EstadoTrabajo, Long>
```

## Conceptos aprendidos

### JpaRepository

Proporciona operaciones CRUD automáticas.

### Inyección de dependencias

Spring crea e inyecta automáticamente el Repository.

### Métodos disponibles

* save()
* findAll()
* findById()
* delete()
* count()

---
# Sesión 06 - Primer registro en la base de datos
* Se genero el primer registro

| id | nombre     |
| -- |------------|
| 1  | Pendiente  |

* Se implemento CommandLineRunner.
* Se inserto primer registro mediante repository.save().
* Se verificar persistencia desde PostgreSQL.
* Proyecto en GitHub



---

---
# Sesion 7

## Objetivos alcanzados

- Creación de la capa Service.
- Implementación de EstadoTrabajoService.
- Comprensión de la inyección de dependencias.
- Creación de EstadoTrabajoController.
- Implementación del endpoint GET /estados.
- Consulta exitosa de datos desde PostgreSQL.
- Comprensión del flujo:

Cliente
↓
Controller
↓
Service
↓
Repository
↓
Hibernate
↓
PostgreSQL

# Sesion 8

## Objetivos alcanzados

- Implementación de GET /estados/{id}.
- Comprensión de @PathVariable.
- Comprensión básica de Optional.
- Implementación de PUT /estados/{id}.
- Actualización de registros existentes.
- Implementación de DELETE /estados/{id}.
- Uso de existsById y deleteById.
- Prueba completa del CRUD de EstadoTrabajo desde Postman.
- Verificación de cambios en PostgreSQL.

## Estado actual

La entidad EstadoTrabajo tiene CRUD completo:

- Crear estados.
- Listar todos los estados.
- Buscar estado por ID.
- Actualizar estado por ID.
- Eliminar estado por ID.
- 

# Sesión 9

## Objetivos alcanzados

* Se completó el CRUD de la entidad `EstadoTrabajo`.
* Se implementó `GET /estados/{id}` para buscar un estado por ID.
* Se implementó `PUT /estados/{id}` para actualizar un estado existente.
* Se implementó `DELETE /estados/{id}` para eliminar un estado por ID.
* Se comprendió el uso de `@PathVariable`.
* Se reforzó el uso de `@RequestBody`.
* Se comprendió mejor el uso de `Optional`.
* Se probó el CRUD completo desde Postman.
* Se verificó la modificación y eliminación de datos en PostgreSQL.
* Se comentó el `CommandLineRunner` para evitar creación automática de registros.
* Se reseteó el ID de la tabla en entorno de desarrollo.

## Entidad TipoTrabajo

Se creó la segunda entidad catálogo del proyecto: `TipoTrabajo`.

Estructura inicial:

* `id`
* `nombre`

Se implementaron las siguientes capas:

* `TipoTrabajo` como Entity.
* `TipoTrabajoRepository`.
* `TipoTrabajoService`.
* `TipoTrabajoController`.

Endpoints creados:

* `GET /tipos-trabajo`
* `GET /tipos-trabajo/{id}`
* `POST /tipos-trabajo`
* `PUT /tipos-trabajo/{id}`
* `DELETE /tipos-trabajo/{id}`

## Conceptos reforzados

* Arquitectura en capas.
* Patrón Controller → Service → Repository.
* Diferencia entre `POST`, `GET`, `PUT` y `DELETE`.
* Uso de `findAll()`.
* Uso de `findById()`.
* Uso de `save()` para crear y actualizar.
* Uso de `existsById()`.
* Uso de `deleteById()`.
* Relación entre endpoints HTTP y operaciones CRUD.
* Reinicio limpio de procesos cuando Postman o Spring quedan ejecutando versiones anteriores.

# Sesión 10

## Objetivos alcanzados

* Se refactorizaron las rutas de los controllers usando `@RequestMapping`.
* Se mejoró `EstadoTrabajoController`.
* Se mejoró `TipoTrabajoController`.
* Se eliminaron rutas repetidas en cada endpoint.
* Se mantuvieron las mismas URLs públicas para Postman.

## Mejoras en respuestas HTTP

Se incorporó `ResponseEntity` para devolver respuestas más profesionales.

### GET por ID

Antes se devolvía directamente un `Optional`.

Ahora:

* Si el registro existe: `200 OK` + JSON.
* Si el registro no existe: `404 Not Found`.

Aplicado en:

* `GET /estados/{id}`
* `GET /tipos-trabajo/{id}`

### PUT por ID

Ahora:

* Si el registro existe: `200 OK` + JSON actualizado.
* Si el registro no existe: `404 Not Found`.

Aplicado en:

* `PUT /estados/{id}`
* `PUT /tipos-trabajo/{id}`

### DELETE por ID

Antes devolvía `true` o `false`.

Ahora:

* Si el registro fue eliminado: `204 No Content`.
* Si el registro no existe: `404 Not Found`.

Aplicado en:

* `DELETE /estados/{id}`
* `DELETE /tipos-trabajo/{id}`

## Conceptos aprendidos/reforzados

* `@RequestMapping`.
* `ResponseEntity`.
* `ResponseEntity.ok(...)`.
* `ResponseEntity.notFound().build()`.
* `ResponseEntity.noContent().build()`.
* Diferencia entre devolver datos y devolver estados HTTP.
* Mejora progresiva de una API REST.
* Separación entre funcionalidad y calidad de respuesta.

## Estado actual

El proyecto tiene dos entidades catálogo completas y mejoradas:

### EstadoTrabajo

CRUD completo con respuestas HTTP profesionales.

### TipoTrabajo

CRUD completo con respuestas HTTP profesionales.



# Sesión 11

## Objetivos alcanzados

* Se agregaron validaciones básicas en las entidades catálogo.
* Se incorporó `@NotBlank` en el campo `nombre` de `EstadoTrabajo`.
* Se incorporó `@NotBlank` en el campo `nombre` de `TipoTrabajo`.
* Se incorporó `@Valid` en los métodos `POST` y `PUT` de ambos controllers.
* Se comprobó desde Postman que los datos inválidos devuelven `400 Bad Request`.
* Se confirmó que los datos válidos siguen funcionando correctamente.

## Manejo global de errores

Se creó una clase global para manejar errores de validación:

* `GlobalExceptionHandler`

Esta clase permite capturar errores generados por validaciones y devolver una respuesta JSON más clara.

Antes, Spring devolvía un error genérico o difícil de leer.

Ahora, cuando un campo como `nombre` llega vacío, la API responde con una estructura más entendible, por ejemplo:

```json
{
  "error": "Validacion",
  "mensaje": "Los datos enviados no son validos",
  "detalles": {
    "nombre": "El nombre del estado es obligatorio"
  }
}
```

## Conceptos incorporados

* `@NotBlank`
* `@Valid`
* `@RestControllerAdvice`
* `@ExceptionHandler`
* `MethodArgumentNotValidException`
* `ResponseEntity`
* `HttpStatus.BAD_REQUEST`
* `Map`
* `HashMap`
* Manejo centralizado de errores

## Estado actual del proyecto

El proyecto cuenta con dos entidades catálogo funcionales y mejoradas:

### EstadoTrabajo

* CRUD completo.
* Respuestas HTTP profesionales.
* Validación básica.
* Manejo claro de errores de validación.

### TipoTrabajo

* CRUD completo.
* Respuestas HTTP profesionales.
* Validación básica.
* Manejo claro de errores de validación.

# Sesión 12

## Objetivos alcanzados

* Se realizaron pruebas sobre el manejo global de errores de validación.
* Se confirmó que las validaciones `@Valid` y `@NotBlank` funcionaban correctamente.
* Se detectó que Spring devolvía el error genérico por defecto en lugar del JSON personalizado.
* Se revisó la clase `GlobalExceptionHandler`.
* Se identificó que la clase estaba fuera del paquete principal de la aplicación.
* Se movió `GlobalExceptionHandler` al paquete correcto:

```java
package com.cristian.programaregistro.exception;
```

* Se confirmó que la clase quedó dentro del árbol principal del proyecto:

```text
com.cristian.programaregistro
├── controller
├── entity
├── repository
├── service
└── exception
```

## Problema detectado

La clase `GlobalExceptionHandler` tenía lógica correcta, pero Spring Boot no la estaba detectando porque estaba ubicada fuera del paquete principal escaneado por la aplicación.

La aplicación principal se encuentra en:

```java
package com.cristian.programaregistro;
```

Spring Boot escanea automáticamente ese paquete y sus subpaquetes.

Por eso, una clase ubicada solamente en:

```java
package exception;
```

queda fuera del escaneo automático.

## Solución aplicada

Se movió la clase al paquete:

```java
com.cristian.programaregistro.exception
```

Después de reiniciar la aplicación, Spring detectó correctamente `@RestControllerAdvice`.

## Resultado de la prueba

Al enviar un `POST` inválido a:

```http
POST /tipos-trabajo
```

con el body:

```json
{
  "nombre": ""
}
```

la API respondió correctamente con un JSON personalizado:

```json
{
  "detalles": {
    "nombre": "El nombre del tipo de trabajo es Obligatorio"
  },
  "mensaje": "Los datos enviados no son validos",
  "error": "Validacion"
}
```

## Conceptos reforzados

* Escaneo automático de componentes en Spring Boot.
* Importancia de la estructura de paquetes.
* Diferencia entre package Java y ruta HTTP.
* `@RestControllerAdvice`.
* `@ExceptionHandler`.
* `MethodArgumentNotValidException`.
* Validaciones con `@Valid` y `@NotBlank`.
* Respuestas personalizadas para errores `400 Bad Request`.
* Diagnóstico de errores cuando el código parece correcto pero Spring no lo ejecuta.

## Lección importante

En Spring Boot no alcanza con que una clase esté bien escrita.

También debe estar dentro del paquete principal de la aplicación o dentro de uno de sus subpaquetes para que Spring pueda detectarla automáticamente.

## Estado actual del proyecto

* `EstadoTrabajo` tiene CRUD completo.
* `TipoTrabajo` tiene CRUD completo.
* Ambos controllers usan respuestas HTTP profesionales.
* Ambas entidades tienen validación básica.
* El manejo global de errores de validación funciona correctamente.

## Pendientes para la próxima sesión

* Revisar línea por línea `GlobalExceptionHandler` para entenderlo a fondo.
* Repasar `Map` y `HashMap`.
* Mejorar pequeños detalles de formato en los mensajes de error.
* Luego avanzar con la entidad `Cliente`.
