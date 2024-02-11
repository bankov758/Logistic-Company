package com.nbu.logisticcompany.entities.dtos;

import javax.validation.constraints.Size;

public class UserRegisterDto {
    @Size(min = 2, max = 40, message = "Username should be between 2 and 40 symbols")
    private String username;

    @Size(min = 2, max = 32, message = "First name should be between 2 and 32 symbols")
    private String firstName;

    @Size(min = 2, max = 32, message = "Last name should be between 2 and 32 symbols")
    private String lastName;

    @Size(min = 8, message = "Password should be at least 8 symbols")
    private String password;

    private String confirmPassword;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String username, String firstName, String lastName, String password, String confirmPassword) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
