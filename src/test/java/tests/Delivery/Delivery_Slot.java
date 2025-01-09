package tests.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Delivery.*;
import pages.*;

import java.util.Map;

public class Delivery_Slot extends BaseClass {
    private Home_Page homePage;
    private DeliverySlot_Page deliverySlotPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        deliverySlotPage = new DeliverySlot_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Delivery Slot Page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickDeliverySlotMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("deliverySlot"));

        logger.info("User successfully navigated to the Delivery Slot page");
    }

    public static String[] slotData(Map<String, String> valueData) {

        String name = valueData.get("Slot Name");
        String zone1 = valueData.get("Zone1");
        String zone2 = valueData.get("Zone2");
        String hour = valueData.get("Hour");
        String minute = valueData.get("Minute");
        String amOrPm = valueData.get("AM/PM");

        return new String[] {name, zone1, zone2, hour, minute, amOrPm};
    }

    @Test(description = "Verify that the user can add Delivery Slot data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Slot")
    public void verifySlotDataEntry(Map<String, String> data) throws InterruptedException {
        String[] slotInfo = slotData(data);

        deliverySlotPage.clickNewSlotButton();

        deliverySlotPage.enterDeliverySlotDetails(slotInfo[0], slotInfo[3], slotInfo[4], slotInfo[5], slotInfo[1], slotInfo[2]);

        deliverySlotPage.clickFinalSaveButton();

        logger.info("Successfully added data - {}", slotInfo[0]);
    }
}