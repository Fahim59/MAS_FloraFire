/*
 * Scenario 4: Package Same – Addi. License Downgrade – Seasonal License Same
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

        upgradedLicenseCount = 10;              //Additional User Count Now
    }

    @Test(description = "Verify that the customer can downgrade additional license(s), confirm the accuracy of recurring payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerAdditionalLicenseDowngrade() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        /*
         * calculating Today's Package Change details
         */

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        /*
         * downgrading additional license
         */

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Scroll(0,500);
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer downgraded additional license/s successfully and clicked on save button.");

        /*
         * verifying recurring order in payment page and submit order
         */

        paymentPage.verifyRecurringOrderTable(packagePrice, upgradedLicenseCount);

        paymentPage.clickTermsBtn();
        Scroll(0,500);

        customerName = paymentPage.fetchNameValue();

        paymentPage.clickSubmitOrderBtn();

        logger.info("Customer verifies recurring order price and submit the order");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 2)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(1000);

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the receipt page and check the recurring payment details", priority = 3)
    public void verifyCustomerReceiptPageWithRecurringOrderDetails() {
        receiptPage.verifyRecurringOrderTable_(packagePrice, upgradedLicenseCount);

        logger.info("Customer viewed the receipt page and verified the recurring order details.");
    }

    @Test(description = "Verify that the customer has received the subscription downgrade receipt in email", priority = 4, enabled = false)
    public void verifyCustomerReceivedSubscriptionDowngradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionDowngrade");

        logger.info("Customer successfully received the Subscription Downgrade receipt.");
    }
}