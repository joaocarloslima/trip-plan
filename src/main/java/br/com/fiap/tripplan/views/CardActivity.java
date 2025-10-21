package br.com.fiap.tripplan.views;

import br.com.fiap.tripplan.model.Activity;
import br.com.fiap.tripplan.model.ActivityType;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoIcon;

public class CardActivity extends Card {
    public CardActivity(Activity activity) {
        setTitle(activity.getTitle());
        add(new Paragraph(activity.getDescription()));
        setMedia(getIconForActivityType(activity.getType()));

        Span badge = new Span(activity.getDurationInMinutes() + " min");
        badge.getElement().getThemeList().add("badge success");
        setHeaderSuffix(badge);

        addThemeVariants(CardVariant.LUMO_ELEVATED);
        addThemeVariants(CardVariant.LUMO_HORIZONTAL);
    }

    private Component getIconForActivityType(ActivityType type) {
        return switch (type) {
            case RESTAURANT -> VaadinIcon.CUTLERY.create();
            case PARK -> LumoIcon.PHOTO.create();
            case MUSEUM -> VaadinIcon.BUILDING.create();
            case SHOPPING -> VaadinIcon.SHOP.create();
            case TOUR -> VaadinIcon.GLOBE.create();
            case HOTEL -> VaadinIcon.BED.create();
            default -> VaadinIcon.QUESTION_CIRCLE.create();
        };
    }
}
