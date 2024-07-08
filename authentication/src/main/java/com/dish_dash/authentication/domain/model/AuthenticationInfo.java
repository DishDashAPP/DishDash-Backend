package com.dish_dash.authentication.domain.model;

@Data
@NoArgsConstructor
public class AuthenticationInfo {
    private String userID;
    private String username;
    private String password;

    public AuthenticationInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
