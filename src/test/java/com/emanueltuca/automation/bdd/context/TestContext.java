package com.emanueltuca.automation.bdd.context;

import com.emanueltuca.automation.domain.DemoUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestContext {
    private DemoUser currentUser;
    private String username;
    private String password;


    public boolean hasCurrentUser() {
        return currentUser != null;
    }

    public void cleanup() {
        this.currentUser = null;
        this.username = null;
        this.password = null;
    }
}
