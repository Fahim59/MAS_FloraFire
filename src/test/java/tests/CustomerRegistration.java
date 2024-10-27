package tests;

import base.BaseClass;
import com.github.javafaker.Faker;
import constants.EndPoint;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class CustomerRegistration extends BaseClass {

    private static final Logger logger = LogManager.getLogger(CustomerRegistration.class);

    Faker faker = new Faker();

    private RegistrationPage registrationPage;
    static String firstName, lastName, email, link;

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
        registrationPage = new RegistrationPage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verifies that a customer can navigate to the registration page successfully", priority = 1)
    public void verifyCustomerNavigationToRegistrationPage() {
        Open_Website(EndPoint.registration.url);

        logger.info("Customer navigated to the registration page.");
    }

    @Test(description = "Verifies the registration form submission flow for a new customer", priority = 2)
    public void verifyNewCustomerRegistrationSubmissionFlow() throws InterruptedException {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = firstName.toLowerCase() + "@" + jsonData.getJSONObject("mailosaur").getString("serverId") + ".mailosaur.net";

        registrationPage.enterRegistrationDetails(firstName, lastName, email, email,
                jsonData.getJSONObject("registration_info").getString("sec.question1"), jsonData.getJSONObject("registration_info").getString("sec.question1_answer"),
                jsonData.getJSONObject("registration_info").getString("sec.question2"), jsonData.getJSONObject("registration_info").getString("sec.question2_answer"),
                jsonData.getJSONObject("registration_info").getString("password"), jsonData.getJSONObject("registration_info").getString("password"));

        logger.info("Entered customer details in the registration form.");

        SmallWait(1000);

        registrationPage.clickRegisterBtn();
        logger.info("Clicked on the register button to submit the form.");
    }

    @Test(description = "Verifies that a customer can complete the registration, see a success message, and navigate to the login page", priority = 3)
    public void verifyCustomerRegistrationAndLoginNavigation() throws InterruptedException {
        SmallWait(1000);

        Assert.assertEquals(jsonData.getJSONObject("registration_info").getString("regSuccessText"), registrationPage.fetchRegSuccessText());

        Assert.assertEquals(jsonData.getJSONObject("registration_info").getString("emailText") + "\n" + email,
                registrationPage.fetchEmailText());

        logger.info("Verified successful registration message.");

        registrationPage.clickLoginPageBtn();
        logger.info("Navigated to the login page.");
    }

    @Test(description = "Verifies that a customer can check their email and successfully click on the activation link", priority = 4)
    public void verifyCustomerEmailActivation() throws InterruptedException {
        SmallWait(60000);

        getActivationLink();

        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(link);

        SmallWait(500);
        Assert.assertEquals(jsonData.getJSONObject("registration_info").getString("activationSuccessText"), registrationPage.fetchActivationText());

        logger.info("Customer checked their email and clicked on the activation link.");
    }

    public void getActivationLink() {
        String apiKey = jsonData.getJSONObject("mailosaur").getString("apiKey");
        String serverId = jsonData.getJSONObject("mailosaur").getString("serverId");
        String emailAddress = email;
        String expectedCustomerName = firstName +" "+ lastName;

        HttpResponse<JsonNode> response = Unirest.get("https://mailosaur.com/api/messages")
                .basicAuth(apiKey, "")
                .queryString("server", serverId)
                .queryString("sentTo", emailAddress)
                .asJson();

        if (!response.getBody().getObject().getJSONArray("items").isEmpty()) {
            kong.unirest.json.JSONObject latestEmail = response.getBody().getObject().getJSONArray("items").getJSONObject(0);
            String messageId = latestEmail.getString("id");

            HttpResponse<JsonNode> messageResponse = Unirest.get("https://mailosaur.com/api/messages/" + messageId)
                    .basicAuth(apiKey, "")
                    .asJson();

            kong.unirest.json.JSONObject messageContent = messageResponse.getBody().getObject();

            kong.unirest.json.JSONArray toArray = messageContent.getJSONArray("to");

            if (!toArray.isEmpty()) {
                String customerName = toArray.getJSONObject(0).getString("name");

                System.out.println("Customer Name: " +customerName);

                if (expectedCustomerName.equals(customerName)) {

                    kong.unirest.json.JSONArray linksArray = messageContent.getJSONObject("html").getJSONArray("links");

                    if (!linksArray.isEmpty()) {
                        String confirmationLink = linksArray.getJSONObject(0).getString("href");
                        link = confirmationLink;
                        System.out.println("Confirmation link: " + confirmationLink);
                    }
                    else { System.out.println("No links found in the email."); }
                }
                else { System.out.println("Customer name does not match."); }
            }
            else { System.out.println("No recipient details found."); }
        }
        else { System.out.println("No emails found."); }
    }
}