package com.vaadin.workshop.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class SamplePerson extends AbstractEntity {

    @NotBlank(message = "First name must not be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Email must not be null")
    @Email(message = "Email must be in a valid format")
    private String email;

    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$",
            message = "Phone number has an invalid format")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth must not be null")
    private LocalDate dateOfBirth;

    @Size(max = 100, message = "Occupation must not exceed 100 characters")
    private String occupation;

    @NotBlank(message = "Role must not be empty")
    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;

    private boolean important;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean isImportant() {
        return important;
    }
    public void setImportant(boolean important) {
        this.important = important;
    }

}
