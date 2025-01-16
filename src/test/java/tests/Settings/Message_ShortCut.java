package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import pages.Settings.MessageShortCut_Page;

import java.util.Map;

public class Message_ShortCut extends BaseClass {
    private Home_Page homePage;
    private MessageShortCut_Page messageShortCutPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        messageShortCutPage = new MessageShortCut_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Message Shortcut page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickMessageShortcutsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("messageShortcut"));

        logger.info("User clicked on the Message Shortcut menu and successfully navigated to the Message Shortcut page");
    }

    public static String[] shortCutData(Map<String, String> valueData) {
        /*
         * data[0] = shortCode
         * data[1] = message
         */

        String shortCode = valueData.get("Short Code");
        String message = valueData.get("Message");

        return new String[] {shortCode, message};
    }

    @Test(description = "Verify that the user can add shortcode data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("ShortCuts")
    public void verifyShortCodeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] shortCodeInfo = shortCutData(data);

        String message = jsonData.getJSONObject("successMessage").getString("shortCut");

        messageShortCutPage.clickNewShortCutButton();

        messageShortCutPage.enterShortCode(shortCodeInfo[0], shortCodeInfo[1]);

        SmallWait(1000);

        Assert.assertEquals(message, messageShortCutPage.getSuccessMessage());

        messageShortCutPage.verifyShortCodeAddition(shortCodeInfo[0]);

        logger.info("Short Code {} added successfully", shortCodeInfo[0]);
    }
}