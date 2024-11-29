package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class Receipt extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Receipt.class);

    private Receipt_Page receiptPage;
    private PackageSelection_Page packageSelectionPage;

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
        receiptPage = new Receipt_Page(driver);
        packageSelectionPage = new PackageSelection_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify that customer can see the receipt page and see the payment details", priority = 1, enabled = true)
    public void verifyCustomerReceiptPageWithRecurringOrderDetails() throws InterruptedException {
        SmallWait(1000);

        int totalUser = Integer.parseInt(jsonData.getJSONObject("storeInfo").getString("count"));
        String cardNumber = jsonData.getJSONObject("payment").getString("cardNumber");

        receiptPage.verifyTrialText();
        receiptPage.verifyTotalPurchaseAmount();
        receiptPage.verifyCardTypeAndEnding(cardNumber);

        Scroll_Down();

        receiptPage.verifyRecurringOrderTable(totalUser);

        logger.info("Customer viewed the receipt page and verified the recurring order details.");
    }

    @Test(description = "Verify that a customer can download the receipt and validate the data within it", priority = 2, enabled = true)
    public void verifyCustomerReceiptDownloadAndDataValidation() throws InterruptedException {
        SmallWait(1000);

        receiptPage.clickDownloadBtn();

        SmallWait(2000);

        String downloadDir = new ConfigLoader().initializeProperty().getProperty("downloadFilePath").replace("${user.home}", System.getProperty("user.home"));

        File latestFile = receiptPage.getLatestFileFromDir(downloadDir);

        FluentWait<File> wait = new FluentWait<>(latestFile)
                .withTimeout(Duration.ofMinutes(1)).pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class).withMessage("File is not downloaded yet");

        try {
            boolean fileDownload = wait.until(f -> f.exists() && f.canRead());

            if (fileDownload) {
                logger.info("File downloaded successfully");

                String extractedText = receiptPage.openFileInNewTabAndVerifyText(driver, latestFile);

                String packageAmount = String.format("$%.2f", packagePrice);
                Assert.assertTrue(extractedText.contains(packageAmount), "The text " + packageAmount + " does not exist in the downloaded PDF file.");

                String totalLicenseAmount = String.format("$%.2f", totalLicensePrice);
                String perUserLicenseAmount = String.format("$%.2f", perUserLicensePrice);
                String userCount = jsonData.getJSONObject("storeInfo").getString("count");

                String licenseInformation = String.format("%s (%s x %s)", totalLicenseAmount, perUserLicenseAmount, userCount);
                Assert.assertTrue(extractedText.contains(licenseInformation), "The text \"" + licenseInformation + "\" does not exist in the downloaded PDF file.");

                String subTotalAmount = String.format("$%.2f", subTotal);
                Assert.assertTrue(extractedText.contains(subTotalAmount), "The text " + subTotalAmount + " does not exist in the downloaded PDF file.");

                String promoAmount = String.format("$%.2f", promoDiscount);
                Assert.assertTrue(extractedText.contains(promoAmount), "The text " + promoAmount + " does not exist in the downloaded PDF file.");

                String recurringAmount = String.format("$%.2f", recurringFee);
                Assert.assertTrue(extractedText.contains(recurringAmount), "The text " + recurringAmount + " does not exist in the downloaded PDF file.");
            }
        }
        catch (Exception e) {
            logger.info("File is not downloaded successfully");
        }

        logger.info("Customer downloaded the receipt and validated the data successfully.");
    }

    @Test(description = "Verify that the customer has received the trial receipt in email", priority = 3)
    public void verifyCustomerReceivedTrialReceipt() throws InterruptedException {
        SmallWait(60000);

        checkTrialReceipt();

        logger.info("Customer successfully received the trial receipt.");
    }

    @Test(description = "Verify that a customer can navigate back to the package tab and check paid subscription data", priority = 4, enabled = true)
    public void verifyCustomerNavigationToPackageTabAndSubscriptionData() throws InterruptedException {
        SmallWait(1500);

        packageSelectionPage.clickPackageTab();

        String packageText = packageSelectionPage.getPackageText();
        String trialStartText = packageSelectionPage.getTrialStartText();
        String trialEndText = packageSelectionPage.getTrialEndText();

        String subscriptionText = packageSelectionPage.getSubscriptionText();

        Matcher packageMatcher = Pattern.compile("Current Package: (.+?) \\(Trial\\)").matcher(packageText);
        Matcher trialStartMatcher = Pattern.compile("Trial Start Date : (\\d{2}/\\d{2}/\\d{4})").matcher(trialStartText);
        Matcher trialEndMatcher = Pattern.compile("Trial End Date: (\\d{2}/\\d{2}/\\d{4})").matcher(trialEndText);

        //Matcher subscriptionMatcher = Pattern.compile("Your current subscription (.+?) will upgrade trial to active from (\\d{2}/\\d{2}/\\d{4}) if you don't cancel your subscription\\.").matcher(subscriptionText);

        //System.out.println(subscriptionMatcher);

        String packageName = jsonData.getJSONObject("packageDetails").getString("package");
        String startDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        String endDate = LocalDate.now().plusDays(13).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        //String recurringDate = LocalDate.now().plusDays(14).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        if (packageMatcher.find()) {
            Assert.assertEquals(packageName, packageMatcher.group(1), "Package name mismatch; should be: " +packageName+ " but displayed: " +packageMatcher.group(1));
        }
        else{ Assert.fail("Package text not found"); }

        if (trialStartMatcher.find()) {
            Assert.assertEquals(startDate, trialStartMatcher.group(1), "Trial Start date mismatch; should be: " +startDate+ " but displayed: " +trialStartMatcher.group(1));
        }
        else{ Assert.fail("Trial start date not found"); }

        if (trialEndMatcher.find()) {
            Assert.assertEquals(endDate, trialEndMatcher.group(1), "Trial End date mismatch; should be: " +endDate+ " but displayed: " +trialEndMatcher.group(1));
        }
        else{ Assert.fail("Trial end date not found"); }

//        if (subscriptionMatcher.find()) {
//            Assert.assertEquals(packageName, subscriptionMatcher.group(1), "Package name mismatch; should be: " +packageName+ " but displayed: " +subscriptionMatcher.group(1));
//            System.out.println(packageName);
//            System.out.println(subscriptionMatcher.group(1));
//
//            Assert.assertEquals(endDate, subscriptionMatcher.group(2), "Recurring start date mismatch; should be: " +endDate+ " but displayed: " +subscriptionMatcher.group(2));
//            System.out.println(endDate);
//            System.out.println(subscriptionMatcher.group(2));
//        }
//        if (subscriptionMatcher.find()) {
//            String subscriptionType = subscriptionMatcher.group(1);
//            String date = subscriptionMatcher.group(2);
//
//            System.out.println("Subscription Type: " + subscriptionType);
//            System.out.println("Date: " + date);
//        }
//        else{
//            Assert.fail("Subscription text not found");
//        }

        packageSelectionPage.paidStartSubscriptionButtonVisibility();
        packageSelectionPage.packageChangeButtonVisibility();
        packageSelectionPage.packageCancelButtonVisibility();

        logger.info("Customer navigated back to the package page and verified the subscription data.");
    }

    public void checkTrialReceipt() {
        String apiKey = jsonData.getJSONObject("mailosaur").getString("apiKey");
        String serverId = jsonData.getJSONObject("mailosaur").getString("serverId");
        String emailAddress = getEmail();
        String expectedCustomerName = getFirstName() +" "+ getLastName();
//        String emailAddress = "margherita@qjav2ant.mailosaur.net";
//        String expectedCustomerName = "Margherita Prosacco";
        String expectedSubject = jsonData.getJSONObject("registration_info").getString("trialReceiptEmailSubject");

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

                logger.info("Customer Name: {}", customerName);

                if (expectedCustomerName.equals(customerName)) {
                    logger.info("Customer name matches.");
                }
                else {
                    logger.info("Customer name does not match.");
                }
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