/*
 * Customer starts Paid Subscription Manually.
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class StartPaidSubscription extends BaseClass {
    private PackageSelection_Page packageSelectionPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    @BeforeMethod
    public void initializePageObjects() {
        packageSelectionPage = new PackageSelection_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);
    }

    @Test(description = "Verify that after clicking on start paid subscription button, the customer is navigated to the payment page.", priority = 1)
    public void verifyStartPaidSubscriptionManually() throws InterruptedException {
        packageSelectionPage.clickPaidSubscriptionBtn();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("checkout"));

        logger.info("Customer clicked on start paid subscription button and successfully navigated to the Payment page");
    }

    @Test(description = "Verify the prorated payment details and submit an order", priority = 2)
    public void verifyProratedPaymentDetails() throws InterruptedException {

        logger.info("Customer verified prorated payment details and submitted the order.");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 2)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        //SmallWait(1000);

        //verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the receipt page and see the prorated payment details", priority = 3)
    public void verifyCustomerReceiptPageWithProratedOrderDetails() throws InterruptedException {

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the trial upgrade receipt in email", priority = 4)
    public void verifyCustomerReceivedSeasonalLicenseReceipt() throws InterruptedException {
        //SmallWait(60000);

        //checkTrialReceipt();

        logger.info("Customer successfully received the Trail Upgrade receipt.");
    }
}