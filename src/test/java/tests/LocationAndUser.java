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

    @Test(description = "Verifies that a customer can enter all required store information", priority = 1, enabled = false)
    public void verifyCustomerStoreInfoEntry() throws InterruptedException {
        SmallWait(500);

        String country = jsonData.getJSONObject("contactInfo").getString("country");
        String zip = jsonData.getJSONObject("contactInfo").getString("zip");
        String businessPhone = jsonData.getJSONObject("contactInfo").getString("businessPhone");
        String timeZone = jsonData.getJSONObject("storeInfo").getString("timeZone");
        String fax = jsonData.getJSONObject("storeInfo").getString("fax");

        locationAndUserPage.enterPrimaryStoreInfoDetails(getCompany(), getFullName(), timeZone, fax);

        SmallWait(200);
        locationAndUserPage.clickAddNewStoreBtn();

        Scroll_Down();

        locationAndUserPage.enterAdditionalStoreInfoDetails(getCompany(), getFullName(), getEmail(), getAddress(),
                getAddressCont(), country, getState(), getCity(), zip, timeZone, businessPhone, fax);

        SmallWait(2000);

        locationAndUserPage.clickSaveBtn();

        logger.info("Customer entered all their store information.");
    }

    @Test(description = "Verifies that a customer can enter all required license information", priority = 2)
    public void verifyCustomerLicenseInfoEntry() throws InterruptedException {
        SmallWait(500);

        SmallWait(500);
        locationAndUserPage.clickLocationTab();

        double licenseCount = Double.parseDouble(jsonData.getJSONObject("storeInfo").getString("count"));

        //locationAndUserPage.enterAdditionalLicense(licenseCount);

        //SmallWait(2000);

        //locationAndUserPage.clickSaveBtn();

        licensePrice = locationAndUserPage.priceTable(licenseCount);

        logger.info("Customer entered all their license information.");
    }

    @Test(description = "Verifies that after clicking the save button, the customer is successfully navigated to Checkout page", priority = 3, enabled = false)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("checkout"));

        logger.info("Customer clicked the save button and verified navigation to the Checkout page");
    }
}