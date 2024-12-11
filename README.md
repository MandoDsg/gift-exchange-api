# API de Intercambio de Regalos Navideño

Este proyecto es una API RESTful desarrollada en **Java con Spring Boot** para gestionar un intercambio de regalos navideño entre amigos o familiares. La API permite registrar participantes, asignar regalos de forma aleatoria, consultar los destinatarios asignados, y enviar correos electrónicos con la información de los destinatarios.

---

## Características

- **Registro de participantes**: Permite registrar participantes con nombre y correo electrónico para organizar un intercambio de regalos.
- **Asignación aleatoria de destinatarios**: Asigna aleatoriamente un destinatario a cada participante, garantizando que un participante no se asigne a sí mismo.
- **Consulta de destinatarios**: Los participantes pueden consultar a quién le toca regalarles sin revelar los demás destinatarios.
- **Autenticación**: La API está protegida con autenticación básica, que garantiza que solo los usuarios autenticados puedan acceder a ciertos recursos.
- **Envío de correos electrónicos**: Utiliza **Mailtrap** para simular el envío de correos electrónicos de prueba, donde los participantes reciben un correo con la información de su destinatario asignado.
- **Documentación Swagger/OpenAPI**: La API está documentada de manera interactiva con Swagger, lo que permite visualizar todos los endpoints y probarlos directamente desde la interfaz web.

---

## Tecnologías

- **Java 21**: El lenguaje de programación utilizado para desarrollar la API.
- **Spring Boot 3.4.0**: Framework que simplifica la configuración y desarrollo de aplicaciones Java.
- **Spring Security**: Para gestionar la autenticación y autorización de los usuarios.
- **Mailtrap**: Servidor de correo electrónico para realizar pruebas sin enviar correos reales.
- **Swagger/OpenAPI**: Herramienta para documentar y probar los endpoints de la API.
- **Base de datos en memoria (H2)**: Utilizada para almacenar los datos de los participantes y asignaciones. Es posible cambiar a otro sistema de base de datos si se desea.

---

## Endpoints

### 1. **Registrar un participante**
- **Método**: `POST`
- **URL**: `/api/participants`
- **Descripción**: Permite registrar un nuevo participante proporcionando su nombre y correo electrónico.
- **Autenticación**: Se requiere autenticación básica de usuario (`user`, `user` por defecto).
- **Cuerpo de la solicitud** (JSON):
  ```json
  {
      "name": "Angel Arellano",
      "email": "angel.arellano@example.com"
  }
  ```
- **Respuesta**:
  - **201 Created**: El participante se ha registrado correctamente en el sistema.
  
#### Ejemplo de respuesta:
```json
{
    "id": 1,
    "name": "Angel Arellano",
    "email": "angel.arellano@example.com"
}
```

---

### 2. **Listar todos los participantes** (Requiere autenticación)
- **Método**: `GET`
- **URL**: `/api/participants`
- **Descripción**: Devuelve una lista de todos los participantes registrados en el sistema.
- **Autenticación**: Se requiere autenticación de administrador (`admin`, `admin` por defecto).
- **Respuesta**:
  - **200 OK**: Lista de participantes en formato JSON.

#### Ejemplo de respuesta:
```json
[
    {
        "id": 1,
        "name": "Angel Arellano",
        "email": "angel.arellano@example.com"
    },
    {
        "id": 2,
        "name": "Alejandra Jiménez",
        "email": "alejandra.jimenez@example.com"
    }
]
```

---

### 3. **Asignar destinatarios aleatorios** (Requiere autenticación)
- **Método**: `POST`
- **URL**: `/api/participants/assign`
- **Descripción**: Asigna aleatoriamente a cada participante un destinatario al que deberá regalarle. No se permite que un participante se asigne a sí mismo.
- **Autenticación**: Se requiere autenticación básica de usuario (`user`, `user` por defecto).
- **Respuesta**:
  - **200 OK**: Los destinatarios han sido asignados y los correos electrónicos han sido enviados con la información del destinatario asignado.

#### Ejemplo de respuesta:
```json
{
    "message": "Asignación de destinatarios completada y correos electrónicos enviados."
}
```

---

### 4. **Consultar destinatario asignado** (Requiere autenticación)
- **Método**: `GET`
- **URL**: `/api/participants/{email}/recipient`
  - **Parámetros**: Reemplaza `{email}` con el correo electrónico del participante.
- **Descripción**: Permite que un participante consulte quién es su destinatario asignado.
- **Autenticación**: Se requiere autenticación básica de usuario (`user`, `user` por defecto).
- **Respuesta**:
  - **200 OK**: Información sobre el destinatario asignado.

#### Ejemplo de respuesta:
```json
{
    "recipient": {
        "name": "Alejandra Jiménez",
        "email": "alejandra.jimenez@example.com"
    }
}
```

---

## Instalación

### Requisitos
Para ejecutar esta API, necesitas tener los siguientes programas instalados en tu máquina:

- **Java 21** o superior.
- **Maven**: Para gestionar las dependencias del proyecto.
- **Base de datos**: Se usa por defecto para pruebas.

### Clonar el repositorio

Para obtener el código fuente del proyecto, clona el repositorio desde GitHub:

```bash
git clone https://github.com/tu-usuario/gift-exchange-api.git
cd gift-exchange-api
```

### Configuración del archivo `application.properties`

En el archivo `src/main/resources/application.properties`, configura las siguientes propiedades para tu aplicación:

#### Configuración de base de datos (si usas PostgreSQL):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gift_exchange_db
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
```

#### Configuración de Mailtrap:
```properties
spring.mail.host=smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=tu_username
spring.mail.password=tu_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

#### Configuración de Spring Security:
```properties
spring.security.user.name=admin
spring.security.user.password=admin_password
spring.security.user.roles=ADMIN,USER
```

### Ejecutar la aplicación

Para ejecutar la aplicación en tu máquina local, utiliza el siguiente comando de Maven:

```bash
mvn spring-boot:run
```

Esto iniciará el servidor de la API en `http://localhost:8080`.

---

## Probar la API

Puedes probar la API utilizando herramientas como **Postman** o **curl**. Los endpoints requieren autenticación básica para las rutas protegidas.

### Ejemplo de configuración en Postman:
1. En la pestaña **Authorization**, selecciona **Basic Auth**.
2. Ingresa el nombre de usuario y la contraseña configurados en la propiedad de seguridad (por defecto, **admin** y **admin_password**).
3. Realiza las peticiones a los endpoints y observa las respuestas.

---

## Documentación Swagger

Una vez que la aplicación esté en funcionamiento, puedes acceder a la documentación interactiva de la API a través de Swagger:

```
http://localhost:8080/swagger-ui.html
```

Aquí podrás ver todos los endpoints disponibles y probarlos directamente desde la interfaz.

---

## Notas

- **Correo electrónico**: Si no deseas utilizar un servidor de correo real, puedes configurar **Mailtrap** para enviar correos electrónicos de prueba.
- **Base de datos**: Si no deseas usar H2, puedes cambiar a una base de datos diferente como MySQL o PostgreSQL, ajustando las configuraciones adecuadas.
- **Autenticación**: Asegúrate de enviar correctamente las credenciales para las rutas que requieren autenticación básica.
