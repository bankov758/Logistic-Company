package com.nbu.logisticcompany.entities.dtos;

import com.nbu.logisticcompany.entities.Role;

public class UserRole {

    private int userId;
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
