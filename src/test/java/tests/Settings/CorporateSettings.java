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

        String geocodingPreference = "Tomtom"; //0
        String passwordResetDays = "90"; //1
        String auditLogEmail = "testmustafizur@gmail.com"; //2

        /* * Transaction Charges */

        String relayFee = "100"; //3
        String overSeasRelayFee = "150"; //4
        String wireOutDeliveryFee = "200"; //5

        /* * Tax */

        String taxOnDelivery = "No"; //6
        String taxOnRelay = "No"; //7
        String salesTax = "7"; //8

        /* * Recipe Inventory Manage Type */

        String recipeType = "Bundle";  //9    //Single or Bundle

        /* * POS */

        String enableDiscountOnOrder = "Yes"; //10
        String allowPartialPayment = "Yes"; //11
        String creditCardFee = "5"; //12
        String enableCCFeeOnOrder = "Yes"; //13

        /* * Gift Card */

        String enableCarryForward = "Yes"; //14

        /* * Corporate Customers */

        String customerOne = "Maria Lopez"; //15
        String customerTwo = "Emily Johnson"; //16
        String customerThree = "Linda Brown"; //17

        return new String[] {geocodingPreference, passwordResetDays, auditLogEmail, relayFee, overSeasRelayFee, wireOutDeliveryFee,
                taxOnDelivery, taxOnRelay, salesTax, recipeType, enableDiscountOnOrder, allowPartialPayment, creditCardFee, enableCCFeeOnOrder,
                enableCarryForward, customerOne, customerTwo, customerThree};
    }

    @Test(description = "Verify that the user can add  data successfully", priority = 2)
    public void verifyCorporateSettingsDataEntry() throws InterruptedException {
        String[] corporateSettingInfo = corporateSettingData();

        corporateSettingsPage.enterGeneralData(corporateSettingInfo[0],corporateSettingInfo[1],corporateSettingInfo[2]);
        logger.info("Successfully added Corporate Settings General data");

        Scroll(0, 250);

        corporateSettingsPage.enterTransactionChargeData(corporateSettingInfo[3],corporateSettingInfo[4],corporateSettingInfo[5]);
        logger.info("Successfully added Corporate Settings Transaction Charge data");

        Scroll(0, 750);

        corporateSettingsPage.enterTaxSettings(corporateSettingInfo[6],corporateSettingInfo[7],corporateSettingInfo[8]);
        logger.info("Successfully added Corporate Settings Tax data");

        Scroll(0, 850);

        corporateSettingsPage.clickRecipeType(corporateSettingInfo[9]);
        logger.info("Successfully added Corporate Settings Recipe Inventory Manage Type data");

        corporateSettingsPage.enterPOSSettings(corporateSettingInfo[10],corporateSettingInfo[11],corporateSettingInfo[12],corporateSettingInfo[13]);
        logger.info("Successfully added Corporate Settings POS data");

        corporateSettingsPage.enableCarryForward(corporateSettingInfo[14]);
        logger.info("Successfully added Corporate Settings GiftCard data");

        Scroll(0, 500);

        corporateSettingsPage.selectCorporateCustomers(corporateSettingInfo[15], corporateSettingInfo[16], corporateSettingInfo[17]);
        logger.info("Successfully added Corporate Customer data");

        corporateSettingsPage.clickSaveButton();
    }
}