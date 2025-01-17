package tests.Settings.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.Delivery.ShortCode_Page;

import java.util.Map;

public class Short_Code extends BaseClass {
    private Home_Page homePage;
    private ShortCode_Page shortCodePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        shortCodePage = new ShortCode_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Short Code page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickShortCodeMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("shortCode"));

        logger.info("User successfully navigated to the Delivery Short Code page");
    }

    public static String[] shortCodeData(Map<String, String> valueData) {
        /*
         * data[0] = flag
         * data[1] = id
         * data[2] = name
         * data[3] = address
         * data[4] = address cont
         * data[5] = country
         */

        String flag = valueData.get("Flag");
        String id = valueData.get("Id");
        String name = valueData.get("Name");
        String address = valueData.get("Address");
        String address_cont = valueData.get("Address Cont.");

        return new String[] {flag, id, name, address, address_cont};
    }

    @Test(description = "Verify that the user can add Short Code data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Test")
    public void verifyShortCodeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] shortCodeInfo = shortCodeData(data);

        //shortCodePage.clickNewCustomerButton();

        logger.info("Successfully added Delivery Short Code - {}", shortCodeInfo[0]);
    }
}