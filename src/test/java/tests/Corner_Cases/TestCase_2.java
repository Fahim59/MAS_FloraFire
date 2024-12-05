/*
 * Scenario 2: Package Same – Addi. License Upgrade – Seasonal License Same
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_2 extends BaseClass {
    private LocationAndUser_Page locationAndUserPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    @BeforeMethod
    public void initializePageObjects() {
        locationAndUserPage = new LocationAndUser_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);

        licenseCount = 2;           //Additional User Count
        upgradedLicenseCount = 4;  //Additional User Count Now
    }

    @Test(description = "Verify that the customer can purchase additional license(s), confirm the accuracy of prorated payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerAdditionalLicensePurchase() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.calculatePriorPackagePrepaid();
        locationAndUserPage.calculateTodayPackageChange();

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Scroll_Down();
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer upgraded additional license/s successfully and clicked on save button.");

        paymentPage.verifyProratedOrderTable();
//        paymentPage.verifyRecurringOrderTable();                 //Will do later

        paymentPage.clickTermsBtn();
        Scroll_Down();
        paymentPage.clickSubmitOrderBtn();

        logger.info("Customer verifies additional license price and submit the order");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 2)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(1000);

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the receipt page check the prorated payment details", priority = 3)
    public void verifyCustomerReceiptPageWithProratedOrderDetails() throws InterruptedException {
        receiptPage.verifyProratedOrderTable();

//        Scroll_Down();

//        receiptPage.verifyRecurringOrderTable();                 //Will do later
//
//        Scroll_Up();

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the seasonal license purchase receipt in email", priority = 4, enabled = false)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionUpgrade");

        logger.info("Customer successfully received the Subscription Upgrade receipt.");

        resetValue();
    }

    public void resetValue() {
        perUserLicensePrice = 0;
        perDayLicensePrice = 0;
        licenseRemainingAmount = 0;

        upgradedPerUserLicensePrice = 0;
        upgradedPerDayLicensePrice = 0;
        licenseNeedToPay = 0;

        licenseAdjustment = 0;

        totalDue = 0;
    }
}