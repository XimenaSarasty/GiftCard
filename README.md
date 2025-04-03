# Tarjetas Regalo

Aplicación monolítica para la gestión de tarjetas regalo.

## Descripción

Este proyecto implementa un sistema de gestión de tarjetas regalo, permitiendo la creación, consulta, actualización y uso de tarjetas regalo digitales.

## Requisitos previos

- Java 11
- Maven 3.6+
- Docker (opcional, para contenedorización)

## 1. Ejecución

### Iniciar la aplicación

Para iniciar la aplicación, puedes usar el plugin de Spring Boot de Maven:

```shellscript
mvn spring-boot:run
```

Alternativamente, puedes ejecutar el JAR generado:

```shellscript
java -jar target/TarjetasRegalo-0.0.1-SNAPSHOT.jar
```

### Configuración de la base de datos

#### Opción 1: Base de datos H2 (por defecto)

La aplicación está configurada para usar H2 como base de datos en memoria por defecto, ideal para desarrollo y pruebas.

La consola de H2 estará disponible en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Datos de conexión por defecto:

- JDBC URL: `jdbc:h2:mem:tarjetasdb`
- Usuario: `sa`
- Contraseña: (dejar en blanco)


Para configurar H2, añade estas propiedades en `application.properties`:

```plaintext
spring.datasource.url=jdbc:h2:mem:tarjetasdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

#### Opción 2: Base de datos PostgreSQL con Docker

Para entornos de producción o pruebas más realistas, puedes usar Docker para ejecutar una base de datos PostgreSQL:

1. Inicia un contenedor PostgreSQL:


```shellscript
docker run --name postgres-tarjetas -e POSTGRES_PASSWORD=password -e POSTGRES_DB=tarjetasdb -p 5432:5432 -d postgres
```

2. Configura la aplicación para usar PostgreSQL modificando `application.properties`:


```plaintext
spring.datasource.url=jdbc:postgresql://localhost:5432/tarjetasdb
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## 3. Funcionalidades principales

### Gestión de tarjetas regalo

El sistema permite la gestión completa del ciclo de vida de las tarjetas regalo:

- **Creación**: Generación de nuevas tarjetas regalo
- **Consulta**: Visualización de detalles y saldo de tarjetas
- **Recarga**: Adición de saldo en la edición a tarjetas existentes
- **Redención**: Uso del saldo disponible en las tarjetas


### Sistema de notificaciones por email

La aplicación cuenta con un sistema de notificaciones por email que se activa en los siguientes eventos:

1. **Creación de tarjeta**: Cuando se crea una nueva tarjeta regalo, se envía un email al destinatario con:

- Código único de la tarjeta
- Monto inicial
- Mensaje personalizado del remitente


2. **Redención de tarjeta**: Cuando se redime (usa) una tarjeta, se envían notificaciones a:

- **Propietario original**: Notificación de que su regalo ha sido utilizado

### Configuración del servicio de email

Para habilitar el sistema de notificaciones, configura las siguientes propiedades en `application.properties`:

```plaintext
# Configuración básica de email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-contraseña-o-token-de-aplicación
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

```

### Procesamiento automático de tarjetas expiradas (Bonus)

El sistema incluye una funcionalidad de procesamiento automático de tarjetas expiradas mediante una tarea programada:

- **Ejecución diaria**: La tarea se ejecuta automáticamente todos los días a las 3:00 AM
- **Verificación de expiración**: Comprueba todas las tarjetas con fecha de expiración anterior a la fecha actual
- **Marcado automático**: Las tarjetas expiradas se marcan con estado "Expired"


Para configurar esta funcionalidad, asegúrate de tener estas propiedades en `application.properties`:

```plaintext
# Configuración de tareas programadas
spring.task.scheduling.pool.size=5
tarjetas.expiracion.cron=0 0 3 * * ?  # Ejecutar todos los días a las 3:00 AM
```

## 4. Endpoints de la API

### Autenticación y seguridad

La API utiliza JWT (JSON Web Tokens) para la autenticación:

- **POST /api/auth/authenticate: Iniciar sesión y obtener token JWT

```json
{
  "username": "usuario@ejemplo.com",
  "password": "contraseña"
}
```

Respuesta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "tokenType": "Bearer"
}
```


- **POST /api/auth/register**: Registrar nuevo usuario

```json
{
  "username": "usuario@ejemplo.com",
  "password": "contraseña",
}
```




### Gestión de tarjetas regalo

Para todos los endpoints siguientes, se requiere incluir el token JWT en el header:
`Authorization: Bearer {token}`

- **POST /api/giftcards/saveGiftCard: Crear nueva tarjeta regalo

```json
{
    "amount": 2000,
    "creationDateCard": "2025-04-02T04:17:48.103+00:00",
    "expirationDateCard": "2025-04-30T04:17:48.103+00:00",
    "state": "Active"
}

```

- **GET /api/giftcards/findAllGiftCards: Obtener todas las tarjetas del usuario autenticado


```json
[
    {
    "code":12345,
        "amount": 2000,
        "creationDateCard": "2025-04-02T04:17:48.103+00:00",
        "expirationDateCard": "2025-04-30T04:17:48.103+00:00",
        "state": "Active"
    },
    {
    "code":12345,
        "amount": 2000,
        "creationDateCard": "2025-04-02T04:17:48.103+00:00",
        "expirationDateCard": "2025-04-30T04:17:48.103+00:00",
        "state": "Active"
    }
]

```

### Administración (solo para usuarios con rol ADMIN)

- **POST /redemptions/redeem/{cardCode}: Redimir o utilizar una tarjeta
    (donde {cardCode} es el código de la tarjeta
    
    Parámetros de Consulta:
    recipientEmail (correo electrónico del destinatario)


## 5. Modelo de datos

El sistema utiliza las siguientes entidades principales:

- **User**: Almacena información de usuarios registrados
- **GiftCard**: Representa una tarjeta regalo con su saldo, estado y fechas
- **Redemption**: Registra la rendición sobre una tarjeta

## 6. Pruebas

Para ejecutar las pruebas unitarias:

```shellscript
mvn test
```

Para ejecutar las pruebas de integración:

```shellscript
mvn verify
```

## 7. Consideraciones de seguridad

- La aplicación utiliza Spring Security para proteger los endpoints
- Las contraseñas se almacenan encriptadas en la base de datos
- Los tokens JWT tienen una duración limitada 
- Se implementan validaciones para prevenir ataques comunes (CSRF, XSS, inyección SQL)


## Licencia

Este proyecto es propiedad de Laura Ximena Limas Sarasty y está protegido por derechos de autor.
