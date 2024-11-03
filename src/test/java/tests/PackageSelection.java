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

    @Test(description = "Verifies that a customer can select their preferred package successfully", priority = 1)
    public void verifyCustomerPreferredPackageSelection() throws InterruptedException {
        String packageName = jsonData.getJSONObject("packageDetails").getString("package");
        packageSelectionPage.selectPackage(packageName);

        packageSelectionPage.clickSaveBtn();

        logger.info("Customer selected their preferred package.");
    }

    @Test(description = "Verifies that after clicking the save button, the customer is successfully navigated to Contact Info page", priority = 2)
    public void verifyCustomerNavigationAfterSaving() throws InterruptedException {
        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("contactInfo"));

        logger.info("Customer clicked the save button and verified navigation to the Contact Info page");
    }
}