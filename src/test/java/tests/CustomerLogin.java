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
        monthTotalDays = (int) monthDays[0];
        monthUsedDays = (int) monthDays[1];

        licenseCount = Integer.parseInt(jsonData.getJSONObject("storeInfo").getString("count"));
    }

    @Test(description = "Verify that a customer can log in successfully", priority = 1)
    public void verifyCustomerSuccessfulLogin() throws InterruptedException {
        Open_Website(EndPoint.login.url);

        if (userName != null && !userName.isEmpty()) {
            loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));
        }
        else {
            userName = "shavonne@qjav2ant.mailosaur.net";
            customerName = "Shavonne Botsford";

            packagePrice = 10.0;
            perUserLicensePrice = 10.0;
            totalLicensePrice = 80.0;
            subTotal = 94.0;
            promoDiscount = 10.34;
            recurringFee = 83.66;

            loginPage.enterLoginDetails(userName, jsonData.getJSONObject("registration_info").getString("password"));

//            SmallWait(500);
//            locationAndUserPage.clickLocationAndUserTab();
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