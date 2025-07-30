package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import pages.Settings.*;

import java.util.Map;

public class Vehicle extends BaseClass {
    private Home_Page homePage;
    private Vehicle_Page vehiclePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        vehiclePage = new Vehicle_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Vehicle page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickVehiclesMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("vehicle"));

        logger.info("User clicked on the Vehicle menu and successfully navigated to the Vehicle list page");
    }

    public static String[] vehicleData(Map<String, String> valueData) {

        String id = valueData.get("Id");
        String license = valueData.get("License");
        String name = valueData.get("Name");
        String vin = valueData.get("VIN");
        String mileage = valueData.get("Mileage");
        String model = valueData.get("Model");
        String status = valueData.get("Status");
        String expDate = valueData.get("Expiration Date");
        String mainDate = valueData.get("Maintenance Due");

        return new String[] {id, license, name, vin, mileage, model, status, expDate, mainDate};
    }

    @Test(description = "Verify that the user can add vehicle data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Vehicle")
    public void verifyVehicleDataEntry(Map<String, String> data) throws InterruptedException {
        String[] vehicleInfo = vehicleData(data);

        String message = jsonData.getJSONObject("successMessage").getString("vehicle");

        vehiclePage.clickNewVehicleButton();

        vehiclePage.enterVehicleInformation(vehicleInfo[0], vehicleInfo[1], vehicleInfo[2], vehicleInfo[3], vehicleInfo[4], vehicleInfo[5],
                vehicleInfo[6], vehicleInfo[7], vehicleInfo[8]);

        SmallWait(1000);

        Assert.assertEquals(message, vehiclePage.getSuccessMessage());

        vehiclePage.verifyVehicleAddition(vehicleInfo[0]);

        logger.info("Vehicle {} added successfully", vehicleInfo[0]);
    }
}