package com.nbu.logisticcompany.entities.dtos.user;

public class UserLoginDto {

//    @JsonProperty("username")
//    @NotNull(message = "Username can't be empty")
//    @Size(min = 2, max = 40, message = "Username should be between 2 and 40 symbols")
    private String usernamef;

//    @JsonProperty("password")
//    @NotNull(message = "Password can't be empty")
//    @Size(min = 8, message = "Password should be longer than 8 symbols")
    private String passwordf;

    public UserLoginDto() {
    }

    public UserLoginDto(String _usernamef, String _passwordf) {
        usernamef = _usernamef;
        passwordf = _passwordf;
    }

    public String getUsernamef() {
        return usernamef;
    }

    public void setUsernamef(String username) {
        this.usernamef = username;
    }

    public String getPasswordf() {
        return passwordf;
    }

    public void setPasswordf(String password) {
        this.passwordf = password;
    }


}
