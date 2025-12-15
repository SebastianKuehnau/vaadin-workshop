package com.vaadin.workshop.views.playground;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridLazyDataView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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

    private final Grid<SamplePerson> grid = new Grid<>(SamplePerson.class);

    public PlaygroundView(SamplePersonService service) {
        var filterField = new TextField();
        filterField.setPlaceholder("Filter by name...");
        filterField.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.setWidthFull();
        add(filterField);

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
