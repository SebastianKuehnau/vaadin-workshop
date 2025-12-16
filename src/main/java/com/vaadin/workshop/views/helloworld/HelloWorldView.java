package com.vaadin.workshop.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Hello World")
@Route(value = "")
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class HelloWorldView extends HorizontalLayout implements HasUrlParameter<String> {

    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        addClassName("playground-view");

        name = new TextField("Your name");
        name.setTooltipText("Your tooltip name");
        name.setHelperText("Your helper text");
        name.setPlaceholder("Your placeholder");

        sayHello = new Button("Say hello");
        sayHello.setTooltipText("Say hello to your button tooltip");
        sayHello.setTabIndex(1);
        name.setTabIndex(2);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);
        sayHello.addClassName("red-button");

        setMargin(true);
        setVerticalComponentAlignment(Alignment.BASELINE, name, sayHello);

        add(name, sayHello);
    }


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            name.setValue(parameter);
        }
    }
}
