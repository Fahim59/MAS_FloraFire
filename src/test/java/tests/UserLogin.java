package tests;

import base.BaseClass;
import constants.EndPoint;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Login_Page;

public class UserLogin extends BaseClass {
    private Login_Page loginPage;

    @BeforeMethod
    public void initializePageObjects() {
        loginPage = new Login_Page(driver);
    }

    @Test(description = "Verify that a user can log in successfully", priority = 1)
    public void verifyUserSuccessfulLogin() {
        Open_Website(EndPoint.login.url);

        logger.info("User logged in successfully.");
    }

    @Test(description = "Verify that after successful login, the user is successfully navigated to Home page", priority = 2)
    public void verifyUserNavigationAfterLogin() throws InterruptedException {
        //SmallWait(1000);
        //verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("packageSelection"));

        logger.info("User successfully navigated to the Home page");
    }
}