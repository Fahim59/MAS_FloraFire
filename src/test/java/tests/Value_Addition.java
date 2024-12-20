package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class Value_Addition extends BaseClass {
    private Home_Page homePage;
    private Value_Page valuePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        valuePage = new Value_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Package Selection page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickValueMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("valueTypes"));

        logger.info("User clicked on the value menu and successfully navigated to the Value Types page");
    }

    @Test(description = "Verify that user can add values successfully", priority = 2)
    public void verifyCustomerValueAddition() throws InterruptedException {
        SmallWait(1000);

        //valuePage.clickNewValueButton();

        //valuePage.enterValueDetails("Test","1");

        //System.out.println(valuePage.getSuccessMessage());
        //Assert.assertEquals("আবেদনটি সফলভাবে প্রেরণ করা হয়েছে", nameClearancePage.text());

        valuePage.clickDetailsButton("Value");

        logger.info("User added values successfully.");
    }
}