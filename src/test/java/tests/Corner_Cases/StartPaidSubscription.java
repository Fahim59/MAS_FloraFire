/*
 * Customer starts Paid Subscription Manually.
*/

package tests.Corner_Cases;

import base.BaseClass;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.testng.Assert;
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
        SmallWait(1000);
        paymentPage.verifyProratedOrderTable_Manual(licenseCount);

        SmallWait(1000);

        paymentPage.clickTermsBtn();

        Scroll_Down();

        paymentPage.clickSubmitOrderBtn();
        SmallWait(300);
        paymentPage.clickPaidSubscriptionConfirmBtn();

        logger.info("Customer verified prorated payment details and submitted the order.");
    }

    @Test(description = "Verify that after successful payment, the customer is successfully navigated to Receipt page", priority = 3)
    public void verifyCustomerNavigationAfterPayment() throws InterruptedException {
        SmallWait(1500);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("receipt"));

        logger.info("Customer successfully navigated to the Receipt page");
    }

    @Test(description = "Verify that customer can see the prorated payment details in receipt page", priority = 4)
    public void verifyCustomerReceiptPageWithProratedOrderDetails() throws InterruptedException {
        SmallWait(1000);
        receiptPage.verifyProratedOrderTable_Manual();

        logger.info("Customer viewed the receipt page and verified the prorated order details.");
    }

    @Test(description = "Verify that the customer has received the trial upgrade receipt in email", priority = 5)
    public void verifyCustomerReceivedTrialUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkTrialUpgradeReceipt();

        logger.info("Customer successfully received the Trail Upgrade receipt.");
    }

    public void checkTrialUpgradeReceipt() {
        String apiKey = jsonData.getJSONObject("mailosaur").getString("apiKey");
        String serverId = jsonData.getJSONObject("mailosaur").getString("serverId");
        String emailAddress = getEmail();
        String expectedCustomerName = customerName;
        String expectedSubject = jsonData.getJSONObject("registration_info").getString("trialUpgradeReceiptEmailSubject");

        HttpResponse<JsonNode> response = Unirest.get("https://mailosaur.com/api/messages")
                .basicAuth(apiKey, "")
                .queryString("server", serverId)
                .queryString("sentTo", emailAddress)
                .asJson();

        if (!response.getBody().getObject().getJSONArray("items").isEmpty()) {
            kong.unirest.json.JSONObject latestEmail = response.getBody().getObject().getJSONArray("items").getJSONObject(0);

            String messageId = latestEmail.getString("id");
            String emailSubject = latestEmail.getString("subject");

            logger.info("Email Subject: {}", emailSubject);

            if (!expectedSubject.equalsIgnoreCase(emailSubject)) {
                logger.info("Email subject does not match.");
                return;
            }

            HttpResponse<JsonNode> messageResponse = Unirest.get("https://mailosaur.com/api/messages/" + messageId)
                    .basicAuth(apiKey, "")
                    .asJson();

            kong.unirest.json.JSONObject messageContent = messageResponse.getBody().getObject();

            kong.unirest.json.JSONArray toArray = messageContent.getJSONArray("to");

            if (!toArray.isEmpty()) {
                String customerName = toArray.getJSONObject(0).getString("name");

                Assert.assertEquals(expectedCustomerName, customerName, "Customer Name mismatch; should be: " +expectedCustomerName+ " but displayed: " +customerName);
            }
            else {
                logger.info("No recipient details found.");
            }
        }
        else {
            logger.info("No emails found.");
        }
    }
}