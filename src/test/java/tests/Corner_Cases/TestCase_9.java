/*
 * Scenario 9: Package Downgrade – Addi. License Upgrade – Seasonal License Same
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_9 extends BaseClass {
    private PackageSelection_Page packageSelectionPage;
    private LocationAndUser_Page locationAndUserPage;
    private Payment_Page paymentPage;
    private Receipt_Page receiptPage;

    @BeforeMethod
    public void initializePageObjects() {
        packageSelectionPage = new PackageSelection_Page(driver);
        locationAndUserPage = new LocationAndUser_Page(driver);
        paymentPage = new Payment_Page(driver);
        receiptPage = new Receipt_Page(driver);

        upgradedPackageName = "Bronze";

        upgradedLicenseCount = 10;                             //Additional User Count Now

        promoDiscount = 15;
    }

    @Test(description = "Verify that the customer can downgrade package, upgrade licence and confirm the accuracy of recurring payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerPackageDowngradeAndLicensePurchase() throws InterruptedException {
        /*
         * package downgrade
         */
        upgradedPackagePrice = packageSelectionPage.selectPackageAndGetValue(upgradedPackageName);

        packageSelectionPage.clickSaveBtn();

        logger.info("Customer downgraded package to: {} level", upgradedPackageName);

        /*
         * calculating downgraded package's license details and upgrading license
         */

        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        Scroll_Down();
        locationAndUserPage.clickSaveBtn();

        /*
         * verifying recurring order in payment page and submit order
         */

        paymentPage.verifyRecurringOrderTable(upgradedPackagePrice, upgradedLicenseCount);

        paymentPage.clickTermsBtn();
        Scroll_Down();

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

    @Test(description = "Verify that customer can see the receipt page check the recurring payment details", priority = 3)
    public void verifyCustomerReceiptPageWithRecurringOrderDetails() {
        receiptPage.verifyRecurringOrderTable_(upgradedPackagePrice, upgradedLicenseCount);

        logger.info("Customer viewed the receipt page and verified the recurring order details.");
    }

    @Test(description = "Verify that the customer has received the subscription upgrade receipt in email", priority = 4)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionDowngrade");

        logger.info("Customer successfully received the Subscription Downgrade receipt.");
    }
}