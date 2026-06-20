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
# Sesión 07 - Service - Inyeccion de Dependencias
    (Dependency Injection)
* Se Crea la capa Service
* Se crea "Controller" que recibe las peticiones HTTP
* Se realiza la peticion HTTP (GET /estados)




---




# Estado actual del proyecto

## Funciona

* Spring Boot inicia correctamente.
* PostgreSQL conectado.
* Hibernate conectado.
* Tabla estado_trabajo creada.
* Repository creado.
* Primer EstadoTrabajo guardado usando Spring Data JPA sin escribir SQL manual.

## Pendiente a seguir
🔜 Entender la capa Service
🔜 Diferencia entre Repository y Service
🔜 Flujo completo Entity → Repository → Service → Controller


---

# Último punto alcanzado

