package tests.Settings;

import base.BaseClass;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.CorporateSettings_Page;

public class CorporateSettings extends BaseClass {
    private Home_Page homePage;
    private CorporateSettings_Page corporateSettingsPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        corporateSettingsPage = new CorporateSettings_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Corporate Settings page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickCorporateSettingsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("corporateSettings"));

        logger.info("User successfully navigated to the Corporate Settings page");
    }

    public static String[] corporateSettingData() {
        /* * General */

        String geocodingPreference = "Tomtom";
        String passwordResetDays = "90";
        String auditLogEmail = "testmustafizur@gmail.com";

        /* * Transaction Charges */

        String relayFee = "100";
        String overSeasRelayFee = "150";
        String wireOutDeliveryFee = "200";

        /* * Tax */

        String taxOnDelivery = "No";
        String taxOnRelay = "No";
        String salesTax = "7";

        /* * POS */

        String enableDiscountOnOrder = "Yes";
        String allowPartialPayment = "Yes";
        String creditCardFee = "5";
        String enableCCFeeOnOrder = "Yes";

        /* * Gift Card */

        String enableCarryForward = "Yes";

        return new String[] {geocodingPreference, passwordResetDays, auditLogEmail, relayFee, overSeasRelayFee, wireOutDeliveryFee,
                taxOnDelivery, taxOnRelay, salesTax, enableDiscountOnOrder, allowPartialPayment, creditCardFee, enableCCFeeOnOrder,
                enableCarryForward};
    }

    @Test(description = "Verify that the user can add  data successfully", priority = 2)
    public void verifyCorporateSettingsDataEntry() throws InterruptedException {
        String[] corporateSettingInfo = corporateSettingData();

        corporateSettingsPage.enterGeneralData(corporateSettingInfo[0],corporateSettingInfo[1],corporateSettingInfo[2]);

        Scroll(0, 250);

        logger.info("Successfully added Corporate Settings General data");

        corporateSettingsPage.enterTransactionChargeData(corporateSettingInfo[3],corporateSettingInfo[4],corporateSettingInfo[5]);

        Scroll(0, 750);

        logger.info("Successfully added Corporate Settings Transaction Charge data");

        corporateSettingsPage.enterTaxSettings(corporateSettingInfo[6],corporateSettingInfo[7],corporateSettingInfo[8]);

        Scroll(0, 850);

        logger.info("Successfully added Corporate Settings Tax data");

        corporateSettingsPage.enterPOSSettings(corporateSettingInfo[9],corporateSettingInfo[10],corporateSettingInfo[11],corporateSettingInfo[12]);

        logger.info("Successfully added Corporate Settings POS data");

        corporateSettingsPage.enableCarryForward(corporateSettingInfo[13]);

        logger.info("Successfully added Corporate Settings GiftCard data");
    }
}