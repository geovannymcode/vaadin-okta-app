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
public class OktaUserService {
    @Value("${spring.security.oauth2.client.provider.okta.issuer-uri}")
    private String oktaIssuer;

    @Value("${spring.security.oauth2.client.registration.okta.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.okta.client-secret}")
    private String clientSecret;

    @Value("${okta.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean registerUser(User user) {
        String baseUrl = oktaIssuer.replace("/oauth2/default", "");
        String url = baseUrl + "/api/v1/users?activate=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "SSWS " + apiToken);

        Map<String, Object> profile = Map.of(
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "login", user.getEmail()
        );

        Map<String, Object> credentials = Map.of(
                "password", Map.of("value", user.getPassword())
        );

        Map<String, Object> body = Map.of(
                "profile", profile,
                "credentials", credentials
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
    public void debugOktaConfig() {
        System.out.println("🔐 OKTA CONFIG ->");
        System.out.println("Issuer: " + oktaIssuer);
        System.out.println("Client ID: " + clientId);
        System.out.println("Client Secret: " + clientSecret);
    }
}
