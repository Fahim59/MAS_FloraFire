package tests;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

public class Payment extends BaseClass {
    private Payment_Page paymentPage;

    @BeforeMethod
    public void initializePageObjects() {
        paymentPage = new Payment_Page(driver);
    }

    @Test(description = "Verify that customer can apply promo code and enter credit card information", priority = 1)
    public void verifyCustomerCreditCardInfoAndSubmitOrder() throws InterruptedException {
        SmallWait(1000);

        String cardNumber = jsonData.getJSONObject("payment").getString("cardNumber");
        String expiration = jsonData.getJSONObject("payment").getString("expiration");
        String cvv = jsonData.getJSONObject("payment").getString("cvv");

        String promo = jsonData.getJSONObject("payment").getString("promo");
        String actualMessage = jsonData.getJSONObject("payment").getString("promoMessage");
        String displayedMessage = paymentPage.applyPromoAndGetMessage(promo);

        if(displayedMessage.contains(actualMessage)){
            logger.info("Promo Code applied successfully");
        }
        else{
            logger.info("Promo Code could not be applied: {}", displayedMessage);
            promoApplied = false;
        }

        SmallWait(1000);

        paymentPage.enterPaymentDetails(cardNumber, expiration, cvv);

        logger.info("Customer applied promo and entered credit card information.");
    }

    @Test(description = "Verify the recurring payment details and submit an order", priority = 2)
    public void verifyRecurringPaymentDetails() throws InterruptedException {
        SmallWait(500);

        String password = jsonData.getJSONObject("registration_info").getString("password");

        /*
         * validating promo discount
         */

        if(promoApplied){
            String promoCode = jsonData.getJSONObject("payment").getString("promo");
            String[] promoDetails = paymentPage.fetchPromoDiscount();
            promoDiscount = Double.parseDouble(promoDetails[0]);

            Assert.assertEquals(promoCode, promoDetails[1],"Promo Code mismatch; should be: " +promoCode+ " but displayed: " +promoDetails[1]);
        }

        paymentPage.verifyRecurringOrderTable(packagePrice, licenseCount);

        paymentPage.clickSubmitOrderBtn();
        SmallWait(1000);
        paymentPage.enterPassword(password);
        paymentPage.clickConfirmBtn();

        logger.info("Customer verified recurring payment details and submitted the order.");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 3)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(15000);

        paymentPage.checkPaymentFailedMessage();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }
}