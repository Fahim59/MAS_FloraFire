package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class LocationAndUser extends BaseClass {

    private static final Logger logger = LogManager.getLogger(LocationAndUser.class);

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
        locationAndUserPage = new LocationAndUser_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify that a customer can enter all required store information", priority = 1)
    public void verifyCustomerStoreInfoEntry() throws InterruptedException {
        SmallWait(1000);

        String country = jsonData.getJSONObject("contactInfo").getString("country");
        String zip = jsonData.getJSONObject("contactInfo").getString("zip");
        String businessPhone = jsonData.getJSONObject("contactInfo").getString("businessPhone");
        String timeZone = jsonData.getJSONObject("storeInfo").getString("timeZone");
        String fax = jsonData.getJSONObject("storeInfo").getString("fax");

        locationAndUserPage.enterPrimaryStoreInfoDetails(getCompany(), getFullName(), timeZone, fax);

        SmallWait(500);
        locationAndUserPage.clickAddNewStoreBtn();

        Scroll_Down();

        locationAndUserPage.enterAdditionalStoreInfoDetails(getCompany(), getFullName(), getEmail(), getAddress(),
                getAddressCont(), country, getState(), getCity(), zip, timeZone, businessPhone, fax);

        SmallWait(1500);

        locationAndUserPage.clickSaveBtn();

        logger.info("Customer entered all the required store information and clicked on save button.");
    }

    @Test(description = "Verify that a customer can enter all required license information", priority = 2)
    public void verifyCustomerLicenseInfoEntry() throws InterruptedException {
        SmallWait(1500);

        //locationAndUserPage.clickLocationTab();

        String licenseCount = jsonData.getJSONObject("storeInfo").getString("count");
        locationAndUserPage.enterAdditionalLicense(Integer.parseInt(licenseCount));

        Object[] priceTable = locationAndUserPage.priceTable(Integer.parseInt(licenseCount));
        totalLicensePrice = (double) priceTable[0];
        perUserLicensePrice = (double) priceTable[1];

        SmallWait(1500);

        locationAndUserPage.clickSaveBtn();

        logger.info("Customer entered the required license count and clicked on save button.");
    }

    @Test(description = "Verify that after clicking the save button, the customer is successfully navigated to Checkout page", priority = 3)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("checkout"));

        logger.info("Customer successfully navigated to the Checkout page");
    }
}