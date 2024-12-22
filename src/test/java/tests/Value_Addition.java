package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.util.Map;

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

    @Test(description = "Verify that user can add values successfully", dataProvider = "clientPortalData", dataProviderClass = DataSource.class, priority = 2, enabled = false)
    public void verifyValueAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String childValue = valueData.get("Name");
        String orderNo = valueData.get("Order");

        String message = jsonData.getJSONObject("successMessage").getString("value");

        valuePage.clickNewValueButton();

        valuePage.enterValueDetails(childValue, orderNo);

        Assert.assertEquals(message, valuePage.getSuccessMessage());

        SmallWait(1000);

        logger.info("User added values successfully.");
    }

    @Test(description = "Verify that user can add value list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 3)
    public void verifyAbandonReasonValueListAdditions(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String value = "Abandon Reason";

        String childValue = valueData.get("Name");
        String orderNo = valueData.get("Order");
        String preSelected = valueData.get("PreSelected");

        String message = jsonData.getJSONObject("successMessage").getString("value");

        if(valuePage.getValueTableTrSize() > 1){
            valuePage.selectValue(value);
        }
        if(valuePage.getValueTableTrSize() == 1){
            valuePage.clickDetailsButton(value);
        }

//        valuePage.selectValue(value);

//        valuePage.clickDetailsButton(value);
//
//        valuePage.clickNewValueButton();
//
//        valuePage.enterValueListDetails(childValue, orderNo, preSelected);
//
//        Assert.assertEquals(message, valuePage.getSuccessMessage());
//
//        SmallWait(1000);
//
//        logger.info("User added value list successfully.");
    }
}