/*
 * Scenario 2: Package Same – Addi. License Upgrade – Seasonal License Same
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

import java.io.IOException;

public class TestCase_2 extends BaseClass {
    private LocationAndUser_Page locationAndUserPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    private String className;

    @BeforeMethod
    public void initializePageObjects() {
        locationAndUserPage = new LocationAndUser_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);

        className = new Object(){}.getClass().getEnclosingClass().getSimpleName();

        packagePrice = 10.0;                       //Package Price

        licenseCount = 8;                        //Additional User Count
        upgradedLicenseCount = 10;              //Additional User Count Now
    }

    @Test(description = "Verify that the customer can purchase additional license(s), confirm the accuracy of prorated and recurring payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerAdditionalLicensePurchase() throws InterruptedException, IOException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        /*
         * calculating Prior Package Prepaid details
         */

        locationAndUserPage.calculatePriorPackagePrepaid();

        /*
         * calculating Today's Package Change details
         */

        locationAndUserPage.calculateTodayPackageChange();

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        /*
         * purchase new additional license
         */

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Scroll(0,500);
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer upgraded additional license/s successfully and clicked on save button.");

        /*
         * verifying prorated and recurring order in payment page and submit order
         */

        paymentPage.verifyProratedOrderTable();

        paymentPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        takeScreenshot(className);

        paymentPage.clickTermsBtn();
        Scroll(0,500);

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

    @Test(description = "Verify that customer can see the receipt page check the prorated and recurring payment details", priority = 3)
    public void verifyCustomerReceiptPageWithProratedAndRecurringOrderDetails() throws InterruptedException {
        receiptPage.verifyProratedOrderTable();

        Scroll(0,500);

        receiptPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        Scroll(0,-500);

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the subscription upgrade receipt in email", priority = 4, enabled = false)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionUpgrade");

        logger.info("Customer successfully received the Subscription Upgrade receipt.");
    }
}