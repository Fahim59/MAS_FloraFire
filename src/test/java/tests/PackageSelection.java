package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import pages.*;

import java.io.FileReader;

public class PackageSelection extends BaseClass {

    private static final Logger logger = LogManager.getLogger(PackageSelection.class);

    private PackageSelection_Page packageSelectionPage;

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
        packageSelectionPage = new PackageSelection_Page(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify that a customer can select preferred package successfully", priority = 1)
    public void verifyCustomerPreferredPackageSelection() throws InterruptedException {
        String packageName = jsonData.getJSONObject("packageDetails").getString("package");

        packagePrice = packageSelectionPage.selectPackageAndGetValue(packageName);

        packageSelectionPage.clickSaveBtn();

        logger.info("Customer selected the preferred package and clicked on save button.");
    }

    @Test(description = "Verify that after clicking the save button, the customer is successfully navigated to Contact Info page", priority = 2)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("contactInfo"));

        logger.info("Customer successfully navigated to the Contact Info page");
    }
}