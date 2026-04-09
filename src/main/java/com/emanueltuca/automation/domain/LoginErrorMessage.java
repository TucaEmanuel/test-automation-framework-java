package com.emanueltuca.automation.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LoginErrorMessage {

    ACCOUNT_LOCKED(
            "account is locked",
            "Epic sadface: Sorry, this user has been locked out."
    ),

    INVALID_CREDENTIALS(
            "credentials are invalid",
            "Epic sadface: Username and password do not match any user in this service"),

    USERNAME_REQUIRED(
            "username is required",
            "Epic sadface: Username is required"),

    PASSWORD_REQUIRED(
            "password is required",
            "Epic sadface: Password is required");

    private final String key;
    private final String errorText;

    LoginErrorMessage(String key, String errorText) {
        this.key = key;
        this.errorText = errorText;
    }

    public static LoginErrorMessage fromKey(String key) {
        return Arrays.stream(values())
                .filter(e -> e.key.equalsIgnoreCase(key.trim()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown login error: " + key)
                );
    }
}
