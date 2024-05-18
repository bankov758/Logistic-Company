package com.nbu.logisticcompany.entities.dtos.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UserRole {

    @Positive(message = "ID should be a positive integer")
    @NotNull(message = "Field is mandatory")
    private int userId;

    @NotNull(message = "Field is mandatory")
    @Size(min = 2, max = 64 , message = "Please enter role's name")
    private String role;

    public UserRole() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
