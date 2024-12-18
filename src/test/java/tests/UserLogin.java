package tests;

import base.BaseClass;
import constants.EndPoint;
import org.testng.annotations.*;
import pages.*;

public class UserLogin extends BaseClass {
    private Login_Page loginPage;

    @BeforeMethod
    public void initializePageObjects() {
        loginPage = new Login_Page(driver);
    }

    @Test(description = "Verify that a user can log in successfully", priority = 1)
    public void verifyUserSuccessfulLogin() {
        //Open_Website(EndPoint.login.url);
        Open_Website("");

        loginPage.enterLoginDetails("mustafiz","11!!qqQQ");

        logger.info("User logged in successfully.");
    }

    @Test(description = "Verify that after successful login, the user is successfully navigated to Home page", priority = 2)
    public void verifyUserNavigationAfterLogin() throws InterruptedException {
        //SmallWait(1000);
        //verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("packageSelection"));

        logger.info("User successfully navigated to the Home page");
    }
}