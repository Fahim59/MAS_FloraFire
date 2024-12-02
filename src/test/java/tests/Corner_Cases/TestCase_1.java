/*
 * Scenario 1: Seasonal License Purchase
*/

package tests.Corner_Cases;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class TestCase_1 extends BaseClass {
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

        seasonalMonthTotalDays = 30;         //(Month Days) Fixed
        seasonalMonthUsedDays = 30;         //Remaining Month Day

        seasonalLicenseCount = 2;         //Seasonal License Added
        perUserSeasonalLicensePrice = 5; //Seasonal License Price
        seasonalMonth = 1;              //Month
    }

    @Test(description = "Verify that the customer can purchase new seasonal license(s), confirm the accuracy of prorated payment details and successfully submit the order.", priority = 1)
    public void verifyCustomerNewSeasonalLicensePurchase() throws InterruptedException {
        locationAndUserPage.clickLocationTab();

        SmallWait(1000);

        locationAndUserPage.calculateSeasonalLicenseTotalFee_Prior();

        locationAndUserPage.enterSeasonalLicenseAndMonth(seasonalLicenseCount, seasonalMonth);

        Scroll_Down();
        locationAndUserPage.clickSaveBtn();

        logger.info("Customer purchased new seasonal license/s successfully and clicked on save button.");

        paymentPage.verifyProratedOrderTable();

        //paymentPage.verifyRecurringOrderTable();    // Will do later

        paymentPage.clickTermsBtn();
        Scroll_Down();
        paymentPage.clickSubmitOrderBtn();

        logger.info("Customer verifies seasonal license price and submit the order");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 2)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(1000);

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the receipt page check the prorated payment details", priority = 3)
    public void verifyCustomerReceiptPageWithProratedOrderDetails() throws InterruptedException {
        //receiptPage.verifyProratedOrderTable();
        receiptPage.verifyProratedOrderTable();

        //Scroll_Down();

        //receiptPage.verifyRecurringOrderTable();

        //Scroll_Up();

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the seasonal license purchase receipt in email", priority = 4, enabled = false)
    public void verifyCustomerReceivedSeasonalLicenseReceipt() throws InterruptedException {
        SmallWait(60000);

        checkReceipt("seasonalLicense");

        packageSelectionPage.clickPackageTab();

        logger.info("Customer successfully received the Seasonal License purchase receipt.");

        resetValue();
    }

    public void resetValue() {
        seasonalLicenseTotalPrice = 0;
        totalDue = 0;
    }
}