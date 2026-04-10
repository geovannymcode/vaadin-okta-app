package com.geovannycode.service;

import com.geovannycode.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Auth0UserService {

    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}")
    private String auth0Issuer;

    @Value("${spring.security.oauth2.client.registration.auth0.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.auth0.client-secret}")
    private String clientSecret;

    @Value("${auth0.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean registerUser(User user) {
        // Auth0 no usa el sufijo /oauth2/default, así que solo armamos la URL directamente.
        // Aseguramos que la URL base termine con un slash por precaución.
        String baseUrl = auth0Issuer.endsWith("/") ? auth0Issuer : auth0Issuer + "/";
        String url = baseUrl + "api/v2/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Auth0 requiere un Bearer token estándar, en lugar del SSWS de Okta
        headers.set("Authorization", "Bearer " + apiToken);

        // El cuerpo de la petición (payload) estructurado para Auth0
        Map<String, Object> body = Map.of(
                "email", user.getEmail(),
                "password", user.getPassword(),
                "connection", "Username-Password-Authentication", // Este es el nombre por defecto de la base de datos de usuarios en Auth0
                "given_name", user.getFirstName(),
                "family_name", user.getLastName(),
                "name", user.getFirstName() + " " + user.getLastName()
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostConstruct
    public void debugAuth0Config() {
        System.out.println("AUTH0 CONFIG ->");
        System.out.println("Issuer: " + auth0Issuer);
        System.out.println("Client ID: " + clientId);
        System.out.println("Client Secret: " + clientSecret);
    }
}