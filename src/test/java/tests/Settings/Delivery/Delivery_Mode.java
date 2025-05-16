package tests.Settings.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Settings.Delivery.DeliveryMode_Page;
import pages.Home_Page;

import java.util.Map;

public class Delivery_Mode extends BaseClass {
    private Home_Page homePage;
    private DeliveryMode_Page deliveryModePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        deliveryModePage = new DeliveryMode_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Delivery Mode Page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickDeliveryModesMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("deliveryMode"));

        logger.info("User successfully navigated to the Delivery Mode page");
    }

    public static String[] modeData(Map<String, String> valueData) {
        String name = valueData.get("Mode Name");
        String slot1 = valueData.get("Slot1");
        String zone1 = valueData.get("Zone1");
        String zone2 = valueData.get("Zone2");
        String hour = valueData.get("Hour");
        String minute = valueData.get("Minute");
        String amOrPm = valueData.get("AM/PM");

        return new String[] {name, slot1, hour, minute, amOrPm, zone1, zone2};
    }

    @Test(description = "Verify that the user can add Delivery Mode data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Mode")
    public void verifyModeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] modeInfo = modeData(data);

        deliveryModePage.clickNewModeButton();

        deliveryModePage.enterDeliveryModeData(modeInfo[0]);

        deliveryModePage.enterDeliverySlotData(modeInfo[1]);

        deliveryModePage.enterSlotDetails(modeInfo[1], modeInfo[2], modeInfo[3], modeInfo[4], modeInfo[5], modeInfo[6]);

        deliveryModePage.clickFinalSaveButton();

        logger.info("Successfully added Delivery Mode - {}", modeInfo[0]);
    }
}