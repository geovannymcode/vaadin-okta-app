
# vaadin-okta-app

Aplicación web moderna construida con Spring Boot, Vaadin y Okta para autenticación y autorización segura.

## 🚀 Tecnologías

- **Backend:** Java 21, Spring Boot 3.x
- **Frontend:** Vaadin 24+
- **Seguridad:** OAuth 2.0 con Okta
- **Gestión de usuarios:** Registro e inicio de sesión seguro
- **Control de acceso:** Integración con `SecurityFilterChain`

## 🧩 Funcionalidades principales

- Registro de usuarios con formulario personalizado en Vaadin
- Inicio de sesión con redirección a Okta (OAuth 2.0)
- Visualización del perfil del usuario autenticado
- Logout seguro
- Protección de rutas con Spring Security

## 📂 Estructura del proyecto

```
src
├── main
│   ├── java/com/geovannycode
│   │   ├── config/SecurityConfig.java
│   │   ├── model/User.java
│   │   ├── service/OktaUserService.java
│   │   └── views
│   │       ├── LoginView.java
│   │       ├── ProfileView.java
│   │       └── RegisterView.java
│   └── resources
│       └── application.yml
```

## ⚙️ Configuración requerida

1. Tener una cuenta gratuita de desarrollador en [Okta](https://developer.okta.com).
2. Crear una aplicación de tipo "Web" (OIDC) en Okta.
3. Obtener los valores de:

    - `Client ID`
    - `Client Secret`
    - `Issuer URI`
    - `API Token`

4. Configurar el archivo `application.yml` con tus credenciales:

```yaml
vaadin:
  launch-browser: true
spring:
  application:
    name: vaadin-okta-app
  security:
    oauth2:
      client:
        registration:
          okta:
            client-id: ${OKTA_CLIENT_ID}
            client-secret: ${OKTA_CLIENT_SECRET}
            scope: openid,profile,email
        provider:
          okta:
            issuer-uri: ${OKTA_ISSUER_URI}
server:
  port: 8080

okta:
  api:
    token: ${OKTA_API_TOKEN}
```

## ▶️ Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

Luego visita: [http://localhost:8080](http://localhost:8080)

## 🔐 Flujo de autenticación

1. Usuario visita `/login`
2. Redirige a Okta para autenticación
3. Okta devuelve un código de autorización
4. Spring Boot intercambia el código por un token
5. El usuario es redirigido a `/profile`

## 📚 Referencias

- [Documentación oficial de Okta](https://developer.okta.com/docs/guides/protect-your-api/springboot/main/)
- [Autenticación con Spring Security y Okta - Baeldung](https://www.baeldung.com/spring-security-okta)
- [Opciones de Login con Spring Boot y Okta](https://developer.okta.com/blog/2019/05/15/spring-boot-login-options)

## 🌐 Autor

Geovanny Mendoza  
🔗 [https://geovannycode.com](https://geovannycode.com)  
🐙 [https://github.com/geovannymcode](https://github.com/geovannymcode)

---

Licensed under the [Apache 2.0 License](LICENSE).

