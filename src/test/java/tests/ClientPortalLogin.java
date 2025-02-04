package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.Login_Page;

public class ClientPortalLogin extends BaseClass {
    private Login_Page loginPage;

    @BeforeMethod
    public void initializePageObjects() {
        loginPage = new Login_Page(driver);
    }

    @Test(description = "Verify that a customer can log in into his tenant successfully", priority = 1)
    public void verifyCustomerSuccessfulLogin() throws InterruptedException {
        SmallWait(2000);

        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        Open_Website(tenantLink);

        if (userName != null && !userName.isEmpty()) {
            loginPage.enterLoginData(userName, jsonData.getJSONObject("registration_info").getString("password"));
        }
        else {
            userName = "testmustafizur+103@gmail.com";
            loginPage.enterLoginData(userName, jsonData.getJSONObject("registration_info").getString("password"));
        }

        logger.info("Customer logged in successfully.");
    }

    @Test(description = "Verify that after successful login, user can see the Home menu", priority = 2)
    public void verifyHomeMenuVisibilityAfterLogin() throws InterruptedException {
        SmallWait(1000);
        loginPage.verifyHomeMenuVisibility();

        logger.info("User successfully navigated to the Home page");
    }
}