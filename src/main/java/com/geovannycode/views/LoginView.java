package com.geovannycode.views;


import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("login")
@PageTitle("Login")
@RouteAlias("")
public class LoginView extends VerticalLayout {

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Bienvenido a la aplicación");
        Anchor loginLink = new Anchor("oauth2/authorization/okta", "Iniciar sesión con Okta");
        loginLink.getElement().setAttribute("router-ignore", true);
        loginLink.getStyle().set("margin-top", "20px");

        Anchor registerLink = new Anchor("register", "Registrarse");
        registerLink.getStyle().set("margin-top", "10px");

        Div linkContainer = new Div(loginLink, new Div(registerLink));
        linkContainer.getStyle().set("display", "flex");
        linkContainer.getStyle().set("flex-direction", "column");
        linkContainer.getStyle().set("align-items", "center");

        add(title, linkContainer);
    }
}
