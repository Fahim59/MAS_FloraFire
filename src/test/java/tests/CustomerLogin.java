package tests;

import base.BaseClass;
import constants.EndPoint;
import org.testng.annotations.*;
import pages.*;

public class CustomerLogin extends BaseClass {
    private Login_Page loginPage;

    @BeforeMethod
    public void initializePageObjects() {
        loginPage = new Login_Page(driver);

        Object[] monthDays = monthDays();
        monthTotalDays = (int) monthDays[0];                    //for package and additional license
        monthUsedDays = (int) monthDays[1];                    //for package and additional license

        licenseCount = Integer.parseInt(jsonData.getJSONObject("storeInfo").getString("licenseCount"));

        promoDiscount = 15;
    }

    @Test(description = "Verify that a customer can log in successfully", priority = 1)
    public void verifyCustomerSuccessfulLogin() {
        Open_Website(EndPoint.login.url);

        if (userName != null && !userName.isEmpty()) {
            loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));
        }
        else {
            userName = "testmustafizur+102@gmail.com";
            loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));
        }

        logger.info("Customer logged in successfully.");
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Package Selection page", priority = 2)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("packageSelection"));

        logger.info("Customer successfully navigated to the Package Selection page");
    }
}