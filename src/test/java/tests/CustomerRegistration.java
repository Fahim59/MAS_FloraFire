package tests;

import base.BaseClass;
import constants.EndPoint;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

public class CustomerRegistration extends BaseClass {
    private Registration_Page registrationPage;
    static String link;

    @BeforeMethod
    public void initializePageObjects() {
        registrationPage = new Registration_Page(driver);
        userName = getEmail();
    }
    //-------------------------------------------------------//

    @Test(description = "Verify that a customer can navigate to the registration page successfully", priority = 1)
    public void verifyCustomerNavigationToRegistrationPage() {
        Open_Website(EndPoint.registration.url);

        logger.info("Customer navigated to the registration page.");
    }

    @Test(description = "Verify the registration form submission flow for a new customer", priority = 2)
    public void verifyNewCustomerRegistrationSubmissionFlow() throws InterruptedException {
        customerName = getFirstName() +" "+ getLastName();

        registrationPage.enterRegistrationDetails(getFirstName(), getLastName(), getEmail(), getEmail(),
                jsonData.getJSONObject("registration_info").getString("sec.question1"), jsonData.getJSONObject("registration_info").getString("sec.question1_answer"),
                jsonData.getJSONObject("registration_info").getString("sec.question2"), jsonData.getJSONObject("registration_info").getString("sec.question2_answer"),
                jsonData.getJSONObject("registration_info").getString("password"), jsonData.getJSONObject("registration_info").getString("password"));

        logger.info("Customer entered registration details in the registration form.");

        SmallWait(1000);

        registrationPage.clickRegisterBtn();
        logger.info("Customer clicked on the register button to submit the form.");
    }

    @Test(description = "Verify that a customer can complete the registration, see a success message, and navigate to the login page", priority = 3)
    public void verifyCustomerRegistrationAndLoginNavigation() throws InterruptedException {
        SmallWait(1000);

        Assert.assertEquals(jsonData.getJSONObject("registration_info").getString("regSuccessText"), registrationPage.fetchRegSuccessText());

        Assert.assertEquals(jsonData.getJSONObject("registration_info").getString("emailText") + "\n" + getEmail(),
                registrationPage.fetchEmailText());

        logger.info("Registration message verification successful.");

        registrationPage.clickLoginPageBtn();

        logger.info("Customer navigated to the login page.");
    }

    @Test(description = "Verify that a customer can check their email and successfully click on the activation link", priority = 4)
    public void verifyCustomerEmailActivation() throws InterruptedException {
        SmallWait(60000);

        getActivationLink();

        //driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(link);

        Assert.assertEquals(jsonData.getJSONObject("registration_info").getString("activationSuccessText"), registrationPage.fetchActivationText());

        logger.info("Customer checked email and clicked on the activation link.");
    }

    public void getActivationLink() {
        String apiKey = jsonData.getJSONObject("mailosaur").getString("apiKey");
        String serverId = jsonData.getJSONObject("mailosaur").getString("serverId");
        String emailAddress = getEmail();
        String expectedCustomerName = getFirstName() +" "+ getLastName();
        String expectedSubject = jsonData.getJSONObject("registration_info").getString("emailSubject");

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

            if (!expectedSubject.equals(emailSubject)) {
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
                    kong.unirest.json.JSONArray linksArray = messageContent.getJSONObject("html").getJSONArray("links");

                    if (!linksArray.isEmpty()) {
                        String confirmationLink = linksArray.getJSONObject(0).getString("href");
                        link = confirmationLink;

                        logger.info("Confirmation link: {}", confirmationLink);
                    }
                    else { logger.info("No links found in the email."); }
                }
                else { logger.info("Customer name does not match."); }
            }
            else { logger.info("No recipient details found."); }
        }
        else { logger.info("No emails found."); }
    }
}