package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class UserLogin extends BaseClass {
    private Login_Page loginPage;
    private Home_Page homePage;

    String userName, password;

    @BeforeMethod
    public void initializePageObjects() {
        loginPage = new Login_Page(driver);
        homePage = new Home_Page(driver);
    }

    @Test(description = "Verify that a user can log in successfully", priority = 1)
    public void verifyUserSuccessfulLogin() {
        Open_Website("");

        userName = jsonData.getJSONObject("login_info").getString("username"); //sean@qca6z4pm.mailosaur.net
        password = jsonData.getJSONObject("login_info").getString("password");

        loginPage.enterLoginDetails(userName, password);

        logger.info("User logged in successfully.");
    }

    @Test(description = "Verify that after successful login, user can see the Home menu", priority = 2)
    public void verifyHomeMenuVisibilityAfterLogin() throws InterruptedException {
        SmallWait(1000);
        homePage.verifyHomeMenuVisibility();

        logger.info("User successfully navigated to the Home page");
    }
}