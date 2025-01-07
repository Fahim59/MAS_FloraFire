package tests.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.*;
import pages.Delivery.DeliveryZone_Page;

import java.util.Map;

public class Delivery_Zone extends BaseClass {
    private Home_Page homePage;
    private DeliveryZone_Page deliveryZonePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        deliveryZonePage = new DeliveryZone_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Delivery Zone page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickDeliveryZonesMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("deliveryZone"));

        logger.info("User successfully navigated to the Delivery Zone page");
    }

    public static String[] zoneData(Map<String, String> valueData) {
        /*
         * data[0] = name
         * data[1] = description
         * data[2] = type
         * data[3] = area
         * data[4] = lat1
         * data[5] = long1
         * data[6] = lat2
         * data[7] = long2
         * data[8] = lat3
         * data[9] = long3
         * data[10] = zip
         * data[11] = zoneFee
         * data[12] = withInTwo
         * data[13] = withInThree
         * data[14] = withInThree
         * data[15] = express
         * data[16] = wedding
         * data[17] = futureWithInTwo
         * data[18] = futureWithInThree
         * data[19] = futureWithInFour
         * data[20] = salesTax
         * data[21] = sunday
         */

        String name = valueData.get("Zone Name");
        String description = valueData.get("Description");
        String type = valueData.get("Type");
        String area = valueData.get("Area Selector");
        String lat1 = valueData.get("Lat1");
        String long1 = valueData.get("Long1");
        String lat2 = valueData.get("Lat2");
        String long2 = valueData.get("Long2");
        String lat3 = valueData.get("Lat3");
        String long3 = valueData.get("Long3");
        String zip = valueData.get("Zip Code");
        String zoneFee = valueData.get("Zone Fee");
        String withInTwo = valueData.get("WithinTwo");
        String withInThree = valueData.get("WithinThree");
        String withInFour = valueData.get("WithinFour");
        String express = valueData.get("Express");
        String wedding = valueData.get("Wedding");
        String futureWithInTwo = valueData.get("FutureWithinTwo");
        String futureWithInThree = valueData.get("FutureWithinThree");
        String futureWithInFour = valueData.get("FutureWithinFour");
        String salesTax = valueData.get("Sales Tax");
        String sunday = valueData.get("Sunday");

        return new String[] {name, description, type, area, lat1, long1, lat2, long2, lat3, long3, zip, zoneFee, withInTwo, withInThree,
                withInFour, express, wedding, futureWithInTwo, futureWithInThree, futureWithInFour, salesTax, sunday};
    }

    @Test(description = "Verify that the user can add Delivery Zone data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Zone")
    public void verifyDeliveryZoneDataEntry(Map<String, String> data) throws InterruptedException {
        String[] zoneInfo = zoneData(data);

        String flag = "Yes";
        String storeName = homePage.getStoreName();

        deliveryZonePage.clickNewZoneButton();

        SmallWait(1500);

        if(zoneInfo[0].equalsIgnoreCase("Coordinates")){
            deliveryZonePage.createZoneWithCoordinate(zoneInfo[0], zoneInfo[1], zoneInfo[2], zoneInfo[3],
                    zoneInfo[4], zoneInfo[5], zoneInfo[6], zoneInfo[7], zoneInfo[8], zoneInfo[9], flag, storeName);

            Scroll(0, 500);
        }
        else if(zoneInfo[0].equalsIgnoreCase("Zip")){
            deliveryZonePage.createZoneWithZipCode(zoneInfo[0], zoneInfo[1], zoneInfo[2], zoneInfo[3],
                    zoneInfo[10], flag, storeName);

            Scroll(0, 300);
        }

        deliveryZonePage.enterDeliveryPrice(zoneInfo[11], zoneInfo[12], zoneInfo[13], zoneInfo[14], zoneInfo[15], zoneInfo[16],
                zoneInfo[17], zoneInfo[18], zoneInfo[19], zoneInfo[20], zoneInfo[21]);

        deliveryZonePage.clickSaveButton();

        logger.info("Successfully added Delivery Zone using - {}", zoneInfo[0]);
    }
}