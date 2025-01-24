package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.StoreSettings_Page;

import java.util.Map;

public class Store_Settings extends BaseClass {
    private Home_Page homePage;
    StoreSettings_Page storeSettingsPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        storeSettingsPage = new StoreSettings_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Store page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickStoreSettingsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("stores"));

        logger.info("User successfully navigated to the Store page");
    }

    public static String[] storeSettingsData() {

        String manager = "Mark";
        String tax = "5";
        String trackInventory = "Yes";
        String timeFormat = "12";
        String dateFormat = "MM/DD/YYYY";

        String fbUrl = "https://www.facebook.com/floralstore";
        String twitterUrl = "https://www.twitter.com/floralstore";
        String pinterestUrl = "https://www.pinterest.com/floralstore";

        String fileName = "shop.jpg";

        return new String[] {manager, tax, trackInventory, timeFormat, dateFormat, fbUrl, twitterUrl, pinterestUrl, fileName};
    }

    @Test(description = "Verify that the user can add Store Settings data successfully", priority = 2)
    public void verifyStoreSettingsDataEntry() throws InterruptedException {
        String[] storeSettingsInfo = storeSettingsData();

        storeSettingsPage.enterStoreDetails(storeSettingsInfo[0], storeSettingsInfo[1], storeSettingsInfo[2], storeSettingsInfo[3],
                storeSettingsInfo[4], storeSettingsInfo[5], storeSettingsInfo[6], storeSettingsInfo[7], storeSettingsInfo[8]);

        logger.info("Successfully added data - {}", storeSettingsInfo[0]);
    }
}