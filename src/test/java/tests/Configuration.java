package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Home_Page;
import pages.Settings.TransactionType_Page;

import java.util.Map;

public class Configuration extends BaseClass {
    private Home_Page homePage;
    private TransactionType_Page configurationPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        configurationPage = new TransactionType_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Configuration page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickConfigurationMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("termCode"));

        logger.info("User clicked on the Configuration menu and successfully navigated to the Configuration list page");
    }

    public static String[] termCodeData(Map<String, String> valueData) {

        String code = valueData.get("Code");
        String description = valueData.get("Description");
        String netDueDays = valueData.get("Net Due Days");
        String agingBucket = valueData.get("Aging Bucket");
        String lateChargePercentage = valueData.get("Late Charge");
        String minLateCharge = valueData.get("Min Late Charge");
        String lateChargeStatement = valueData.get("Late Charge Description");

        return new String[] {code, description, netDueDays, agingBucket, lateChargePercentage, minLateCharge, lateChargeStatement};
    }

    @Test(description = "Verify that the user can add term code data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Configuration")
    public void verifyTermCodeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] termCodeInfo = termCodeData(data);

        String message = jsonData.getJSONObject("successMessage").getString("termCodeCreate");

        configurationPage.clickAddButton();

        configurationPage.enterTermCodeDetails(termCodeInfo[0], termCodeInfo[1], termCodeInfo[2], termCodeInfo[3], termCodeInfo[4], termCodeInfo[5], termCodeInfo[6]);

        SmallWait(1000);

        Assert.assertEquals(message, configurationPage.getSuccessMessage());

        configurationPage.verifyTermCodeAddition(termCodeInfo[0]);

        logger.info("Term Code {} added successfully", termCodeInfo[0]);
    }
}