package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import pages.Settings.TransactionType_Page;

import java.util.Map;

public class TransactionType extends BaseClass {
    private Home_Page homePage;
    private TransactionType_Page transactionTypePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        transactionTypePage = new TransactionType_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Payment Transaction Code page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickTransactionTypeSettingsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("transactionTypeSettings"));

        logger.info("User clicked on the Transaction Type menu and successfully navigated to the Transaction Code list page");
    }

    public static String[] transactionCodeData(Map<String, String> valueData) {

        String code = valueData.get("Code");
        String title = valueData.get("Title");
        String active = valueData.get("Active");
        String type = valueData.get("Type");

        return new String[] {code, title, active, type};
    }

    @Test(description = "Verify that the user can add transaction code data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Payment Transaction")
    public void verifyTransactionCodeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] transactionCodeInfo = transactionCodeData(data);

        String message = jsonData.getJSONObject("successMessage").getString("transactionType");

        transactionTypePage.clickAddButton();

        transactionTypePage.enterTransactionCodeDetails(transactionCodeInfo[0], transactionCodeInfo[1], transactionCodeInfo[2], transactionCodeInfo[3]);

        SmallWait(1000);

        Assert.assertEquals(message, transactionTypePage.getSuccessMessage());

        transactionTypePage.verifyTransactionCodeAddition(transactionCodeInfo[0]);

        logger.info("Transaction Code {} added successfully", transactionCodeInfo[0]);
    }
}