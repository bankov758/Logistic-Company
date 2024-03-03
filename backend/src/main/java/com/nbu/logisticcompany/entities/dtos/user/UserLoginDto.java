package com.nbu.logisticcompany.entities.dtos.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginDto {

    @NotNull(message = "Username can't be empty")
    @Size(min = 2, max = 40, message = "Username should be between 2 and 40 symbols")
    private String username;

    @NotNull(message = "Password can't be empty")
    @Size(min = 8, message = "Password should be longer than 8 symbols")
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
