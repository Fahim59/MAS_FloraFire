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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test(description = "Verify the prorated and recurring payment details and submit an order", priority = 2)
    public void verifyProratedAndRecurringPaymentDetails() throws InterruptedException {
        SmallWait(1000);
        paymentPage.verifyProratedOrderTable_Manual();

        SmallWait(500);
        paymentPage.verifyRecurringOrderTable_Manual();

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

    @Test(description = "Verify that customer can see the prorated and recurring payment details in receipt page", priority = 4)
    public void verifyCustomerReceiptPageWithProratedAndRecurringOrderDetails() throws InterruptedException {
        SmallWait(1000);
        receiptPage.verifyProratedOrderTable();

        Scroll_Down();
        receiptPage.verifyRecurringOrderTable();

        logger.info("Customer viewed the receipt page and verified the prorated and recurring order details.");
    }

    @Test(description = "Verify that the customer has received the trial upgrade receipt in email", priority = 5)
    public void verifyCustomerReceivedTrialUpgradeReceipt() throws InterruptedException {
        SmallWait(60000);

        checkTrialUpgradeReceipt();

        logger.info("Customer successfully received the Trail Upgrade receipt.");
    }

    @Test(description = "Verify that a customer can navigate back to the package tab and check paid subscription data", priority = 6)
    public void verifyCustomerNavigationToPackageTabAndSubscriptionData() throws InterruptedException, ParseException {
        packageSelectionPage.clickPackageTab();

        SmallWait(1500);

        /*
         * validating package name
        */

        String packageText = packageSelectionPage.getPackageText();
        Matcher packageMatcher = Pattern.compile("Current Package: (.+)").matcher(packageText);
        String packageName = jsonData.getJSONObject("packageDetails").getString("package");

        if (packageMatcher.find()) {
            Assert.assertEquals(packageName, packageMatcher.group(1), "Package name mismatch; should be: " +packageName+ " but displayed: " +packageMatcher.group(1));
        }
        else{ Assert.fail("Package text not found"); }

        /*
         * validating package start date
        */

        String packageStartText = packageSelectionPage.getTrialStartText();
        Matcher packageStartMatcher = Pattern.compile("Package Start Date : (\\d{1,2}/\\d{1,2}/\\d{4})").matcher(packageStartText);
        String startDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        if (packageStartMatcher.find()) {
            String extractedDate = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("M/d/yyyy").
                    parse(packageStartMatcher.group(1)));

            Assert.assertEquals(startDate, extractedDate, "Package Start date mismatch; should be: " +startDate+ " but displayed: " +extractedDate);
        }
        else{ Assert.fail("Package start date not found"); }

        /*
         * validating next recurring date
        */

        String nextRecurringText = packageSelectionPage.getTrialEndText();
        Matcher nextRecurringMatcher = Pattern.compile("Next Recurring Date: (\\d{1,2}/\\d{1,2}/\\d{4})").matcher(nextRecurringText);
        String endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        if (nextRecurringMatcher.find()) {
            String extractedDate = LocalDate.parse(nextRecurringMatcher.group(1), DateTimeFormatter.ofPattern("M/d/yyyy")).
                    format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            Assert.assertEquals(endDate, extractedDate, "Next Recurring Date mismatch; should be: " +endDate+ " but displayed: " +extractedDate);
        }
        else{ Assert.fail("Next Recurring Date not found"); }

        logger.info("Customer navigated back to the package page and verified the subscription data.");
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