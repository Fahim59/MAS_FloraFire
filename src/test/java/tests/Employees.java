package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.util.Map;

public class Employees extends BaseClass {
    private Home_Page homePage;
    private Employee_Page employeePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        employeePage = new Employee_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Employee list page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickEmployeesMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("employeeList"));

        logger.info("User successfully navigated to the Employee list page page");
    }

    public static String[] employeeData(Map<String, String> valueData) {
        /*
         * data[0] = firstname
         * data[1] = lastname
         * data[2] = address
         * data[3] = address cont
         * data[4] = country
         * data[5] = state
         * data[6] = city
         * data[7] = zip
         * data[8] = pType
         * data[9] = phone
         * data[10] = email
         * data[11] = hire date
         * data[12] = status
         * data[13] = role
         * data[14] = department
         * data[15] = order review
         * data[16] = username
         * data[17] = password
         * data[18] = empId
         * data[19] = pin
         * data[20] = contact person
         * data[21] = cpPhone
         * data[22] = relation
         */

        String firstname = valueData.get("First Name");
        String lastname = valueData.get("Last Name");
        String address = valueData.get("Address");
        String address_cont = valueData.get("Address Cont.");
        String country = valueData.get("Country");
        String state = valueData.get("State");
        String city = valueData.get("City");
        String zip = valueData.get("Zip");
        String pType = valueData.get("P. Type");
        String phone = valueData.get("Phone");
        String email = valueData.get("Email");
        String hireDate = valueData.get("Hire Date");
        String status = valueData.get("Status");
        String role = valueData.get("Role");
        String department = valueData.get("Department");
        String review = valueData.get("Order Review");
        String username = valueData.get("Username");
        String password = valueData.get("Password");
        String empId = valueData.get("Emp Id");
        String pin = valueData.get("PIN");
        String contact = valueData.get("Contact Person");
        String cPhone = valueData.get("C.P. Phone");
        String relation = valueData.get("Relation");

        return new String[] {firstname, lastname, address, address_cont, country, state, city, zip, pType, phone, email, hireDate, status, role,
        department, review, username, password, empId, pin, contact, cPhone, relation};
    }

    @Test(description = "Verify that the user can add Employee data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Employee")
    public void verifyEmployeeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] employeeInfo = employeeData(data);

        String storeName = homePage.getStoreName();

        String createMessage = jsonData.getJSONObject("successMessage").getString("employeeCreate");

        String comment = "N/A";

        employeePage.clickNewEmployeeButton();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("employeeMaintenance"));

        employeePage.enterEmployeePersonalDetails(employeeInfo[0], employeeInfo[1], employeeInfo[2], employeeInfo[3],
                employeeInfo[4], employeeInfo[5], employeeInfo[6], employeeInfo[7], employeeInfo[8], employeeInfo[9],
                employeeInfo[10], employeeInfo[11], comment);

        Scroll(0, 300);

        employeePage.enterEmployeeOtherDetails(employeeInfo[12], employeeInfo[13], storeName, employeeInfo[14], employeeInfo[15]);

        Scroll(0, 450);

        employeePage.enterUserDetails(employeeInfo[16], employeeInfo[17], employeeInfo[18], employeeInfo[19],
                employeeInfo[20], employeeInfo[21], employeeInfo[22]);

        employeePage.clickSaveButton();

        Assert.assertEquals(createMessage, employeePage.getSuccessMessage());

        employeePage.verifyEmployeeAddition(employeeInfo[18]);

        logger.info("Successfully added Employee - {}", employeeInfo[18]);
    }
}