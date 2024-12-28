package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class Base extends BaseClass {
    private Home_Page homePage;

    String userName, password;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
    }

    @Test(description = "Verify that a user can ", priority = 1)
    public void verifyUser() {

        logger.info("User .");
    }
}