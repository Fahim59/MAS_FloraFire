package tests.Settings;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.CreditCardSettings_Page;

public class CreditCard_Settings extends BaseClass {
    private Home_Page homePage;
    private CreditCardSettings_Page creditCardSettingsPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        creditCardSettingsPage = new CreditCardSettings_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Credit Card Settings page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickCreditCardSettingsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("creditCardSettings"));

        logger.info("User successfully navigated to the Credit Card Settings page");
    }

    @Test(description = "Verify that the user can add Credit Card data successfully", priority = 2)
    public void verifyCredit_CardDataEntry() throws InterruptedException {
        String message = jsonData.getJSONObject("successMessage").getString("creditCard");

        String publicKey = jsonData.getJSONObject("creditCard").getString("publicKey");
        String secretKey = jsonData.getJSONObject("creditCard").getString("secretKey");
        String developerId = jsonData.getJSONObject("creditCard").getString("developerId");
        String versionNumber = jsonData.getJSONObject("creditCard").getString("versionNumber");
        String url = jsonData.getJSONObject("creditCard").getString("url");

        creditCardSettingsPage.enterCreditCardSettings(publicKey, secretKey, developerId, versionNumber, url);

        Assert.assertEquals(message, creditCardSettingsPage.getSuccessMessage());

        logger.info("Successfully added Credit Card Settings data");
    }
}