package tests;

import base.BaseClass;
import com.github.javafaker.Faker;
import constants.EndPoint;
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

    FileReader data;
    JSONObject details;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            details = new JSONObject(tokener);
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
        registrationPage.enterRegistrationDetails(faker.name().firstName(), faker.name().lastName(), details.getJSONObject("registration_info").getString("email"), details.getJSONObject("registration_info").getString("email"),
                details.getJSONObject("registration_info").getString("sec.question1"), details.getJSONObject("registration_info").getString("sec.question1_answer"),
                details.getJSONObject("registration_info").getString("sec.question2"), details.getJSONObject("registration_info").getString("sec.question2_answer"),
                details.getJSONObject("registration_info").getString("password"), details.getJSONObject("registration_info").getString("password"));

        logger.info("Entered customer details in the registration form.");

        SmallWait(1000);

        registrationPage.clickRegisterBtn();
        logger.info("Clicked on the register button to submit the form.");
    }

    @Test(description = "Verifies that a customer can complete the registration, see a success message, and navigate to the login page", priority = 3)
    public void verifyCustomerRegistrationAndLoginNavigation() throws InterruptedException {
        SmallWait(1000);

        Assert.assertEquals(details.getJSONObject("registration_info").getString("regSuccessText"), registrationPage.fetchRegSuccessTextField());

        Assert.assertEquals(details.getJSONObject("registration_info").getString("emailText") + "\n" + details.getJSONObject("registration_info").getString("email"),
                registrationPage.fetchEmailTextField());

        logger.info("Verified successful registration message.");

        registrationPage.clickLoginPageBtn();
        logger.info("Navigated to the login page.");
    }
}