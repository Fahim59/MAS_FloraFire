package tests;

import base.BaseClass;
import constants.EndPoint;
import org.testng.annotations.*;
import pages.*;

public class CustomerLogin extends BaseClass {
    private Login_Page loginPage;
    private Payment_Page paymentPage;
    private LocationAndUser_Page locationAndUserPage;

    @BeforeMethod
    public void initializePageObjects() {
        loginPage = new Login_Page(driver);
        paymentPage = new Payment_Page(driver);
        locationAndUserPage = new LocationAndUser_Page(driver);

        Object[] monthDays = monthDays();
        monthTotalDays = (int) monthDays[0];                    //for package and additional license
        monthUsedDays = (int) monthDays[1];                    //for package and additional license

        licenseCount = Integer.parseInt(jsonData.getJSONObject("storeInfo").getString("licenseCount"));
    }

    @Test(description = "Verify that a customer can log in successfully", priority = 1)
    public void verifyCustomerSuccessfulLogin() throws InterruptedException {
        Open_Website(EndPoint.login.url);

        if (userName != null && !userName.isEmpty()) {
            loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));
        }
        else {
            userName = "mr@qca6z4pm.mailosaur.net";
            //customerName = "Valorie Denesik";

            loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));

//            SmallWait(500);
//            locationAndUserPage.clickLocationTab();
//            paymentPage.clickPaymentTab();
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