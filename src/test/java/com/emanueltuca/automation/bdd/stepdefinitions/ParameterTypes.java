package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.domain.DemoUser;
import com.emanueltuca.automation.domain.LoginErrorMessage;
import io.cucumber.java.ParameterType;

public class ParameterTypes {

    @ParameterType("account is locked|credentials are invalid|username is required|password is required")
    public LoginErrorMessage loginError(String errorMessageKey) {
        return LoginErrorMessage.fromKey(errorMessageKey);
    }


    @ParameterType("standard|locked out|problem")
    public DemoUser demoUser(String userKey) {
        return DemoUser.fromKey(userKey);
    }
}
