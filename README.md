# **Tarjetas Regalo**  

Aplicación monolítica para la gestión de tarjetas regalo.

## **3. Funcionalidades principales**  

### **Gestión de tarjetas regalo**  

El sistema permite la gestión completa del ciclo de vida de las tarjetas regalo:  

- **Creación**: Generación de nuevas tarjetas regalo  
- **Consulta**: Visualización de detalles y saldo de tarjetas  
- **Recarga**: Adición de saldo en la edición a tarjetas existentes  
- **Redención**: Uso del saldo disponible en las tarjetas  
- **Edición**: Modificación de los datos de una tarjeta existente  
- **Eliminación**: Eliminación de tarjetas regalo  
- **Búsqueda**: Obtención de tarjetas por ID o código de tarjeta (`codeCard`)  

---

## **4. Endpoints de la API**  

### **Autenticación y seguridad**  

La API utiliza JWT (JSON Web Tokens) para la autenticación. Para acceder a los endpoints siguientes, se debe incluir el token JWT en el encabezado:  

```
Authorization: Bearer {token}
```

### **Gestión de tarjetas regalo**  

#### **1. Crear una nueva tarjeta regalo**  
- **Endpoint:** `POST /api/giftcards/saveGiftCard`  
- **Descripción:** Permite crear una nueva tarjeta regalo.  
- **Cuerpo de la solicitud:**  

```json
{
    "amount": 2000,
    "creationDateCard": "2025-04-02T04:17:48.103+00:00",
    "expirationDateCard": "2025-04-30T04:17:48.103+00:00",
    "state": "Active"
}
```

#### **2. Obtener todas las tarjetas regalo**  
- **Endpoint:** `GET /api/giftcards/findAllGiftCards`  
- **Descripción:** Devuelve todas las tarjetas regalo del usuario autenticado.  

- **Ejemplo de respuesta:**  
```json
[
    {
        "id": 1,
        "codeCard": "12345",
        "amount": 2000,
        "creationDateCard": "2025-04-02T04:17:48.103+00:00",
        "expirationDateCard": "2025-04-30T04:17:48.103+00:00",
        "state": "Active"
    },
    {
        "id": 2,
        "codeCard": "67890",
        "amount": 3000,
        "creationDateCard": "2025-04-02T04:17:48.103+00:00",
        "expirationDateCard": "2025-05-30T04:17:48.103+00:00",
        "state": "Active"
    }
]
```

#### **3. Buscar una tarjeta por ID**  
- **Endpoint:** `GET /api/giftcards/findById/{id}`  
- **Descripción:** Devuelve los detalles de una tarjeta regalo específica según su ID.  
- **Ejemplo de respuesta:**  

```json
{
    "id": 1,
    "codeCard": "12345",
    "amount": 2000,
    "creationDateCard": "2025-04-02T04:17:48.103+00:00",
    "expirationDateCard": "2025-04-30T04:17:48.103+00:00",
    "state": "Active"
}
```

#### **4. Buscar una tarjeta por `codeCard`**  
- **Endpoint:** `GET /api/giftcards/findByCode/{codeCard}`  
- **Descripción:** Devuelve la tarjeta regalo con el código de tarjeta especificado.  
- **Ejemplo de respuesta:**  

```json
{
    "id": 2,
    "codeCard": "67890",
    "amount": 3000,
    "creationDateCard": "2025-04-02T04:17:48.103+00:00",
    "expirationDateCard": "2025-05-30T04:17:48.103+00:00",
    "state": "Active"
}
```

#### **5. Editar una tarjeta regalo**  
- **Endpoint:** `PUT /api/giftcards/update/{id}`  
- **Descripción:** Modifica los datos de una tarjeta regalo existente.  
- **Cuerpo de la solicitud:**  

```json
{
    "amount": 5000,
    "expirationDateCard": "2025-06-30T04:17:48.103+00:00",
    "state": "Active"
}
```

- **Ejemplo de respuesta:**  
```json
{
    "message": "Tarjeta regalo actualizada correctamente",
    "updatedGiftCard": {
        "id": 2,
        "codeCard": "67890",
        "amount": 5000,
        "creationDateCard": "2025-04-02T04:17:48.103+00:00",
        "expirationDateCard": "2025-06-30T04:17:48.103+00:00",
        "state": "Active"
    }
}
```

#### **6. Eliminar una tarjeta regalo**  
- **Endpoint:** `DELETE /api/giftcards/delete/{id}`  
- **Descripción:** Elimina una tarjeta regalo por su ID.  
- **Ejemplo de respuesta:**  

```json
{
    "message": "Tarjeta regalo eliminada correctamente"
}
```

---

## **5. Modelo de datos**  

El sistema utiliza las siguientes entidades principales:

### **1. `User` (Usuario)**  
Registra la información de los usuarios que pueden crear y administrar tarjetas.  

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role; // ADMIN o USER
}
```

### **2. `GiftCard` (Tarjeta Regalo)**  
Representa una tarjeta regalo con su saldo, estado y fechas.  

```java
@Entity
public class GiftCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeCard;
    private Double amount;
    private LocalDateTime creationDateCard;
    private LocalDateTime expirationDateCard;
    private String state; // Active, Expired, Used
}
```

### **3. `Redemption` (Redención)**  
Registra el uso de una tarjeta regalo.  

```java
@Entity
public class Redemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipientEmail;
    private LocalDateTime redemptionDate;
}
```

---

## **6. Pruebas**  

Para ejecutar las pruebas unitarias:  

```shell
mvn test
```

Para ejecutar las pruebas de integración:  

```shell
mvn verify
```

---

## **7. Consideraciones de seguridad**  

- La aplicación usa **Spring Security** para proteger los endpoints.  
- Las contraseñas se almacenan **encriptadas** en la base de datos.  
- Los tokens JWT tienen una **duración limitada** y deben renovarse periódicamente.  
- Se implementan validaciones para prevenir ataques **CSRF, XSS e inyección SQL**.  

---

## **Licencia**  

Este proyecto es propiedad de **Laura Ximena Limas Sarasty** y está protegido por derechos de autor.  

---
