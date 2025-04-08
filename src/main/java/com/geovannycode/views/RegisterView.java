package com.geovannycode.views;

import com.geovannycode.model.User;
import com.geovannycode.service.OktaUserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("register")
@PageTitle("Registro de Usuario")
public class RegisterView extends VerticalLayout {

    public RegisterView(OktaUserService oktaUserService) {
        // Configuración del layout principal
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Componentes del formulario
        TextField firstName = new TextField("Nombre");
        firstName.setRequired(true);
        firstName.setMinLength(2);

        TextField lastName = new TextField("Apellido");
        lastName.setRequired(true);
        lastName.setMinLength(2);

        EmailField email = new EmailField("Correo electrónico");
        email.setRequired(true);
        email.setErrorMessage("Por favor ingresa un correo electrónico válido");
        email.setPlaceholder("ejemplo@correo.com");

        PasswordField password = new PasswordField("Contraseña");
        password.setRequired(true);
        password.setMinLength(8);
        password.setErrorMessage("La contraseña debe tener al menos 8 caracteres");

        // Resetear estado de validación al escribir
        firstName.addValueChangeListener(e -> firstName.setInvalid(false));
        lastName.addValueChangeListener(e -> lastName.setInvalid(false));
        email.addValueChangeListener(e -> email.setInvalid(false));
        password.addValueChangeListener(e -> password.setInvalid(false));

        // Configuración del FormLayout
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, email, password);
        formLayout.setResponsiveSteps(
                // Una columna por defecto para pantallas pequeñas
                new ResponsiveStep("0", 1),
                // Dos columnas para pantallas más grandes
                new ResponsiveStep("500px", 2)
        );

        // Hacer que el correo ocupe todo el ancho
        formLayout.setColspan(email, 2);
        formLayout.setColspan(password, 2);

        // Estilo para el formulario
        formLayout.setMaxWidth("600px");
        formLayout.getStyle()
                .set("margin", "0 auto")
                .set("padding", "20px")
                .set("border-radius", "8px")
                .set("background-color", "var(--lumo-base-color)")
                .set("box-shadow", "0 2px 10px var(--lumo-shade-10pct)");

        // Botones
        Button registerButton = new Button("Registrarse");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancelar", e -> UI.getCurrent().navigate("login"));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout buttonLayout = new HorizontalLayout(registerButton, cancelButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(true);

        // Evento de registro
        registerButton.addClickListener(event -> {
            if (validateForm(firstName, lastName, email, password)) {
                User user = new User(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue());
                if (oktaUserService.registerUser(user)) {
                    Notification notification = Notification.show(
                            "Usuario registrado con éxito. Ahora puedes iniciar sesión.",
                            5000,
                            Notification.Position.TOP_CENTER);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    UI.getCurrent().navigate("login");
                } else {
                    Notification notification = Notification.show(
                            "No se pudo completar el registro. Intenta nuevamente.",
                            5000,
                            Notification.Position.TOP_CENTER);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        });

        // Título
        H2 title = new H2("Registro de Usuario");
        title.getStyle().set("margin-bottom", "2em").set("color", "var(--lumo-primary-text-color)");

        // Agregar componentes al layout principal
        add(title, formLayout, buttonLayout);
    }

    private boolean validateForm(TextField firstName, TextField lastName, EmailField email, PasswordField password) {
        boolean isValid = true;

        if (firstName.isEmpty()) {
            firstName.setInvalid(true);
            firstName.setErrorMessage("El nombre es requerido");
            isValid = false;
        }

        if (lastName.isEmpty()) {
            lastName.setInvalid(true);
            lastName.setErrorMessage("El apellido es requerido");
            isValid = false;
        }

        if (email.isEmpty() || !email.getValue().matches(".+@.+\\..+")) {
            email.setInvalid(true);
            email.setErrorMessage("Ingresa un correo electrónico válido");
            isValid = false;
        }

        if (password.isEmpty() || password.getValue().length() < 8) {
            password.setInvalid(true);
            isValid = false;
        }

        return isValid;
    }
}