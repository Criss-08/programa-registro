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
com.cristian.programaregistro.exception;
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

# Sesión 13

## Objetivos alcanzados

* Se repasó en detalle la clase `GlobalExceptionHandler`.
* Se comprendió el flujo de errores de validación:

    * `@Valid`
    * `@NotBlank`
    * `MethodArgumentNotValidException`
    * `@RestControllerAdvice`
    * `@ExceptionHandler`
    * respuesta personalizada con `ResponseEntity`
* Se reforzó la diferencia entre errores normales de Spring y errores personalizados por la aplicación.
* Se comprendió la importancia de que las clases estén dentro del package principal escaneado por Spring Boot.

## Mejora aplicada en manejo de errores

Se reemplazó `HashMap` por `LinkedHashMap` en `GlobalExceptionHandler`.

Motivo:

* `HashMap` no garantiza el orden de los elementos.
* `LinkedHashMap` mantiene el orden en que se agregan las claves.

Con esto, la respuesta JSON de errores queda más ordenada y predecible:

```json
{
  "error": "Validacion",
  "mensaje": "Los datos enviados no son validos",
  "detalles": {
    "nombre": "..."
  }
}
```

## Inicio de entidad Cliente

Se creó la entidad `Cliente`, que representa a odontólogos, consultorios o terceros que envían trabajos al laboratorio.

Campos iniciales:

* `id`
* `nombre`
* `email`
* `telefono`
* `direccion`
* `observaciones`
* `activo`

Validaciones iniciales:

* `nombre` obligatorio con `@NotBlank`
* `email` con formato válido usando `@Email`

Se confirmó que Hibernate creó correctamente la tabla `cliente` en PostgreSQL.

## Capas creadas para Cliente

Se crearon las siguientes clases:

* `Cliente`
* `ClienteRepository`
* `ClienteService`

El `ClienteService` incluye métodos para:

* Obtener todos los clientes.
* Obtener cliente por ID.
* Guardar cliente.
* Actualizar cliente.
* Eliminar cliente.

## Conceptos reforzados

* Entity con más de dos campos.
* Validaciones en entidades.
* `@Email`.
* Campo `activo` como base para futuro borrado lógico.
* Repository con `JpaRepository`.
* Service como capa de lógica.
* Actualización de múltiples campos dentro de un método `actualizar`.
* Diferencia entre `clienteExistente` y `clienteActualizado`.

## Estado actual del proyecto

Entidades catálogo completas:

* `EstadoTrabajo`
* `TipoTrabajo`

Entidad de dominio iniciada:

* `Cliente`

Estado de `Cliente`:

* Entity creada.
* Tabla creada en PostgreSQL.
* Repository creado.
* Service creado.
* Falta Controller.
* Falta prueba desde Postman.

# Sesión 14

## Objetivos alcanzados

* Se creó `ClienteController`.
* Se implementaron los endpoints REST para `Cliente`.
* Se probó el CRUD completo desde Postman.

## Endpoints implementados

* `GET /clientes`
* `GET /clientes/{id}`
* `POST /clientes`
* `PUT /clientes/{id}`
* `DELETE /clientes/{id}`

## Validaciones comprobadas

Se confirmó que las validaciones funcionan correctamente en `Cliente`:

* `@NotBlank` para el campo `nombre`.
* `@Email` para el campo `email`.
* `@Valid` en `POST` y `PUT`.
* `GlobalExceptionHandler` devuelve errores claros cuando los datos no son válidos.

## Borrado lógico

Se modificó el comportamiento de `DELETE /clientes/{id}`.

Antes, el método podía eliminar físicamente el registro.

Ahora, el cliente no se borra de la base de datos. En su lugar, se actualiza:

```java
activo = false;
```

Resultado probado:

* `DELETE /clientes/{id}` devuelve `204 No Content`.
* Luego `GET /clientes/{id}` sigue encontrando el cliente.
* El cliente aparece con `activo: false`.

Esto permite conservar historial y evitar futuros problemas cuando otras entidades, como `Trabajo`, dependan de `Cliente`.

## Estado actual de Cliente

* Entity creada.
* Repository creado.
* Service creado.
* Controller creado.
* CRUD completo.
* Validaciones funcionales.
* Manejo global de errores funcional.
* Borrado lógico implementado.

# Sesión 15

## Objetivos alcanzados

* Se mejoró la lógica de clientes activos e inactivos.
* Se adaptó el borrado lógico para que sea usable dentro de la API.
* Se modificó el listado principal de clientes para mostrar solo clientes activos.
* Se agregó un endpoint para consultar clientes inactivos.
* Se agregó un endpoint para reactivar clientes dados de baja.
* Se realizaron pruebas desde Postman.
* Se confirmó que todo funciona correctamente.
* Se realizó commit y push de los cambios.

## Cambios implementados

### ClienteRepository

Se agregaron métodos personalizados usando Spring Data JPA:

```java
List<Cliente> findByActivoTrue();

List<Cliente> findByActivoFalse();
```

Estos métodos permiten consultar clientes según el valor del campo `activo`.

## ClienteService

Se modificó el método `obtenerTodos()` para devolver solo clientes activos.

También se agregó:

```java
public List<Cliente> obtenerInactivos()
```

para listar clientes dados de baja.

Se agregó además:

```java
public Optional<Cliente> reactivar(Long id)
```

para volver a activar un cliente existente.

## ClienteController

Se agregaron nuevos endpoints:

```http
GET /clientes/inactivos
```

Devuelve clientes con `activo = false`.

```http
PUT /clientes/{id}/reactivar
```

Reactiva un cliente, cambiando `activo` a `true`.

## Comportamiento actual de Cliente

```http
GET /clientes
```

Devuelve solo clientes activos.

```http
GET /clientes/inactivos
```

Devuelve solo clientes inactivos.

```http
GET /clientes/{id}
```

Devuelve un cliente por ID, esté activo o inactivo.

```http
DELETE /clientes/{id}
```

No elimina físicamente el cliente. Hace borrado lógico:

```java
activo = false;
```

```http
PUT /clientes/{id}/reactivar
```

Reactiva el cliente:

```java
activo = true;
```

## Conceptos reforzados

* Borrado lógico.
* Listado filtrado por estado.
* Métodos derivados de Spring Data JPA.
* Uso de nombres como `findByActivoTrue()` y `findByActivoFalse()`.
* Reactivación de registros.
* Diseño de endpoints REST más cercanos a un sistema real.
* Conservación de historial para futuras relaciones como `Cliente -> Trabajo`.

## Estado actual del proyecto

### EstadoTrabajo

* CRUD completo.
* Validaciones.
* Manejo global de errores.

### TipoTrabajo

* CRUD completo.
* Validaciones.
* Manejo global de errores.

### Cliente

* CRUD completo.
* Validaciones.
* Manejo global de errores.
* Borrado lógico.
* Listado de activos.
* Listado de inactivos.
* Reactivación de clientes.

# Sesión 16

## Objetivos alcanzados

* Se creó la entidad `Paciente`.
* Se relacionó `Paciente` con `Cliente`.
* Se creó `PacienteRepository`.
* Se creó `PacienteService`.
* Se creó `PacienteController`.
* Se implementó CRUD completo para `Paciente`.
* Se agregaron validaciones básicas.
* Se implementó borrado lógico.
* Se implementó reactivación de pacientes.
* Se agregó listado de pacientes activos.
* Se agregó listado de pacientes inactivos.
* Se agregó listado de pacientes por cliente.
* Se probaron los endpoints desde Postman.
* Se realizó commit y push del avance.

## Entidad Paciente

La entidad `Paciente` representa a una persona asociada a un cliente/odontólogo/consultorio.

Campos implementados:

* `id`
* `nombre`
* `apellido`
* `observaciones`
* `activo`
* `cliente`

Validaciones:

* `nombre` obligatorio con `@NotBlank`.
* `cliente` obligatorio con `@NotNull`.

## Relación con Cliente

Se implementó la relación:

```text
Cliente 1 ---- N Paciente
```

Esto significa:

* Un cliente puede tener muchos pacientes.
* Un paciente pertenece a un único cliente.

En código se implementó con:

```java
@ManyToOne
@JoinColumn(name = "cliente_id", nullable = false)
private Cliente cliente;
```

Hibernate creó en la tabla `paciente` una columna `cliente_id`, que funciona como clave foránea hacia la tabla `cliente`.

## PacienteRepository

Se creó `PacienteRepository` extendiendo `JpaRepository`.

Además de los métodos básicos heredados, se agregaron métodos personalizados:

```java
List<Paciente> findByActivoTrue();

List<Paciente> findByActivoFalse();

List<Paciente> findByClienteIdAndActivoTrue(Long clienteId);
```

Estos métodos permiten:

* Listar pacientes activos.
* Listar pacientes inactivos.
* Listar pacientes activos pertenecientes a un cliente específico.

## PacienteService

Se creó `PacienteService` como capa de lógica.

A diferencia de entidades anteriores, este service usa dos repositories:

* `PacienteRepository`
* `ClienteRepository`

Esto es necesario porque para guardar o actualizar un paciente primero se verifica que el cliente asociado exista.

Métodos implementados:

* `obtenerTodos()`
* `obtenerInactivos()`
* `obtenerPorCliente(Long clienteId)`
* `obtenerPorId(Long id)`
* `guardar(Paciente paciente)`
* `actualizar(Long id, Paciente pacienteActualizado)`
* `eliminar(Long id)`
* `reactivar(Long id)`

## Validación de Cliente al guardar Paciente

Para crear un paciente, el JSON debe incluir un cliente existente:

```json
{
  "nombre": "Juan",
  "apellido": "Gomez",
  "observaciones": "Paciente de prueba",
  "activo": true,
  "cliente": {
    "id": 1
  }
}
```

El service valida que:

* El paciente tenga un cliente.
* El cliente tenga ID.
* El cliente exista en la base de datos.

Si el cliente existe, se asocia correctamente al paciente antes de guardar.

## PacienteController

Se creó `PacienteController` con ruta base:

```http
/pacientes
```

Endpoints implementados:

```http
GET /pacientes
```

Lista pacientes activos.

```http
GET /pacientes/inactivos
```

Lista pacientes inactivos.

```http
GET /pacientes/cliente/{clienteId}
```

Lista pacientes activos pertenecientes a un cliente específico.

```http
GET /pacientes/{id}
```

Busca un paciente por ID.

```http
POST /pacientes
```

Crea un paciente.

```http
PUT /pacientes/{id}
```

Actualiza un paciente existente.

```http
DELETE /pacientes/{id}
```

Aplica borrado lógico, cambiando `activo` a `false`.

```http
PUT /pacientes/{id}/reactivar
```

Reactiva un paciente, cambiando `activo` a `true`.

## Borrado lógico

Al igual que con `Cliente`, `Paciente` no se borra físicamente de la base de datos.

Cuando se ejecuta:

```http
DELETE /pacientes/{id}
```

el sistema hace:

```java
activo = false;
```

Esto permite conservar historial para futuras entidades como `Trabajo`.

## Reactivación

Se agregó la posibilidad de reactivar pacientes dados de baja:

```http
PUT /pacientes/{id}/reactivar
```

Esto cambia:

```java
activo = true;
```

y el paciente vuelve a aparecer en el listado principal.

## Conceptos nuevos incorporados

* Relación `@ManyToOne`.
* Uso de `@JoinColumn`.
* Clave foránea `cliente_id`.
* Relación entre entidades JPA.
* Uso de dos repositories dentro de un mismo service.
* Validación de existencia de una entidad relacionada antes de guardar.
* Métodos derivados más avanzados de Spring Data JPA.
* `findByClienteIdAndActivoTrue(Long clienteId)`.
* Uso de `flatMap` en actualización con relación.
* CRUD con entidad relacionada.
* Borrado lógico aplicado a una entidad dependiente.

## Estado actual del proyecto

### EstadoTrabajo

* CRUD completo.
* Validaciones.
* Manejo global de errores.

### TipoTrabajo

* CRUD completo.
* Validaciones.
* Manejo global de errores.

### Cliente

* CRUD completo.
* Validaciones.
* Manejo global de errores.
* Borrado lógico.
* Listado de activos.
* Listado de inactivos.
* Reactivación.

### Paciente

* Entity creada.
* Relación con Cliente.
* Repository creado.
* Service creado.
* Controller creado.
* CRUD completo.
* Validaciones.
* Borrado lógico.
* Listado de activos.
* Listado de inactivos.
* Listado por cliente.
* Reactivación.

# Mejora técnica - Respuestas 201 Created

## Objetivo

Se mejoraron las respuestas HTTP de los endpoints `POST`.

Antes, cuando se creaba un recurso correctamente, la API devolvía:

```http
200 OK
```

Aunque funcionaba, no era la respuesta más precisa para una creación.

Ahora, los endpoints `POST` devuelven:

```http
201 Created
```

Esto indica de forma más correcta que el recurso fue creado exitosamente.

## Endpoints modificados

Se aplicó la mejora en:

```http
POST /estados
POST /tipos-trabajo
POST /clientes
POST /pacientes
```

## Comportamiento actual

```text
POST exitoso       → 201 Created
POST inválido      → 400 Bad Request
GET inexistente    → 404 Not Found
PUT inexistente    → 404 Not Found
DELETE correcto    → 204 No Content
```

## Conceptos reforzados

* Uso de `ResponseEntity`.
* Uso de `HttpStatus.CREATED`.
* Diferencia entre `200 OK` y `201 Created`.
* Mejora semántica de respuestas REST.
* Separación de commits por tipo de cambio.

## Commit realizado

Se realizó commit y push de esta mejora para mantener el historial del proyecto ordenado.

## Sesión 17 - Entidad Trabajo y CRUD funcional

En esta sesión se implementó la entidad central del sistema: `Trabajo`.

### Objetivo de la sesión

Crear la entidad `Trabajo`, conectarla con las entidades ya existentes y probar su funcionamiento completo desde Postman.

La decisión fue avanzar de forma controlada:

1. Primero darle vida a la entidad.
2. Luego crear repository, service y controller.
3. Después probar endpoints.
4. Dejar las reglas de negocio más complejas para una siguiente etapa.

---

### Entidad creada: Trabajo

Se creó la clase:



## Sesión 18 - Regla de coherencia entre Paciente y Cliente en Trabajo

En esta sesión se avanzó con una mejora pequeña pero importante sobre la entidad `Trabajo`.

### Objetivo

Agregar una regla de negocio para evitar que se pueda crear o actualizar un trabajo con un paciente que no pertenece al cliente indicado.

Ejemplo de inconsistencia que se quiere evitar:

Trabajo:
cliente = Dr. Pérez

Paciente:
pertenece a Dra. Gómez

## Sesión 19 - Filtro de trabajos por fecha de ingreso

En esta sesión se realizó una mejora pequeña y funcional sobre la entidad `Trabajo`.

### Objetivo

Permitir consultar trabajos activos según su fecha de ingreso.

Esto sirve para responder preguntas del sistema como:


¿Qué trabajos ingresaron hoy?
¿Qué trabajos se cargaron en una fecha determinada?

## Sesión 20 - Filtro de trabajos por fecha de entrega estimada

En esta sesión se agregó una mejora pequeña y funcional sobre la entidad `Trabajo`.

### Objetivo

Permitir consultar trabajos activos según su fecha estimada de entrega.

Esto sirve para responder preguntas del sistema como:


¿Qué trabajos tengo estimados para entregar en una fecha determinada?
¿Qué trabajos debería preparar para tal día?

## Sesión 21 - Revisión de enfoque de aprendizaje

En esta sesión se hizo una pausa para revisar la forma de trabajo del proyecto.





