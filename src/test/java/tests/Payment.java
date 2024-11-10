package tests;

import base.BaseClass;
import constants.EndPoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
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

    @Test(description = "Verifies that a customer can enter all credit card information and successfully submit an order", priority = 1)
    public void verifyCustomerCreditCardInfoAndSubmitOrder() throws InterruptedException {
        SmallWait(500);

        String cardNumber = jsonData.getJSONObject("payment").getString("cardNumber");
        String expiration = jsonData.getJSONObject("payment").getString("expiration");
        String cvv = jsonData.getJSONObject("payment").getString("cvv");

        String promo = jsonData.getJSONObject("payment").getString("promo");
        String message = jsonData.getJSONObject("payment").getString("promoMessage");

        paymentPage.enterPaymentDetails(cardNumber, expiration, cvv);

        //paymentPage.applyPromo(promo, message);

        logger.info("Customer entered all credit card information and submitted the order.");
    }

    @Test(description = "Verifies that after successful login, the customer is successfully navigated to Package Selection page", priority = 2, enabled = false)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("packageSelection"));

        logger.info("Customer clicked the login button and verified navigation to the Package Selection page");
    }
}