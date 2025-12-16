package com.vaadin.workshop.views.crudexample;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.workshop.data.SamplePerson;
import com.vaadin.workshop.services.SamplePersonService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

class PersonForm extends Div {

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField email = new TextField("Email");
    private final TextField phone = new TextField("Phone");
    private final DatePicker dateOfBirth = new DatePicker("Date Of Birth");
    private final TextField occupation = new TextField("Occupation");
    private final TextField role = new TextField("Role");
    private final Checkbox important = new Checkbox("Important");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button delete = new Button("", VaadinIcon.TRASH.create());

    private final SamplePersonService samplePersonService;
    private final SerializableRunnable refreshGridRunnable;
    private final BeanValidationBinder<SamplePerson> binder;
    private SamplePerson samplePerson;

    PersonForm(SamplePersonService samplePersonService, SerializableRunnable refreshGridRunnable) {
        this.samplePersonService = samplePersonService;
        this.refreshGridRunnable = refreshGridRunnable;
        setClassName("editor-layout");

        add(createFormLayout());
        add(createButtonLayout());

        // Configure Form
        binder = new BeanValidationBinder<>(SamplePerson.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        delete.addClickListener(this::clickDeleteButton);
        save.addClickListener(this::clickSaveButton);
        cancel.addClickListener(this::clickCancelButton);
    }

    private void clickDeleteButton(ClickEvent<Button> buttonClickEvent) {
        samplePersonService.delete(samplePerson.getId());
        clearForm();
        refreshGridRunnable.run();
        Notification.show("Data deleted");
    }

    private void clickCancelButton(ClickEvent<Button> buttonClickEvent) {
        clearForm();
        refreshGridRunnable.run();

        Notification.show("Data not saved");
    }

    private void clickSaveButton(ClickEvent<Button> buttonClickEvent) {
        try {
            if (this.samplePerson == null) {
                this.samplePerson = new SamplePerson();
            }
            binder.writeBean(this.samplePerson);
            this.samplePersonService.save(this.samplePerson);
            clearForm();
            refreshGridRunnable.run();
            Notification.show("Data updated");
            UI.getCurrent().navigate(CrudExampleView.class);
        } catch (ObjectOptimisticLockingFailureException exception) {
            Notification n = Notification.show(
                    "Error updating the data. Somebody else has updated the record while you were making changes.");
            n.setPosition(Notification.Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (ValidationException validationException) {
            Notification.show("Failed to update the data. Check again that all values are valid");
        }
    }

    private Div createFormLayout() {

        firstName.setRequired(true);
        lastName.setId("lastname-field");

        Div editorDiv = new Div();
        editorDiv.add(
                new FormLayout(
                        firstName, lastName, email, phone, dateOfBirth,
                        occupation, role, important));

        editorDiv.setClassName("editor");

        return editorDiv;
    }

    private HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        save.setId("save-button");
        cancel.setId("cancel-button");
        delete.setId("delete-button");

        buttonLayout.add(delete, cancel, save);
        return buttonLayout;
    }

    void clearForm() {
        populateForm(null);
    }

    void populateForm(SamplePerson value) {
        this.samplePerson = value;
        binder.readBean(this.samplePerson);
    }

}
