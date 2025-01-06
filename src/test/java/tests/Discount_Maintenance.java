package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.util.Map;

public class Discount_Maintenance extends BaseClass {
    private Home_Page homePage;
    private DiscountMaintenance_Page discountMaintenancePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        discountMaintenancePage = new DiscountMaintenance_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Discount Maintenance page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickDiscountMaintenanceMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("discountList"));

        logger.info("User successfully navigated to the Discount Maintenance page");
    }

    public static String[] discountData(Map<String, String> valueData) {
        /*
         * data[0] = code
         * data[1] = type
         * data[2] = amount
         * data[3] = status
         * data[4] = endDate
         * data[5] = applies_on
         * data[6] = description
         */

        String code = valueData.get("Code");
        String type = valueData.get("Type");
        String amount = valueData.get("Amount");
        String status = valueData.get("Status");
        String endDate = valueData.get("End Date");
        String applies_on = valueData.get("Applies On");
        String description = valueData.get("Description");

        return new String[] {code, type, amount, status, endDate, applies_on, description};
    }

    @Test(description = "Verify that the user can add Discount data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Discount")
    public void verifyDiscountDataEntry(Map<String, String> data) throws InterruptedException {
        String[] discountInfo = discountData(data);

        String createMessage = jsonData.getJSONObject("successMessage").getString("discountCreate");

        discountMaintenancePage.clickNewDiscountButton();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("discountMaintenance"));

        discountMaintenancePage.enterDiscountDetails(discountInfo[0], discountInfo[1], discountInfo[2], discountInfo[3],
                discountInfo[4], discountInfo[5], discountInfo[6]);

        Assert.assertEquals(createMessage, discountMaintenancePage.getSuccessMessage());

        discountMaintenancePage.verifyDiscountAddition(discountInfo[0]);

        logger.info("Successfully added data - {}", discountInfo[0]);
    }
}