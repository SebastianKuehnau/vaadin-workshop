package com.vaadin.workshop.views.products;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.workshop.data.Product;
import com.vaadin.workshop.data.ProductRepository;

@Menu(title = "Products", order = 5)
@Route("products")
public class ProductManagement extends HorizontalLayout {

    private final TextField name = new TextField("Name");
    private final TextField price = new TextField("Price");
    private final TextField description = new TextField("Description");
    private final TextField rating = new TextField("Rating");

    public ProductManagement(ProductRepository repository) {

        var grid = new Grid<>(Product.class);
        grid.setItems(repository.findAll());
        grid.setSizeFull();
        add(grid);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(name);
        formLayout.add(price);
        formLayout.add(description);
        formLayout.add(rating);

        var buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("cancel", event -> {}),
                new Button("save", event -> {}));
        formLayout.add(buttonLayout);
        add(formLayout);

        setSizeFull();
    }
}
