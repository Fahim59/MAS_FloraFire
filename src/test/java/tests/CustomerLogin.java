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

    private Login_Page loginPage;
    private LocationAndUser_Page locationAndUserPage;

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
        loginPage = new Login_Page(driver);
        locationAndUserPage = new LocationAndUser_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verifies that a customer can log in successfully", priority = 1)
    public void verifyCustomerSuccessfulLogin() throws InterruptedException {
        Open_Website(EndPoint.login.url);

        loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));

        //loginPage.enterLoginDetails("testmustafizur+14@gmail.com", jsonData.getJSONObject("registration_info").getString("password"));

        loginPage.clickLoginBtn();

        SmallWait(500);
        locationAndUserPage.clickLocationAndUserTab();

        logger.info("Customer logged in successfully.");
    }

    @Test(description = "Verifies that after successful login, the customer is successfully navigated to Package Selection page", priority = 2, enabled = false)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("packageSelection"));

        logger.info("Customer clicked the login button and verified navigation to the Package Selection page");
    }
}