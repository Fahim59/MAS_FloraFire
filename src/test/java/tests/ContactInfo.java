package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class ContactInfo extends BaseClass {

    private ContactInfo_Page contactInfoPage;

    @BeforeMethod
    public void initializePageObjects() {
        contactInfoPage = new ContactInfo_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify that a customer can enter all required basic information", priority = 1)
    public void verifyCustomerBasicInfoEntry() throws InterruptedException {
        SmallWait(1000);

        String country = jsonData.getJSONObject("contactInfo").getString("country");
        String zip = jsonData.getJSONObject("contactInfo").getString("zip");
        String businessPhone = jsonData.getJSONObject("contactInfo").getString("businessPhone");
        String mobile = jsonData.getJSONObject("contactInfo").getString("mobile");
        String additionalPhone = jsonData.getJSONObject("contactInfo").getString("additionalPhone");
        String aboutUs = jsonData.getJSONObject("contactInfo").getString("aboutUs");
        String answer = jsonData.getJSONObject("contactInfo").getString("answer");

        contactInfoPage.enterContactInfoDetails(getCompany(), getAddress(), getAddressCont(), country, getState(),
                getCity(), zip, businessPhone, mobile, additionalPhone, aboutUs, answer);

        logger.info("Customer entered all the basic information and clicked on save button.");
    }

    @Test(description = "Verify that after clicking the save button, the customer is successfully navigated to Store Info page", priority = 2)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("storeInfo"));

        logger.info("Customer successfully navigated to the Store Info page");
    }
}