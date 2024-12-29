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

        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("customerList"));

        logger.info("User clicked on the customer menu and successfully navigated to the Customer List page");
    }

    public static String[] customerData(Map<String, String> valueData) {
        /*
         * data[0] = flag
         * data[1] = id
         * data[2] = name
         * data[3] = address
         * data[4] = address cont
         * data[5] = country
         * data[6] = state
         * data[7] = city
         * data[8] = zip
         * data[9] = customer type
         * data[10] = store
         * data[11] = tax flag
         * data[12] = delivery flag
         * data[13] = charge
         * data[14] = discount
         * data[15] = reference
         * data[16] = comment
         * data[17] = email
         * data[18] = phone
         */

        String flag = valueData.get("Flag");
        String id = valueData.get("Id");
        String name = valueData.get("Name");
        String address = valueData.get("Address");
        String address_cont = valueData.get("Address Cont.");
        String country = valueData.get("Country");
        String state = valueData.get("State");
        String city = valueData.get("City");
        String zip = valueData.get("Zip");
        String customer_type = valueData.get("Type");
        String store = valueData.get("Store");
        String tax_flag = valueData.get("Tax");
        String delivery_flag = valueData.get("Delivery");
        String charge = valueData.get("Charge");
        String discount = valueData.get("Discount");
        String reference = valueData.get("Reference");
        String comment = valueData.get("Comment");
        String email = valueData.get("Email");
        String phone = valueData.get("Phone");

        return new String[] {flag, id, name, address, address_cont, country, state, city, zip, customer_type, store,
                tax_flag, delivery_flag, charge, discount, reference, comment, email, phone};
    }

    @Test(description = "Verify that the user can add customer's data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Test")
    public void verifyCustomerDataEntry(Map<String, String> data) throws InterruptedException {
        String[] customerInfo = customerData(data);

        SmallWait(2000);
        customerMaintenancePage.clickNewCustomerButton();

        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("customerMaintenance"));

        customerMaintenancePage.enterCustomerId(customerInfo[0],customerInfo[1]);
    }
}