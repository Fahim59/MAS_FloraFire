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

public class CustomerLogin extends BaseClass {

    private static final Logger logger = LogManager.getLogger(CustomerLogin.class);

    private LoginPage loginPage;

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
        loginPage = new LoginPage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verifies that a customer can log in successfully", priority = 1)
    public void verifyCustomerSuccessfulLogin() {
        Open_Website(EndPoint.login.url);

        //loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));
        loginPage.enterLoginDetails("janopin764@regishub.com", jsonData.getJSONObject("registration_info").getString("password"));
        loginPage.clickLoginBtn();

        logger.info("Customer logged in successfully.");
    }
}