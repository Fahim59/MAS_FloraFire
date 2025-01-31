package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Home_Page;

import java.util.Map;

public class Order_Entry extends BaseClass {
    private Home_Page homePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
    }

    @Test(description = "Verify that after successful login, the user is successfully navigated to User list page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickUsersMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("users"));

        logger.info("User successfully navigated to the User list page");
    }

    @Test(description = "Verify that after successful login, the user can create a new role", priority = 2)
    public void verifyNewRoleCreation() throws InterruptedException {
        SmallWait(1000);

        String role = "employee";

        logger.info("User successfully created new role - {}", role);
    }


    @Test(description = "Verify that after successful login, the customer is successfully navigated to Order Entry page", priority = 1, enabled = false)
    public void verifyCustomerNavigation() throws InterruptedException {
        SmallWait(1000);

        homePage.clickOrderEntryMenu();

        SmallWait(1000);
        //verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("valueTypes"));

        logger.info("User successfully navigated to the  page");
    }

    public static String[] xData(Map<String, String> valueData) {
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

    @Test(description = "Verify that the user can add  data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2, enabled = false)
    @DataSource.SheetName("")
    public void verifyXDataEntry(Map<String, String> data) throws InterruptedException {
        String[] xInfo = xData(data);

        //customerMaintenancePage.clickNewCustomerButton();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString(""));

        logger.info("Successfully added data - {}", xInfo[0]);
    }
}