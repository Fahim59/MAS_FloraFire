package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import pages.Settings.ValueTypeSettings_Page;
import pages.Settings.Value_Page;
import utils.ConfigLoader;
import utils.ExcelReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Value_Addition extends BaseClass {
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

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Value Types page", priority = 1, enabled = false)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickValueMenu();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("valueTypes"));

        logger.info("User clicked on the value menu and successfully navigated to the Value Types page");
    }


    @Test(description = "Verify that user can add values successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2, enabled = false)
    @DataSource.SheetName("Value")
    public void verifyValueAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String childValue = valueData.get("Name");
        String orderNo = valueData.get("Order");

        String message = jsonData.getJSONObject("successMessage").getString("valueType");

        valuePage.clickNewValueButton();

        valuePage.enterValueDetails(childValue, orderNo);

        Assert.assertEquals(message, valuePage.getValueTypeSuccessMessage());

        SmallWait(1000);

        logger.info("User added values successfully.");
    }


    public static String[] valueListData(String name, Map<String, String> valueData) {
        value = name;

        String childValue = valueData.get("Name");
        String orderNo = valueData.get("Order");
        String preSelected = valueData.get("PreSelected");
        String flag = valueData.get("Flag");

        return new String[] {value, childValue, orderNo, preSelected, flag};
    }

    @Test(description = "Verify that user can add Account Class list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 3, enabled = false)
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

    @Test(description = "Verify that user can add AR Statement Invoice Type Id list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 4, enabled = false)
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

    @Test(description = "Verify that user can add Customer Status list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 5, enabled = false)
    public void verifyCustomerStatusValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Customer Status", valueData);

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

    @Test(description = "Verify that user can add Invoice PaymentSchedule list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 6, enabled = false)
    public void verifyInvoicePaymentScheduleValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Invoice Payment Schedule", valueData);

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

    @Test(description = "Verify that user can add Payment Terms list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 7, enabled = false)
    public void verifyPaymentTermsValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Payment Terms", valueData);

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

    @Test(description = "Verify that user can add Price Sheet Type list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 8, enabled = false)
    public void verifyPriceSheetTypeValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Price Sheet Type", valueData);

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

    @Test(description = "Verify that user can add Referred By list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 9, enabled = false)
    public void verifyReferredByValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Referred By", valueData);

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

    @Test(description = "Verify that user can add Employee Department list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 10, enabled = false)
    public void verifyEmployeeDepartmentValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Employee Department", valueData);

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

    @Test(description = "Verify that user can add Employee Status list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 11, enabled = false)
    public void verifyEmployeeStatusValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Employee Status", valueData);

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

    @Test(description = "Verify that user can add EmployeeContactPersonRelation list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 12, enabled = false)
    public void verifyEmployeeContactPersonRelationValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Employee Contact Person Relation", valueData);

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

    @Test(description = "Verify that user can add Gift Card Reason list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 13, enabled = false)
    public void verifyGiftCardReasonValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Gift Card Reason", valueData);

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

    @Test(description = "Verify that user can add Care Code list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 14, enabled = false)
    public void verifyCareCodeValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Care Code", valueData);

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

    @Test(description = "Verify that user can add ProductDepartment list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 15, enabled = false)
    public void verifyProductDepartmentValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Product Department", valueData);

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

    @Test(description = "Verify that user can add Product Type list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 16, enabled = false)
    public void verifyProductTypeValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Product Type", valueData);

        if(data[4].equalsIgnoreCase("1")){
            valuePage.selectValue(data[0]);
            SmallWait(2000);
            valuePage.clickDetailsButton(data[0]);
        }

        valuePage.clickNewValueButton();

        valuePage.enterValueListDetails(data[1], data[2], data[3]);

        Assert.assertEquals(message, valuePage.getValueSuccessMessage());

        SmallWait(1500);

        if(data[4].equalsIgnoreCase("-1")){
            valuePage.clickBackButton();
        }

        logger.info("User added \"{}\" in the value list successfully.", data[1]);
    }

    @Test(description = "Verify that user can add VehicleStatus list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 17, enabled = false)
    public void verifyVehicleStatusValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Vehicle Status", valueData);

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

    @Test(description = "Verify that user can add Tips Category list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 18, enabled = false)
    public void verifyTipsCategoryValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Tip Category", valueData);

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


    @Test(description = "Verify that user can map value type in the settings successfully", priority = 19, enabled = true)
    public void verifyValueTypeSettingMap() throws InterruptedException {
        SmallWait(1000);

        homePage.clickValueTypeSettingsMenu();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("valueTypeSettings"));

        /*
         * setup of customer data
         */

        String status = jsonData.getJSONObject("values").getString("status");
        String accountClass = jsonData.getJSONObject("values").getString("accClass");
        String referredBy = jsonData.getJSONObject("values").getString("referredBy");
        String manager = jsonData.getJSONObject("values").getString("accManager");
        String invoicePayment = jsonData.getJSONObject("values").getString("invoicePayment");
        String term = jsonData.getJSONObject("values").getString("term");
        String statementInvoice = jsonData.getJSONObject("values").getString("statementInvoice");
        String priceSheet = jsonData.getJSONObject("values").getString("priceSheet");

        valueTypeSettingsPage.setCustomerFields(status, accountClass, referredBy, manager, invoicePayment, term, statementInvoice, priceSheet);

        /*
         * setup of product data
         */

//        String productType = jsonData.getJSONObject("values").getString("productType");
//        String prodDepartment = jsonData.getJSONObject("values").getString("productDepartment");
//        String careCode = jsonData.getJSONObject("values").getString("careCode");
//
//        valueTypeSettingsPage.setProductFields(productType, prodDepartment, careCode);
//
//        Scroll(0, 600);
//        SmallWait(500);

        /*
         * setup of employee data
         */

//        String empStatus = jsonData.getJSONObject("values").getString("employeeStatus");
//        String empDepartment = jsonData.getJSONObject("values").getString("empDepartment");
//        String empRole = jsonData.getJSONObject("values").getString("empRole");
//        String contactPersonRelation = jsonData.getJSONObject("values").getString("contactPersonRelation");
//
//        valueTypeSettingsPage.setEmployeeFields(empStatus, empDepartment, empRole, contactPersonRelation);

        /*
         * setup of vehicle data
         */

//        String vehicleStatus = jsonData.getJSONObject("values").getString("vehicleStatus");
//
//        valueTypeSettingsPage.selectVehicleStatus(vehicleStatus);

        /*
         * setup of gift card data
         */

//        String giftCardReason = jsonData.getJSONObject("values").getString("giftCardReason");
//
//        valueTypeSettingsPage.selectGiftCardReason(giftCardReason);

        /*
         * setup of order data
         */

//        String tipCategory = jsonData.getJSONObject("values").getString("tipCategory");
//
//        valueTypeSettingsPage.selectTipCategory(tipCategory);
//
//        Scroll(0, 500);
//
//        valueTypeSettingsPage.clickSaveBtn();

        logger.info("User mapped value type settings successfully.");
    }

//    private Object[][] testData;
//
//    @BeforeMethod
//    public Object[][] loadData(Method method) throws IOException, InvalidFormatException {
//        ExcelReader reader = new ExcelReader();
//        List<Map<String, String>> allData = reader.getData(
//                new ConfigLoader().initializeProperty().getProperty("dataFile"), "ValueList");
//
//        int startRow = 0;
//        int endRow = 0;
//
//        if (method.getName().contains("AccountClass")) {
//            startRow = 0;
//            endRow = 2;
//        }
//        else if (method.getName().contains("ARStatementInvoice")) {
//            startRow = 3;
//            endRow = 5;
//        }
//        else if (method.getName().contains("CustomerStatus")) {
//            startRow = 6;
//            endRow = 9;
//        }
//        else if (method.getName().contains("InvoicePaymentSchedule")) {
//            startRow = 10;
//            endRow = 11;
//        }
//        else if (method.getName().contains("PaymentTerms")) {
//            startRow = 12;
//            endRow = 14;
//        }
//        else if (method.getName().contains("PriceSheetType")) {
//            startRow = 15;
//            endRow = 16;
//        }
//        else if (method.getName().contains("ReferredByValue")) {
//            startRow = 17;
//            endRow = 20;
//        }
//        else if (method.getName().contains("EmployeeDepartment")) {
//            startRow = 21;
//            endRow = 25;
//        }
//        else if (method.getName().contains("EmployeeStatus")) {
//            startRow = 26;
//            endRow = 29;
//        }
//        else if (method.getName().contains("EmployeeContactPersonRelation")) {
//            startRow = 30;
//            endRow = 33;
//        }
//        else if (method.getName().contains("GiftCardReason")) {
//            startRow = 34;
//            endRow = 35;
//        }
//        else if (method.getName().contains("CareCode")) {
//            startRow = 36;
//            endRow = 37;
//        }
//        else if (method.getName().contains("ProductDepartment")) {
//            startRow = 38;
//            endRow = 41;
//        }
//        else if (method.getName().contains("ProductType")) {
//            startRow = 42;
//            endRow = 45;
//        }
//        else if (method.getName().contains("VehicleStatus")) {
//            startRow = 46;
//            endRow = 49;
//        }
//        else if (method.getName().contains("TipsCategory")) {
//            startRow = 50;
//            endRow = 53;
//        }
//
//        testData = new Object[endRow - startRow + 1][1];
//        for (int i = startRow, j = 0; i <= endRow; i++, j++) {
//            testData[j][0] = allData.get(i);
//        }
//        return testData;
//    }
//
//    @Test
//    public void verifyAccountClassValueList(Map<String, String> valueData) {
////        for (Object[] dataRow : testData) {
////            Map<String, String> rowData = (Map<String, String>) dataRow[0];
////            System.out.println("Running testAccountClass with data: " + rowData);
////        }
//
//        String[] data = valueListData("Account Class", valueData);
//
//        System.out.println(data[0]);
//    }
}