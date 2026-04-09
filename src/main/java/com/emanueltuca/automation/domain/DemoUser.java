package com.emanueltuca.automation.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DemoUser {
    STANDARD_USER("standard", "standard_user", "secret_sauce"),
    LOCKED_OUT_USER("locked out", "locked_out_user", "secret_sauce"),
    PROBLEM_USER("problem", "problem_user", "secret_sauce");

    private final String key;
    private final String username;
    private final String password;

    DemoUser(String key, String username, String password) {
        this.key = key;
        this.username = username;
        this.password = password;
    }

    public static DemoUser fromKey(String key) {
        return Arrays.stream(values())
                .filter(e -> e.key.equalsIgnoreCase(key.trim()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown login error: " + key)
                );
    }
}
