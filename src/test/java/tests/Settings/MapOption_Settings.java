package tests.Settings;

import base.BaseClass;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.MapOptionSettings_Page;

import java.util.Map;

public class MapOption_Settings extends BaseClass {
    private Home_Page homePage;
    MapOptionSettings_Page mapOptionSettingsPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        mapOptionSettingsPage = new MapOptionSettings_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Map Option Settings page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickMapOptionMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("mapSettings"));

        logger.info("User successfully navigated to the Map Option Settings page");
    }

    @Test(description = "Verify that the user can add Map Option Settings data successfully", priority = 2)
    public void verifyMapOptionSettingsDataEntry() {

        String key = "7rxmANLpTlwNV5DLJYfPamojRZ4OJvnC";

        mapOptionSettingsPage.enterAPIKeySettings(key);

        logger.info("Successfully added API key data");
    }
}