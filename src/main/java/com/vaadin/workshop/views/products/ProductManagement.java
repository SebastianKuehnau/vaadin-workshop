package com.vaadin.workshop.views.products;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.workshop.data.Product;
import com.vaadin.workshop.data.ProductRepository;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@Menu(title = "Products", order = 5, icon = LineAwesomeIconUrl.SHOPPING_BAG_SOLID)
@Route("products")
public class ProductManagement extends HorizontalLayout {

    private final ProductRepository repository;

    private final TextField name = new TextField("Name");
    private final TextField price = new TextField("Price");
    private final TextField description = new TextField("Description");
    private final TextField rating = new TextField("Rating");
    private final Grid<Product> grid = new Grid<>(Product.class);

    private Product selectedProduct = new Product();

    private final BeanValidationBinder<Product> binder = new BeanValidationBinder<>(Product.class);

    private final Button cancel = new Button("cancel", this::cancel);
    private final Button save = new Button("save", this::save);

    public ProductManagement(ProductRepository repository) {
        this.repository = repository;
        binder.bindInstanceFields(this);

        addClassName("product-management");

        grid.setItems(repository.findAll());
        grid.asSingleSelect().addValueChangeListener(event -> {
                selectedProduct = event.getValue();
                binder.readBean(event.getValue());
        });
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        add(grid);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(name, price, description, rating);

        var buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancel, save);
        formLayout.add(buttonLayout);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        add(formLayout);

        setSizeFull();
        setSpacing(false);
    }

    private void cancel(ClickEvent<Button> buttonClickEvent) {
        binder.readBean(selectedProduct);
    }

    private void save(ClickEvent<Button> buttonClickEvent) {
        try {
            binder.writeBean(selectedProduct);
            repository.save(selectedProduct);
            grid.getDataProvider().refreshItem(selectedProduct);
            Notification.show("Product saved").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (ValidationException e) {
            Notification.show("Failed to save product").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
