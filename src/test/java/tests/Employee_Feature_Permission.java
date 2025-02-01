package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class Employee_Feature_Permission extends BaseClass {
    private Home_Page homePage;
    private Users_Page usersPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        usersPage = new Users_Page(driver);
    }

    @Test(description = "Verify that after successful login, the user is successfully navigated to User list page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickUsersMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("users"));

        logger.info("User successfully navigated to the User list page");
    }

    @Test(description = "Verify that the user can give feature permission to an employee", priority = 2)
    public void verifyGivingFeaturePermission() throws InterruptedException {
        SmallWait(1000);

        String employee = "mark";

        usersPage.giveUserFeaturePermission(employee);

        logger.info("User successfully gives feature permission to - {}", employee);
    }
}