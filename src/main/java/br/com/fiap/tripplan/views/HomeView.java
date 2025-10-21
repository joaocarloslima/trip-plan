package br.com.fiap.tripplan.views;

import br.com.fiap.tripplan.model.Activity;
import br.com.fiap.tripplan.service.ChatService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends VerticalLayout {

    private final ChatService chatService;

    public HomeView(ChatService chatService) {

        TextField textField = new TextField();
        textField.setLabel("Destino");
        textField.setClearButtonVisible(true);
        textField.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        Button generateButton = new Button("Gerar Roteiro de Viagem", new Icon(VaadinIcon.MAGIC));
        generateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        generateButton.addClickListener(event -> handleGenerate(textField.getValue()));

        var formContainer = new HorizontalLayout(textField, generateButton);
        formContainer.setAlignItems(Alignment.END);

        add(new H1("Trip Plan"));
        add(new Paragraph("Planeje suas viagens de forma fácil e rápida!"));
        add(formContainer);

        this.chatService = chatService;

        var message = new MessageInput();
        message.setWidthFull();
        message.addSubmitListener(event -> executeCommand(event.getValue()));
        add(message);
    }

    private void handleGenerate(String destination) {
        var activities = chatService.getTravelItinerary(destination);
        activities.forEach(activity -> add(new CardActivity(activity)));
    }

    private void executeCommand(String command) {
        var response = chatService.executeCommand(command);
        Notification.show(response, 0, Notification.Position.TOP_STRETCH);
    }
}
