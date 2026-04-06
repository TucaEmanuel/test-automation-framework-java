package flows;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.LoginPage;

public class LoginFlow {
    private final TestContext testContext;

    public LoginFlow(TestContext testContext) {
        this.testContext = testContext;
    }

    public void openLoginPage() {
        testContext.transitionTo(LoginPage.class).open();
    }

    public void loginAs(String username, String password) {
        testContext.getCurrentPage(LoginPage.class)
                .enterUsername(username)
                .enterPassword(password)
                .submitLoginExpectingSuccess();
    }

    public void loginAsInvalid(String username, String password) {
        testContext.getCurrentPage(LoginPage.class)
                .enterUsername(username)
                .enterPassword(password)
                .submitLoginExpectingFailure();
    }

}
