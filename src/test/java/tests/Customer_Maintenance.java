package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.*;

import java.util.Map;

public class Customer_Maintenance extends BaseClass {
    private Home_Page homePage;
    private CustomerMaintenance_Page customerMaintenancePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        customerMaintenancePage = new CustomerMaintenance_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Customer List page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickCustomerMaintenanceMenu();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("customerList"));

        logger.info("User clicked on the customer menu and successfully navigated to the Customer List page");
    }

    public static String[] customerData(Map<String, String> valueData) {

        String flag = valueData.get("Flag");
        String id = valueData.get("Id");
        String name = valueData.get("Name");
        String address = valueData.get("Address");
        String address_cont = valueData.get("Address Cont.");
        String country = valueData.get("Country");
        String state = valueData.get("State");
        String city = valueData.get("City");
        String zip = valueData.get("Zip");
        String status = valueData.get("Status");
        String customer_type = valueData.get("Type");
        String customer_class = valueData.get("Class");
        String tax_flag = valueData.get("Tax");
        String delivery_flag = valueData.get("Delivery");
        String charge = valueData.get("Charge");
        String discount = valueData.get("Discount");
        String reference = valueData.get("Reference");
        String comment = valueData.get("Comment");
        String email = valueData.get("Email");
        String phone = valueData.get("Phone");

        return new String[] {flag, id, name, address, address_cont, country, state, city, zip, status, customer_type, customer_class,
                tax_flag, delivery_flag, charge, discount, reference, comment, email, phone};
    }

    @Test(description = "Verify that the user can add customer's data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Customer")
    public void verifyCustomerDataEntry(Map<String, String> data) throws InterruptedException {
        String[] customerInfo = customerData(data);

        String storeName = homePage.getStoreName();
        String schedule = "Month End only";

        customerMaintenancePage.clickNewCustomerButton();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("customerMaintenance"));

        customerMaintenancePage.enterCustomerPersonalDetails(customerInfo[0],customerInfo[1],customerInfo[2],customerInfo[3],
                customerInfo[4],customerInfo[5],customerInfo[6],customerInfo[7],customerInfo[8]);

        logger.info("Personal details added successfully for {}", customerInfo[2]);

        Scroll(0, 500);

        SmallWait(500);

        customerMaintenancePage.enterCustomerEmail(customerInfo[18]);
        logger.info("Email added successfully for {}", customerInfo[2]);

        customerMaintenancePage.enterCustomerPhone(customerInfo[19]);
        logger.info("Phone number added successfully for {}", customerInfo[2]);

        Scroll(0, 800);

        SmallWait(500);

        customerMaintenancePage.enterCustomerOtherDetails(customerInfo[9], customerInfo[10], customerInfo[11],
                storeName, customerInfo[12], customerInfo[13], customerInfo[14], customerInfo[15], customerInfo[15],
                schedule, customerInfo[16], customerInfo[17]);

        logger.info("Other details added successfully for {}", customerInfo[2]);

        SmallWait(500);

        Scroll(0, 300);

        customerMaintenancePage.clickSaveButton();

        logger.info("Successfully added customer - {}", customerInfo[2]);
    }
}