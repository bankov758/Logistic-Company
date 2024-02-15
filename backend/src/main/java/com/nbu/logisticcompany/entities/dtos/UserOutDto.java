package com.nbu.logisticcompany.entities.dtos;

import com.nbu.logisticcompany.entities.Role;

import java.util.Set;

public class UserOutDto {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles;

    public UserOutDto() {
    }

    public UserOutDto(int id, String username, String firstName, String lastName, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
