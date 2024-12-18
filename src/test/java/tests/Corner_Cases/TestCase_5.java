/*
 * Scenario 5: Package Same – Addi. License Downgrade – Seasonal License Ney Buy
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_5 extends BaseClass {
    private LocationAndUser_Page locationAndUserPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    @BeforeMethod
    public void initializePageObjects() {
        locationAndUserPage = new LocationAndUser_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);

        packagePrice = 10.0;                       //Package Price

        upgradedLicenseCount = 8;                //Additional User Count Now

        seasonalMonthTotalDays = 30;           //(Month Days) Fixed
        seasonalMonthUsedDays = 30;           //Remaining Month Day

        seasonalLicenseCount = 2;           //Seasonal License Added
        perUserSeasonalLicensePrice = 5;   //Seasonal License Price
        seasonalMonth = 1;                //Month
    }

    @Test(description = "Verify that the customer can downgrade additional license(s), buys seasonal license and confirm the accuracy of recurring and prorated payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerAdditionalLicenseDowngradeAndSeasonalPurchase() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        /*
         * calculating Prior Package Prepaid details
         */

        locationAndUserPage.calculateSeasonalLicenseTotalFee_Prior();

        /*
         * calculating Today's Package Change details
         */

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        /*
         * downgrading additional license and purchase seasonal license
         */

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        locationAndUserPage.enterSeasonalLicenseAndMonth(seasonalLicenseCount, seasonalMonth);

        Scroll_Down();
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer downgraded additional license/s, purchase seasonal license successfully and clicked on save button.");

        /*
         * verifying prorated and recurring order in payment page and submit order
         */

        paymentPage.verifyProratedOrderTable();

        paymentPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        paymentPage.clickTermsBtn();
        Scroll_Down();

        customerName = paymentPage.fetchNameValue();

        paymentPage.clickSubmitOrderBtn();

        logger.info("Customer verifies prorated and recurring order price and submit the order");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 2)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(1000);

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the receipt page, check the prorated and recurring payment details", priority = 3)
    public void verifyCustomerReceiptPageWithRecurringAndProratedOrderDetails() throws InterruptedException {
        receiptPage.verifyProratedOrderTable();

        Scroll_Down();

        receiptPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        Scroll_Up();

        logger.info("Customer viewed the receipt page and verified the prorated and recurring order details.");
    }

    @Test(description = "Verify that the customer has received the subscription downgrade receipt in email", priority = 4)
    public void verifyCustomerReceivedSubscriptionDowngradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionDowngrade");

        logger.info("Customer successfully received the Subscription Downgrade receipt.");
    }
}