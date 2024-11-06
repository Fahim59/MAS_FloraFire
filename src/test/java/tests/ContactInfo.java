package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class ContactInfo extends BaseClass {

    private static final Logger logger = LogManager.getLogger(ContactInfo.class);

    private ContactInfo_Page contactInfoPage;

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
        contactInfoPage = new ContactInfo_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verifies that a customer can enter all required basic information", priority = 1)
    public void verifyCustomerBasicInfoEntry() throws InterruptedException {
        SmallWait(1500);

        String country = "United States of America";
        String zip = "94105";
        String answer = "I heard about you from the newspaper";

        contactInfoPage.enterContactInfoDetails(getCompany(), getAddress(), getAddressCont(), country, getState(),
                getCity(), zip, getPhone(), getPhone(), getPhone(), "Newspaper", answer);

        //contactInfoPage.clickSaveBtn();

        logger.info("Customer entered all their basic information.");
    }

    @Test(description = "Verifies that after clicking the save button, the customer is successfully navigated to Store Info page", priority = 2, enabled = false)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("storeInfo"));

        logger.info("Customer clicked the save button and verified navigation to the Store Info page");
    }
}