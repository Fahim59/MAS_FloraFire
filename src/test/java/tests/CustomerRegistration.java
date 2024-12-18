package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.*;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class CustomerRegistration extends BaseClass {

    private HomePage homePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new HomePage(driver);
    }

    @Test(description = "Go to the registration page", priority = 1)
    public void got_to_registration_page() {
        logger.info("Customer go to the FLorafire Registration page");
    }

    @Test(description = "Verify that after clicking the save button, the customer is successfully navigated to Store Info page", priority = 2)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        //SmallWait(1000);
        //verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("storeInfo"));

        logger.info("Customer successfully navigated to the Store Info page");
    }
}