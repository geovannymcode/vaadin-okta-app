
# vaadin-auth0-app

Aplicación web moderna construida con Spring Boot, Vaadin y Auth0 para autenticación y autorización segura.

## 🚀 Tecnologías

- **Backend:** Java 21, Spring Boot 4.0.5
- **Frontend:** Vaadin 25.1.1 (100% Java, backend-driven UI sin necesidad de escribir JavaScript)
- **Seguridad:** OAuth 2.0 y OpenID Connect (OIDC) con Auth0
- **Gestión de usuarios:** Registro e inicio de sesión seguro
- **Control de acceso:** Integración nativa con `VaadinWebSecurity` y Spring Security

## 🧩 Funcionalidades principales

- Registro de usuarios interactuando con la Management API de Auth0 desde un formulario personalizado en Vaadin
- Inicio de sesión delegando la identidad mediante redirección al Universal Login de Auth0
- Visualización del perfil del usuario autenticado consumiendo el `OidcUser`
- Logout seguro y limpieza de sesión
- Protección de rutas web y recursos estáticos internos

## 📂 Estructura del proyecto

```
src
├── main
│   ├── java/com/geovannycode
│   │   ├── config/SecurityConfig.java
│   │   ├── model/User.java
│   │   ├── service/Auth0UserService.java
│   │   └── views
│   │       ├── LoginView.java
│   │       ├── ProfileView.java
│   │       └── RegisterView.java
│   └── resources
│       └── application.yml
```

## ⚙️ Configuración requerida

1. Tener un tenant gratuito de desarrollador configurado en [Auth0](https://auth0.com/).
2. Crear una aplicación de tipo "Regular Web Application" en la consola de Auth0.
3. Configurar los URIs de la aplicación en Auth0:
   - Allowed Callback URLs: http://localhost:8080/login/oauth2/code/auth0
   - Allowed Logout URLs: http://localhost:8080
4. Obtener los siguientes valores de la pestaña Settings y APIs:
   - Client ID
   - Client Secret
   - Domain (Issuer URI)
   - Management API Token (Para permitir el registro de usuarios desde la app)

5. Configurar el archivo `application.yml`  o inyectar las credenciales mediante variables de entorno:


```yaml
vaadin:
   launch-browser: ${VAADIN_LAUNCH_BROWSER:true}

spring:
   application:
      name: vaadin-auth0-app
   security:
      oauth2:
         client:
            registration:
               auth0:
                  client-id: ${AUTH0_CLIENT_ID}
                  client-secret: ${AUTH0_CLIENT_SECRET}
                  scope: openid,profile,email
            provider:
               auth0:
                  issuer-uri: ${AUTH0_ISSUER_URI}

server:
   port: ${SERVER_PORT:8080}

auth0:
   api:
      token: ${AUTH0_API_TOKEN}
```
*Nota*: Asegúrate de que tu AUTH0_ISSUER_URI incluya el protocolo y termine con una barra inclinada / (ej. https://dev-xxxx.us.auth0.com/), ya que Spring Security requiere una validación estricta de la URL del emisor.

## ▶️ Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

Luego visita: [http://localhost:8080](http://localhost:8080)

## 🔐 Flujo de autenticación

1. El usuario intenta acceder a una ruta protegida (ej. `/profile`).
2. Spring Security detecta la falta de sesión y redirige automáticamente a la vista pública `/login`.
3. El usuario selecciona "Iniciar sesión con Auth0".
4. La aplicación redirige al servidor de autorización de Auth0.
5. Tras un login exitoso, Auth0 devuelve un código de autorización a la ruta callback registrada.
6. Spring Boot intercambia este código por los tokens JWT y establece el contexto de seguridad.
7. El usuario es redirigido a su destino original y la interfaz de Vaadin se renderiza con sus datos.

## 📚 Referencias

- [Documentación oficial de Auth0 para Spring Boot](https://auth0.com/docs/quickstart/webapp/java-spring-boot)
- [Spring Security OAuth2 Client](https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html)

## 🌐 Autor

Geovanny Mendoza  
🔗 [https://geovannycode.com](https://geovannycode.com)  
🐙 [https://github.com/geovannymcode](https://github.com/geovannymcode)

---

Licensed under the [Apache 2.0 License](LICENSE).

