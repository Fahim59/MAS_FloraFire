package tests.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Delivery.DeliveryCode_Page;
import pages.Home_Page;

import java.util.Map;

public class Delivery_Code extends BaseClass {
    private Home_Page homePage;
    private DeliveryCode_Page deliveryCodePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        deliveryCodePage = new DeliveryCode_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Delivery Code page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickDeliveryCodeMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("deliveryCode"));

        logger.info("User successfully navigated to the Delivery Code page");
    }

    public static String[] deliveryCodeData(Map<String, String> valueData) {
        /*
         * data[0] = code
         * data[1] = department
         * data[2] = shortDes
         * data[3] = phoneSystemCode
         * data[4] = longDes
         * data[5] = messageCode
         * data[6] = status
         * data[7] = message1
         * data[8] = sendProblemBox
         * data[9] = message2
         * data[10] = forceSignEntry
         * data[11] = emailFile
         * data[12] = leftAddress
         * data[13] = emailSubject
         */

        String code = valueData.get("Delivery Code");
        String department = valueData.get("Department");
        String shortDes = valueData.get("Short Description");
        String phoneSystemCode = valueData.get("Phone System Code");
        String longDes = valueData.get("Long Description");
        String messageCode = valueData.get("P.S. Message Code");
        String status = valueData.get("Status");
        String message1 = valueData.get("W.S. Message 1");
        String sendProblemBox = valueData.get("Send Problem Box");
        String message2 = valueData.get("W.S. Message 2");
        String forceSignEntry = valueData.get("Force Sign Entry");
        String emailFile = valueData.get("Email File Name");
        String leftAddress = valueData.get("Left Address Entry");
        String emailSubject = valueData.get("Email Subject");

        return new String[] {code, department, shortDes, phoneSystemCode, longDes, messageCode, status, message1, sendProblemBox,
                message2, forceSignEntry, emailFile, leftAddress, emailSubject};
    }

    @Test(description = "Verify that the user can add Delivery Code data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Test")
    public void verifyDeliveryCodeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] deliveryCodeInfo = deliveryCodeData(data);

        deliveryCodePage.clickNewDeliveryCodeButton();

        logger.info("Successfully added Delivery Code - {}", deliveryCodeInfo[0]);
    }
}