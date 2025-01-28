/*
 * New Account
 * Scenario 10: Package Upgrade – Addi. License Upgrade – Seasonal License Same
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_10 extends BaseClass {
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

        packagePrice = 10.0;                                    //Package Price
        licenseCount = 8;                                     //Additional User Count

        upgradedLicenseCount = 10;                           //Additional User Count Now

        seasonalMonthTotalDays = 30;                       //(Month Days) Fixed
        seasonalMonthUsedDays = 30;                       //Remaining Month Day

        seasonalLicenseCount = 0;                       //Seasonal License Added
        perUserSeasonalLicensePrice = 0;               //Seasonal License Price
        seasonalMonth = 1;                            //Month
    }

    @Test(description = "Verify that the customer can upgrade package, upgrade licence and confirm the accuracy of prorated and recurring payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerPackageUpgradeAndLicensePurchase() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        /*
         * calculating Prior Package Prepaid details
         */

        locationAndUserPage.calculatePriorPackagePrepaid();
        locationAndUserPage.calculateSeasonalLicenseTotalFee_Prior();

        /*
         * package upgrade
         */

        packageSelectionPage.clickPackageTab();

        upgradedPackagePrice = packageSelectionPage.selectPackageAndGetValue(upgradedPackageName);

        Scroll(0,500);
        packageSelectionPage.clickSaveBtn();

        logger.info("Customer upgraded package to: {} level", upgradedPackageName);

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
         * buying new additional license
         */

        locationAndUserPage.enterAdditionalLicense(upgradedLicenseCount);

        Scroll(0,500);
        locationAndUserPage.clickSaveBtn();

        /*
         * verifying prorated and recurring order in payment page and submit order
         */

        paymentPage.verifyProratedOrderTable();

        paymentPage.verifyRecurringOrderTable(upgradedPackagePrice, upgradedLicenseCount);

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

        receiptPage.verifyRecurringOrderTable(upgradedPackagePrice, upgradedLicenseCount);

        Scroll(0,-500);

        logger.info("Customer viewed the receipt page and verified the prorated and recurring order details.");
    }

    @Test(description = "Verify that the customer has received the subscription upgrade receipt in email", priority = 4)
    public void verifyCustomerReceivedSubscriptionUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("subscriptionUpgrade");

        logger.info("Customer successfully received the Subscription Upgrade receipt.");
    }
}