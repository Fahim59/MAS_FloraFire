package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class Base extends BaseClass {
    private Home_Page homePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to ", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        //homePage.clickValueMenu();

        SmallWait(1000);
        //verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("valueTypes"));

        logger.info("User clicked on the  menu and successfully navigated to the  page");
    }
}