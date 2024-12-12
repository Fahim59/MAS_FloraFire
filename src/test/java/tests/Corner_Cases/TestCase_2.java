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

        packagePrice = 10.0;                       //Package Price

        licenseCount = 8;                       //Additional User Count
        upgradedLicenseCount = 10;              //Additional User Count Now

        promoDiscount = 15;
    }

    @Test(description = "Verify that the customer can purchase additional license(s), confirm the accuracy of prorated payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerAdditionalLicensePurchase() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.calculatePriorPackagePrepaid();
        locationAndUserPage.calculateTodayPackageChange();

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        Scroll_Down();
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer upgraded additional license/s successfully and clicked on save button.");

        paymentPage.verifyProratedOrderTable();

        paymentPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        paymentPage.clickTermsBtn();
        Scroll_Down();

        customerName = paymentPage.fetchNameValue();

        paymentPage.clickSubmitOrderBtn();

        logger.info("Customer verifies additional license price and submit the order");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 2)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(1000);

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the receipt page check the prorated and recurring payment details", priority = 3)
    public void verifyCustomerReceiptPageWithProratedAndRecurringOrderDetails() throws InterruptedException {
        receiptPage.verifyProratedOrderTable();

        Scroll_Down();

        receiptPage.verifyRecurringOrderTable_(packagePrice, upgradedLicenseCount);

        Scroll_Up();

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the subscription upgrade receipt in email", priority = 4)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionUpgrade");

        logger.info("Customer successfully received the Subscription Upgrade receipt.");
    }
}