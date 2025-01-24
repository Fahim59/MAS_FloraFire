package tests.Settings;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import pages.Settings.ValueTypeSettings_Page;
import pages.Settings.Value_Page;

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

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Value Types page", priority = 1)
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

    @Test(description = "Verify that user can add Account Class list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 3)
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

    @Test(description = "Verify that user can add AR Statement Invoice Type Id list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 4)
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

    @Test(description = "Verify that user can add Customer Status list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 5)
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

    @Test(description = "Verify that user can add Invoice PaymentSchedule list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 6)
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

    @Test(description = "Verify that user can add Payment Terms list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 7)
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

    @Test(description = "Verify that user can add Price Sheet Type list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 8)
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

    @Test(description = "Verify that user can add Referred By list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 9)
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

    @Test(description = "Verify that user can add Employee Department list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 10)
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

    @Test(description = "Verify that user can add Employee Status list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 11)
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

    @Test(description = "Verify that user can add EmployeeContactPersonRelation list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 12)
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

    @Test(description = "Verify that user can add Gift Card Reason list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 13)
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

    @Test(description = "Verify that user can add Care Code list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 14)
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

    @Test(description = "Verify that user can add ProductDepartment list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 15)
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

    @Test(description = "Verify that user can add Product Type list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 16)
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

    @Test(description = "Verify that user can add VehicleStatus list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 17)
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

    @Test(description = "Verify that user can add Tips Category list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 18)
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

    @Test(description = "Verify that user can add Misc Income Reason list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 19)
    public void verifyMiscIncomeReasonValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Misc Income Reason", valueData);

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

    @Test(description = "Verify that user can add Paid Out Reason list successfully", dataProvider = "ValueListData", dataProviderClass = DataSource.class, priority = 20)
    public void verifyPaidOutReasonValueListAddition(Map<String, String> valueData) throws InterruptedException {
        SmallWait(1000);

        String[] data = valueListData("Paid Out Reason", valueData);

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


    @Test(description = "Verify that user can map value type in the settings successfully", priority = 21)
    public void verifyValueTypeSettingMap() throws InterruptedException {
        SmallWait(2000);

        //homePage.clickValueTypeSettingsMenu();
        homePage.clickValueTypeSettingsM();

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

        String productType = jsonData.getJSONObject("values").getString("productType");
        String prodDepartment = jsonData.getJSONObject("values").getString("productDepartment");
        String careCode = jsonData.getJSONObject("values").getString("careCode");

        valueTypeSettingsPage.setProductFields(productType, prodDepartment, careCode);

        Scroll(0, 600);
        SmallWait(500);

        /*
         * setup of employee data
         */

        String empStatus = jsonData.getJSONObject("values").getString("employeeStatus");
        String empDepartment = jsonData.getJSONObject("values").getString("empDepartment");
        String empRole = jsonData.getJSONObject("values").getString("empRole");
        String contactPersonRelation = jsonData.getJSONObject("values").getString("contactPersonRelation");

        valueTypeSettingsPage.setEmployeeFields(empStatus, empDepartment, empRole, contactPersonRelation);

        /*
         * setup of vehicle data
         */

        String vehicleStatus = jsonData.getJSONObject("values").getString("vehicleStatus");

        valueTypeSettingsPage.selectVehicleStatus(vehicleStatus);

        /*
         * setup of gift card data
         */

        String giftCardReason = jsonData.getJSONObject("values").getString("giftCardReason");

        valueTypeSettingsPage.selectGiftCardReason(giftCardReason);

        /*
         * setup of order data
         */

        String tipCategory = jsonData.getJSONObject("values").getString("tipCategory");
        String miscIncome = jsonData.getJSONObject("values").getString("miscIncome");
        String paidOut = jsonData.getJSONObject("values").getString("paidOut");

        valueTypeSettingsPage.setOrderFields(tipCategory, miscIncome, paidOut);

        Scroll(0, 500);

        valueTypeSettingsPage.clickSaveBtn();

        logger.info("User mapped value type settings successfully.");
    }
}