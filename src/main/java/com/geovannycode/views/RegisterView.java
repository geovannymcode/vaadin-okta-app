package com.geovannycode.views;

import com.geovannycode.model.User;
import com.geovannycode.service.OktaUserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends VerticalLayout {

    public RegisterView(OktaUserService oktaUserService) {

        TextField firstName = new TextField("Nombre");
        TextField lastName = new TextField("Apellido");
        EmailField email = new EmailField("Correo electrónico");
        PasswordField password = new PasswordField("Contraseña");
        Button registerButton = new Button("Registrarse");

        Span message = new Span();
        message.setVisible(false); 

        registerButton.addClickListener(event -> {
            User user = new User(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue());
            if (oktaUserService.registerUser(user)) {
                Notification.show("Usuario registrado con éxito. Ahora puedes iniciar sesión.");
                UI.getCurrent().navigate("login");
            } else {
                message.setText("No se pudo completar el registro. Intenta nuevamente.");
                message.getStyle().set("color", "red");
                message.setVisible(true);
            }
        });

        add(new H2("Formulario de Registro"), firstName, lastName, email, password, registerButton, message);
    }
}
