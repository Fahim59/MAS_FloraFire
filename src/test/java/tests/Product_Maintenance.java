package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.util.Map;

public class Product_Maintenance extends BaseClass {
    private Home_Page homePage;
    private ProductMaintenance_Page productMaintenancePage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        productMaintenancePage = new ProductMaintenance_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Product Maintenance Page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickProductMaintenanceMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("productList"));

        logger.info("User successfully navigated to the Product Maintenance page");
    }

    public static String[] productData(Map<String, String> valueData) {
        String code = valueData.get("Code");
        String type = valueData.get("Type");
        String status = valueData.get("Status");
        String sku = valueData.get("SKU");
        String department = valueData.get("Department");
        String name = valueData.get("Name");
        String careCode = valueData.get("C. Code");
        String bin = valueData.get("Bin");
        String p_unit = valueData.get("P. Unit");
        String p_unit_value = valueData.get("P. Unit Value");
        String s_unit = valueData.get("S. Unit");
        String s_unit_value = valueData.get("S. Unit Value");
        String description = valueData.get("Description");
        String category = valueData.get("Category");
        String commisionable = valueData.get("Commisionable");
        String force_wireService = valueData.get("Force W.S.");
        String wireService = valueData.get("Wire Service");
        String nonTaxable = valueData.get("Non Taxable");
        String basePrice = valueData.get("Base Price");
        String midPrice = valueData.get("Mid Price");
        String highPrice = valueData.get("High Price");
        String wireOutPrice = valueData.get("WireOut Price");
        String unitCost = valueData.get("Unit Cost");
        String bp_override = valueData.get("BP Override");
        String trackInventory = valueData.get("Track Inventory");
        String onHand = valueData.get("On Hand");
        String onOrder = valueData.get("On Order");
        String lowStock = valueData.get("Low Stock");
        String outOfStockSale = valueData.get("Out of Stock Sale");
        String isSeasonal = valueData.get("Is Seasonal");
        String isSeasonalPricing = valueData.get("Is Seasonal Pricing");
        String seasonalPrice = valueData.get("Seasonal Price");
        String seasonalAvailability = valueData.get("Seasonal Availability");
        String startDate = valueData.get("Start Date");
        String endDate = valueData.get("End Date");
        String imageUrl = valueData.get("Image URL");
        String url = valueData.get("URL");

        return new String[] {code, type, status, sku, department, name, careCode, bin, p_unit, p_unit_value, s_unit, s_unit_value,
                description, category, commisionable, force_wireService, wireService, nonTaxable, basePrice, midPrice, highPrice,
                wireOutPrice, unitCost, bp_override, trackInventory, onHand, onOrder, lowStock, outOfStockSale, isSeasonal, isSeasonalPricing,
                seasonalPrice, seasonalAvailability, startDate, endDate, imageUrl, url};
    }

    @Test(description = "Verify that the user can add Gift Card data successfully", priority = 2, enabled = false)
    public void verifyGiftCardDataEntry() throws InterruptedException {
        /*
         * Variables
         */

        String storeName = homePage.getStoreName();

        String category = jsonData.getJSONObject("giftCard").getString("category");
        String type = jsonData.getJSONObject("giftCard").getString("type");
        String description = jsonData.getJSONObject("giftCard").getString("description");

        String createMessage = jsonData.getJSONObject("successMessage").getString("productCreate");

        String flag = jsonData.getJSONObject("giftCard").getString("flag");
        String url = jsonData.getJSONObject("giftCard").getString("url");
        String fileName = jsonData.getJSONObject("giftCard").getString("fileName");

        /*
         * Implementation
         */

        productMaintenancePage.clickNewProductButton();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("productMaintenance"));

        productMaintenancePage.enterGiftCardInfo(category, type, type, description, storeName);

        Scroll(0, 1600);

        productMaintenancePage.clickSaveAndContButton();

        SmallWait(1000);

        Assert.assertEquals(createMessage, productMaintenancePage.getSuccessMessage());

        Scroll(0, 1900);

        productMaintenancePage.uploadProductImage(flag, url, fileName);

        productMaintenancePage.verifyProductAddition("GC");

        logger.info("Gift Card added successfully");
    }

    @Test(description = "Verify that the user can add Product data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 3, enabled = false)
    @DataSource.SheetName("")
    public void verifyProductDataEntry(Map<String, String> data) throws InterruptedException {
        String[] productInfo = productData(data);

        String storeName = homePage.getStoreName();

        productMaintenancePage.clickNewProductButton();

        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("productMaintenance"));

        logger.info("Successfully added data - {}", productInfo[0]);
    }
}