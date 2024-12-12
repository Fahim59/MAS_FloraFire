package tests;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

public class PackageSelection extends BaseClass {
    private PackageSelection_Page packageSelectionPage;

    @BeforeMethod
    public void initializePageObjects() {
        packageSelectionPage = new PackageSelection_Page(driver);
    }

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