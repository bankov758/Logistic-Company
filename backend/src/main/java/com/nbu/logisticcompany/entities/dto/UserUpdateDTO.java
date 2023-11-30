package com.nbu.logisticcompany.entities.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class UserUpdateDTO {

    @Positive(message = "Id should be a positive number")
    private int id;

    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    private String currentPassword;

    private String newPassword;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(int id, String firstName, String lastName, String currentPassword, String newPassword) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}