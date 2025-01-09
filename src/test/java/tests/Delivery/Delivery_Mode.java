package tests.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Delivery.DeliveryMode_Page;
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
        String slot2 = valueData.get("Slot2");

        return new String[] {name, slot1, slot2};
    }

    @Test(description = "Verify that the user can add Delivery Mode data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Mode")
    public void verifyModeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] modeInfo = modeData(data);

        deliveryModePage.clickNewModeButton();

        deliveryModePage.enterDeliveryModeDetails(modeInfo[0], modeInfo[1], modeInfo[2]);

        deliveryModePage.clickFinalSaveButton();

        logger.info("Successfully added Delivery Mode - {}", modeInfo[0]);
    }
}