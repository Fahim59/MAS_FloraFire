package tests.Settings.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.Delivery.ShortCode_Page;

import java.util.Map;

public class Short_Code extends BaseClass {
    private Home_Page homePage;
    private ShortCode_Page shortCodePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        shortCodePage = new ShortCode_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Short Code page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickShortCodeMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("shortCode"));

        logger.info("User successfully navigated to the Delivery Short Code page");
    }

    public static String[] shortCodeData(Map<String, String> valueData) {
        /*
         * data[0] = code
         * data[1] = location
         * data[2] = address
         * data[3] = address cont
         * data[4] = country
         * data[5] = state
         * data[6] = city
         * data[7] = zip
         * data[8] = phone
         * data[9] = instruction
         * data[10] = type
         * data[11] = zone
         * data[12] = flag
         * data[13] = charge
         */

        String code = valueData.get("Short Code");
        String location = valueData.get("Location");
        String address = valueData.get("Address");
        String address_cont = valueData.get("Address Cont.");
        String country = valueData.get("Country");
        String state = valueData.get("State");
        String city = valueData.get("City");
        String zip = valueData.get("Zip");
        String phone = valueData.get("Phone");
        String instruction = valueData.get("Special Instruction");
        String type = valueData.get("Delivery Type");
        String zone = valueData.get("Delivery Zone");
        String flag = valueData.get("Flag");
        String charge = valueData.get("Special Delivery Charge");

        return new String[] {code, location, address, address_cont, country, state, city, zip, phone,
                instruction, type, zone, flag, charge};
    }

    @Test(description = "Verify that the user can add Short Code data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("ShortCode")
    public void verifyShortCodeDataEntry(Map<String, String> data) throws InterruptedException {
        String[] shortCodeInfo = shortCodeData(data);

        String storeName = homePage.getStoreName();

        String successText = jsonData.getJSONObject("successMessage").getString("addressValidationSuccessText");
        String failedText = jsonData.getJSONObject("successMessage").getString("addressValidationFailedText");

        shortCodePage.clickNewCodeButton();

        shortCodePage.enterDeliveryShortCodeDetails(shortCodeInfo[0],shortCodeInfo[1],shortCodeInfo[2],shortCodeInfo[3],shortCodeInfo[4],
                shortCodeInfo[5],shortCodeInfo[6],shortCodeInfo[7],shortCodeInfo[8],shortCodeInfo[9],storeName,shortCodeInfo[10],
                shortCodeInfo[11],shortCodeInfo[12],shortCodeInfo[13]);

        shortCodePage.clickValidateButton();

        String validationMessage = shortCodePage.getValidationMessage();

        while (!validationMessage.equalsIgnoreCase(successText)) {
            if (validationMessage.equalsIgnoreCase(failedText)) {
                System.out.println("Validation Failed. Retrying...");

                shortCodePage.clickSuggestionText();

                SmallWait(1000);
                shortCodePage.clickValidateButton();
            }

            validationMessage = shortCodePage.getValidationMessage();
        }

        shortCodePage.clickSaveButton();

        shortCodePage.verifyDeliveryShortCodeAddition(shortCodeInfo[0]);

        logger.info("Successfully added Delivery Short Code - {}", shortCodeInfo[0]);
    }
}