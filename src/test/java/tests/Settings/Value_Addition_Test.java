package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Home_Page;
import pages.Settings.ValueTypeSettings_Page;
import pages.Settings.Value_Page;
import utils.ConfigLoader;
import utils.ExcelReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

public class Value_Addition_Test extends BaseClass {
    private Home_Page homePage;
    private Value_Page valuePage;
    private ValueTypeSettings_Page valueTypeSettingsPage;

    private String message;
    private static String value;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        valuePage = new Value_Page(driver);
        valueTypeSettingsPage = new ValueTypeSettings_Page(driver);

        message = jsonData.getJSONObject("successMessage").getString("value");
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Value Types page", priority = 1, enabled = true)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickValueMenu();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("valueTypes"));

        logger.info("User clicked on the value menu and successfully navigated to the Value Types page");
    }

    public static String[] valueListData(String name, Map<String, String> valueData) {
        value = name;

        String childValue = valueData.get("Name");
        String orderNo = valueData.get("Order");
        String preSelected = valueData.get("PreSelected");
        String flag = valueData.get("Flag");

        return new String[] {value, childValue, orderNo, preSelected, flag};
    }

    @Test(description = "Verify that user can add Account Class list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 3, enabled = true)
    public void verifyAccountClassValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Account Class", valueData);

        if(data[4].equalsIgnoreCase("1")){
            valuePage.selectValue(data[0]);
            SmallWait(2000);
            valuePage.clickDetailsButton(data[0]);
        }

        valuePage.clickNewValueButton();

        valuePage.enterValueListDetails(data[1], data[2], data[3]);

        Assert.assertEquals(message, valuePage.getValueSuccessMessage());

        SmallWait(1000);

        if(data[4].equalsIgnoreCase("-1")){
            valuePage.clickBackButton();
        }

        logger.info("User added \"{}\" in the value list successfully.", data[1]);
    }

    @Test(description = "Verify that user can add AR Statement Invoice Type Id list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 4, enabled = true)
    public void verifyARStatementInvoiceValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("AR Statement Invoice Type Id", valueData);

        if(data[4].equalsIgnoreCase("1")){
            valuePage.selectValue(data[0]);
            SmallWait(2000);
            valuePage.clickDetailsButton(data[0]);
        }

        valuePage.clickNewValueButton();

        valuePage.enterValueListDetails(data[1], data[2], data[3]);

        Assert.assertEquals(message, valuePage.getValueSuccessMessage());

        SmallWait(1000);

        if(data[4].equalsIgnoreCase("-1")){
            valuePage.clickBackButton();
        }

        logger.info("User added \"{}\" in the value list successfully.", data[1]);
    }
}