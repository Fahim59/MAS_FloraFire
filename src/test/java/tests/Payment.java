package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class Payment extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Payment.class);

    private Payment_Page paymentPage;

    FileReader data;
    JSONObject jsonData;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);

            jsonData = new JSONObject(tokener);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (data != null) {
                data.close();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        paymentPage = new Payment_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify that customer can apply promo code and enter credit card information", priority = 1, enabled = true)
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
        }

        SmallWait(1000);

        paymentPage.enterPaymentDetails(cardNumber, expiration, cvv);

        logger.info("Customer applied promo and entered credit card information.");
    }

    @Test(description = "Verify the recurring payment details and submit an order", priority = 2)
    public void verifyRecurringPaymentDetails() throws InterruptedException {
        SmallWait(500);

        String password = jsonData.getJSONObject("registration_info").getString("password");

        double getPackagePrice = paymentPage.fetchPackagePrice();

        int licenseCount = Integer.parseInt(jsonData.getJSONObject("storeInfo").getString("count"));
        double[] licenseDetails = paymentPage.fetchLicenseDetails();

        String promoCode = jsonData.getJSONObject("payment").getString("promo");
        String[] promoDetails = paymentPage.fetchPromoDiscount();
        promoDiscount = Double.parseDouble(promoDetails[0]);

        subTotal = packagePrice + totalLicensePrice;
        double getSubTotal = paymentPage.fetchSubtotal();

        recurringFee = subTotal - promoDiscount;
        double getRecurringFee = paymentPage.fetchRecurringFee();

        Assert.assertEquals(packagePrice, getPackagePrice,"Package price mismatch; should be: " +packagePrice+ " but displayed: " +getPackagePrice);

        Assert.assertEquals(totalLicensePrice , licenseDetails[0],"Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +licenseDetails[0]);
        Assert.assertEquals(perUserLicensePrice , licenseDetails[1],"Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +licenseDetails[1]);
        Assert.assertEquals(licenseCount , licenseDetails[2],"License count mismatch; should be: " +licenseCount+ " but displayed: " +licenseDetails[2]);

        Assert.assertEquals(subTotal, getSubTotal,"Subtotal mismatch; should be: " +subTotal+ " but displayed: " +getSubTotal);

        Assert.assertEquals(promoCode, promoDetails[1],"Promo Code mismatch; should be: " +promoCode+ " but displayed: " +promoDetails[1]);

        Assert.assertEquals(recurringFee, getRecurringFee,"Recurring Fee mismatch; should be: " +recurringFee+ " but displayed: " +getRecurringFee);

        //paymentPage.clickTermsBtn();           //Extra

        paymentPage.clickSubmitOrderBtn();
        SmallWait(1000);
        paymentPage.enterPassword(password);
        paymentPage.clickConfirmBtn();

        logger.info("Customer verified recurring payment details and submitted the order.");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 3, enabled = true)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(15000);
        //SmallWait(1000);

        paymentPage.checkPaymentFailedMessage();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }
}