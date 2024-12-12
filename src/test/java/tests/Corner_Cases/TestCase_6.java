/*
 * Scenario 6: Package Upgrade – Addi. License Same – Seasonal License Same
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_6 extends BaseClass {
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

        upgradedPackageName = "Silver";

        packagePrice = 10.0;                        //Package Price
        licenseCount = 8;

        upgradedLicenseCount = 8;                //Additional User Count Now

        seasonalMonthTotalDays = 30;           //(Month Days) Fixed
        seasonalMonthUsedDays = 30;           //Remaining Month Day

        seasonalLicenseCount = 6;           //Seasonal License Added
        perUserSeasonalLicensePrice = 5;   //Seasonal License Price
        seasonalMonth = 1;                //Month

        promoDiscount = 15;
    }

    @Test(description = "Verify that the customer can upgrade package, confirm the accuracy of recurring payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerPackageUpgrade() throws InterruptedException {
        /*
         * calculating Prior Package Prepaid details
         */

        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.calculatePriorPackagePrepaid();
        locationAndUserPage.calculateSeasonalLicenseTotalFee_Prior();

        /*
         * package upgrade
         */

        packageSelectionPage.clickPackageTab();

        upgradedPackagePrice = packageSelectionPage.selectPackageAndGetValue(upgradedPackageName);

        Scroll_Down();
        packageSelectionPage.clickSaveBtn();

        /*
         * calculating Today's Package Change details
         */

        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.calculateTodayPackageChange();
        locationAndUserPage.calculateSeasonalLicenseTotalFee_Today();
        logger.info("Seasonal License Total Price: {}", upgradedTotalAmount);

        Object[] priceTable = locationAndUserPage.priceTable(upgradedLicenseCount);
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        /*
         * verifying prorated and recurring order in payment page and submit order
         */

        paymentPage.clickPaymentTab();

        logger.info("Customer upgraded package successfully and clicked on save button.");

        paymentPage.verifyProratedOrderTable();

        paymentPage.verifyRecurringOrderTable(upgradedPackagePrice, upgradedLicenseCount);

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

    @Test(description = "Verify that customer can see the receipt page check the recurring payment details", priority = 3)
    public void verifyCustomerReceiptPageWithRecurringOrderDetails() throws InterruptedException {
        receiptPage.verifyProratedOrderTable();

        Scroll_Down();

        receiptPage.verifyRecurringOrderTable(upgradedPackagePrice, upgradedLicenseCount);

        Scroll_Up();

        logger.info("Customer viewed the receipt page and verified the recurring order details.");
    }

    @Test(description = "Verify that the customer has received the subscription upgrade receipt in email", priority = 4)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionUpgrade");

        logger.info("Customer successfully received the Subscription Upgrade receipt.");
    }
}