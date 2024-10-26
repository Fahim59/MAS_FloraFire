package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.*;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class CustomerRegistration extends BaseClass {

    private static final Logger logger = LogManager.getLogger(CustomerRegistration.class);

    private HomePage homePage;

    FileReader data;
    JSONObject products;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            products = new JSONObject(tokener);
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
        homePage = new HomePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Go to the registration page", priority = 1)
    public void got_to_registration_page() {
        logger.info("Customer go to the FLorafire Registration page");
    }
}