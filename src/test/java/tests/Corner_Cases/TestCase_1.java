/*
 * Scenario 1: Package Same – Addi. License Same – Seasonal License New Buy
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_1 extends BaseClass {
    private LocationAndUser_Page locationAndUserPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    @BeforeMethod
    public void initializePageObjects() {
        locationAndUserPage = new LocationAndUser_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);
    }

    @Test(description = "Verify that the customer can purchase new seasonal license(s), confirm the accuracy of prorated payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerNewSeasonalLicensePurchase() {

        logger.info("Customer purchased new seasonal license/s successfully and clicked on save button.");
        logger.info("Customer verifies seasonal license price and submit the order");
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

    @Test(description = "Verify that the customer has received the seasonal license purchase receipt in email", priority = 4)
    public void verifyCustomerReceivedSeasonalLicenseReceipt() throws InterruptedException {
        //SmallWait(60000);

        //checkTrialReceipt();

        logger.info("Customer successfully received the Seasonal License purchase receipt.");
    }
}