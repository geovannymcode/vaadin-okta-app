package com.geovannycode.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Route("profile")
@PageTitle("Perfil de Usuario")
public class ProfileView extends VerticalLayout {

    public ProfileView() {
        addClassName("profile-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            OidcUser user = (OidcUser) authentication.getPrincipal();

            H1 title = new H1("Bienvenido, " + user.getGivenName());
            Paragraph email = new Paragraph("Email: " + user.getEmail());

            Button logoutButton = new Button("Cerrar sesión", e -> {
                getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
            });

            add(title, email, logoutButton);
        } else {
            H1 title = new H1("No se ha iniciado sesión");
            Button loginButton = new Button("Ir a login", e -> {
                getUI().ifPresent(ui -> ui.navigate("login"));
            });

            add(title, loginButton);
        }
    }
}
