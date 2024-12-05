/*
 * Scenario 3: Package Same – Addi. License Downgrade – Seasonal License Same
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_4 extends BaseClass {
    private LocationAndUser_Page locationAndUserPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    @BeforeMethod
    public void initializePageObjects() {
        locationAndUserPage = new LocationAndUser_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);

        packagePrice = 10.0;                      //Package Price

        upgradedLicenseCount = 8;              //Additional User Count Now

        promoApplied = true;
        promoDiscount = 15;
    }

    @Test(description = "Verify that the customer can purchase additional license(s), confirm the accuracy of prorated payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerAdditionalLicensePurchase() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        Scroll_Down();
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer upgraded additional license/s successfully and clicked on save button.");

        paymentPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

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
    public void verifyCustomerReceiptPageWithProratedOrderDetails() {
        receiptPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the seasonal license purchase receipt in email", priority = 4)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionDowngrade");

        logger.info("Customer successfully received the Subscription Downgrade receipt.");
    }
}