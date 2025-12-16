package com.vaadin.workshop.views.playground;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridLazyDataView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.workshop.data.SamplePerson;
import com.vaadin.workshop.services.SamplePersonService;
import org.springframework.data.jpa.domain.Specification;
import org.vaadin.lineawesome.LineAwesomeIconUrl;


@PageTitle("Playground")
@Route("playground")
@Menu(order = 4, icon = LineAwesomeIconUrl.SLIDERS_H_SOLID)
public class PlaygroundView extends VerticalLayout {

    /**
     * 1. Fundamentals Vaadin + Short exercise
     * 2. Copilot + Exercise
     * 3. Data Binding + Exercise
     * 4. Testing + Exercise
     */

    public PlaygroundView(SamplePersonService service) {

        addClassName("playground-view");

        // Create 5 buttons
        Button button1 = new Button("Button 1");
        button1.addClassName("red-button");

        Button button2 = new Button("Button 2");
        button2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button button3 = new Button("Button 3");
        button3.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button button4 = new Button("Button 4");
        button4.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        Button button5 = new Button("Button 5");

        // Add buttons to a horizontal layout
        HorizontalLayout buttonLayout = new HorizontalLayout(
            button1, button2, button3, button4, button5
        );
        add(buttonLayout);

        var themeButtonLayout = new HorizontalLayout();
        themeButtonLayout.add(new Button("default", buttonClickEvent -> {
            UI.getCurrent().getElement().getThemeList().clear();
        }));
        themeButtonLayout.add(new Button("purple", buttonClickEvent -> {
            var themeList = UI.getCurrent().getElement().getThemeList();
            if (themeList.contains("purple")) {
                themeList.remove("purple");
            } else {
                themeList.add("purple");
            }
        }));
        themeButtonLayout.add(new Button("yellow", buttonClickEvent -> {
            var themeList = UI.getCurrent().getElement().getThemeList();
            if (themeList.contains("yellow")) {
                themeList.remove("yellow");
            } else {
                themeList.add("yellow");
            }
        }));
        add(themeButtonLayout);

        HorizontalLayout filterLayout = new HorizontalLayout();
        var filterField = new TextField();

        Button filter = new Button("filter");
        filter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filterLayout.add(filterField, filter);
        filterLayout.getStyle().setWidth("100%");
        filterField.setPlaceholder("Filter by name...");
        filterField.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        filterField.setWidthFull();
        
        // Add the button layout on top of the filter layout
        add(filterLayout);

        var grid = new Grid<>(SamplePerson.class);
        grid.setColumns("firstName", "lastName", "email", "occupation");
        GridLazyDataView<SamplePerson> dataView = grid.setItemsPageable(
                pageable -> service.list(pageable, createNameFilter(filterField.getValue())).getContent());
        filterField.addValueChangeListener(e -> dataView.refreshAll());
        add(grid);

        setSizeFull();
    }

    private Specification<SamplePerson> createNameFilter(String filterText) {
        if (filterText == null || filterText.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String pattern = "%" + filterText.toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern));
    }
}
