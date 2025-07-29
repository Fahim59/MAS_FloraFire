package tests.Settings;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.CorporateSettings_Page;

public class Corporate_Settings extends BaseClass {
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

        String geocodingPreference = "None";
        String passwordResetDays = "90";
        String auditLogEmail = "testmustafizur@gmail.com";
        String hotOrderThreshold = "2";

        /* * Transaction Charges */

        String relayFee = "100";
        String overSeasRelayFee = "150";
        String wireOutDeliveryFee = "200";

        /* * Sales */

        String inventoryTracking = "Corporate Level"; //Store Level

        /* * Tax */

        String taxOnBusiness = "Yes";
        String taxOnDelivery = "Yes";
        String taxOnRelay = "No";
        String taxOnMerchandise = "Yes";
        String salesTax = "7";

        /* * Recipe Inventory Manage Type */

        String recipeType = "Bundle";    //Single or Bundle

        /* * POS */

        String enableDiscountOnOrder = "Yes";
        String allowPartialPayment = "Yes";
        String creditCardFee = "5";
        String enableCCFeeOnOrder = "Yes";

        /* * Gift Card */

        String enableCarryForward = "Yes";

        /* * Corporate Customers */

        String customerOne = "Maria Lopez";
        String customerTwo = "Emily Johnson";
        String customerThree = "Linda Brown";

        /* * Idle Time */

        String time = "3600";

        /* * Country */

        String country = "United States of America";

        /* * Terms And Conditions */

        String terms = "Payment is due within 30 days of the invoice date. A late fee of 1.5% per month will be applied to overdue balances. Returns and cancellations must be requested within 14 days and may be subject to a 15% restocking fee. Any disputes must be submitted in writing within 7 days of receipt.";

        /* * Default Print Messages */

        String receiptMessage = "Thank You! Visit Again!";
        String invoiceMessage = "Thank you for your Business! Your favorite florist any day, every day. Should you have inquiries concerning this invoice, please contact John: ";

        /* * Design Module */

        String cutOffTime = "1";

        /* * Email Service Type */

        String type = "ClickSend";

        /* * SMS Setup */

        String sendSms = "Yes";

        /* * Invoice Email Policy */

        String sendInvoice = "Yes";

        return new String[] {geocodingPreference, passwordResetDays, auditLogEmail, hotOrderThreshold,
                relayFee, overSeasRelayFee, wireOutDeliveryFee,
                inventoryTracking,
                taxOnBusiness, taxOnDelivery, taxOnRelay, taxOnMerchandise, salesTax,
                recipeType,
                enableDiscountOnOrder, allowPartialPayment, creditCardFee, enableCCFeeOnOrder,
                enableCarryForward,
                customerOne, customerTwo, customerThree,
                time,
                country,
                terms,
                receiptMessage, invoiceMessage,
                cutOffTime,
                type,
                sendSms,
                sendInvoice};
    }

    @Test(description = "Verify that the user can add  data successfully", priority = 2)
    public void verifyCorporateSettingsDataEntry() throws InterruptedException {
        String[] corporateSettingInfo = corporateSettingData();

        String saveMessage = jsonData.getJSONObject("successMessage").getString("corporateSettings");

        corporateSettingsPage.enterGeneralData(corporateSettingInfo[0],corporateSettingInfo[1],corporateSettingInfo[2], corporateSettingInfo[3]);
        logger.info("Successfully added Corporate Settings General data");

        Scroll(0, 250);

        corporateSettingsPage.enterTransactionChargeData(corporateSettingInfo[4],corporateSettingInfo[5],corporateSettingInfo[6]);
        logger.info("Successfully added Corporate Settings Transaction Charge data");

        Scroll(0, 950);

        corporateSettingsPage.selectInventoryTracking(corporateSettingInfo[7]);
        logger.info("Successfully added Corporate Settings Sales data");

        corporateSettingsPage.enterTaxSettings(corporateSettingInfo[8], corporateSettingInfo[9],corporateSettingInfo[10],
                corporateSettingInfo[11], corporateSettingInfo[12]);
        logger.info("Successfully added Corporate Settings Tax data");

        Scroll(0, 850);

        corporateSettingsPage.clickRecipeType(corporateSettingInfo[13]);
        logger.info("Successfully added Corporate Settings Recipe Inventory Manage Type data");

        corporateSettingsPage.enterPOSSettings(corporateSettingInfo[14],corporateSettingInfo[15],corporateSettingInfo[16],corporateSettingInfo[17]);
        logger.info("Successfully added Corporate Settings POS data");

        corporateSettingsPage.enableCarryForward(corporateSettingInfo[18]);
        logger.info("Successfully added Corporate Settings GiftCard data");

        Scroll(0, 600);

        corporateSettingsPage.selectCorporateCustomers(corporateSettingInfo[19], corporateSettingInfo[20], corporateSettingInfo[21]);
        logger.info("Successfully added Corporate Customer data");

        corporateSettingsPage.enterIdleTime(corporateSettingInfo[22]);
        logger.info("Successfully added Max Idle Time Before Session Timeout data");

        corporateSettingsPage.selectDefaultCountry(corporateSettingInfo[23]);
        logger.info("Successfully added default Country data");

        corporateSettingsPage.enterTermsAndConditions(corporateSettingInfo[24]);
        logger.info("Successfully added Terms And Conditions data");

        Scroll(0, 250);

        corporateSettingsPage.setPrintMessage(corporateSettingInfo[25], corporateSettingInfo[26]);
        logger.info("Successfully added Default Print Messages data");

        Scroll(0, 500);

        corporateSettingsPage.enterCutOffTime(corporateSettingInfo[27]);
        logger.info("Successfully added Design Module data");

        corporateSettingsPage.clickEmailType(corporateSettingInfo[28]);
        logger.info("Successfully added Email Service Type data");

        corporateSettingsPage.sendSmsAfterOrder(corporateSettingInfo[29]);
        logger.info("Successfully added Sms Settings data");

        corporateSettingsPage.sendInvoiceAfterOrder(corporateSettingInfo[30]);
        logger.info("Successfully added Email Invoice Email Policy data");

        corporateSettingsPage.clickSaveButton();

        Assert.assertEquals(saveMessage, corporateSettingsPage.getSuccessMessage());
    }
}