package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductMaintenance_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public ProductMaintenance_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newProductButton = By.xpath("//span[contains(text(),'New Product')]");

    public void clickNewProductButton() throws InterruptedException {
        SmallWait(2000);
        click_Element(newProductButton);
    }

    /*
     * Product Info
     */

    private final By codeField = By.xpath("//input[@formcontrolname='productCode']");
    private final By typeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");

    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By productSKUField = By.xpath("//input[@formcontrolname='sku']");

    private final By departmentField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");
    private final By itemNameField = By.xpath("//input[@formcontrolname='name']");

    private final By careCodeField = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");
    private final By binLocationField = By.xpath("//input[@formcontrolname='binLocation']");

    private final By purchaseUnitField = By.xpath("(//div[contains(@id,'mat-select-value')])[6]");
    private final By purchaseUnitValueField = By.xpath("//input[@formcontrolname='purchasedUnitOfMeasureValue']");

    private final By sellingUnitField = By.xpath("(//div[contains(@id,'mat-select-value')])[7]");
    private final By sellingUnitValueField = By.xpath("//input[@formcontrolname='sellingUnitOfMeasureValue']");

    private final By descriptionField = By.xpath("//textarea[@formcontrolname='description']");
    private final By storeField = By.xpath("(//div[contains(@id,'mat-select-value')])[8]");

    private final By productCategoryField = By.xpath("(//div[contains(@id,'mat-select-value')])[9]");

    private final By commisionableField = By.xpath("//mat-checkbox[@formcontrolname='isCommisionable']/div/div/input");

    private final By forceWireServiceField = By.xpath("//mat-checkbox[@formcontrolname='isForceWireServiceEnabled']/div/div/input");
    private final By wireServiceField = By.xpath("(//div[contains(@id,'mat-select-value')])[10]");

    public ProductMaintenance_Page enterProductCode(String code){
        write_Send_Keys(codeField, code);

        return this;
    }
    public ProductMaintenance_Page selectProductType(String type) throws InterruptedException {
        if(!get_Text(typeField).equals(type)){
            click_Element(typeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+type+"', ' ')]")));
        }

        return this;
    }

    public ProductMaintenance_Page selectProductStatus(String status) throws InterruptedException {
        if(!get_Text(statusField).equals(status)){
            click_Element(statusField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+status+"', ' ')]")));
        }

        return this;
    }
    public ProductMaintenance_Page enterProductSKU(String sku){
        write_Send_Keys(productSKUField, sku);

        return this;
    }

    public ProductMaintenance_Page selectProductDepartment(String department) throws InterruptedException {
        if(!get_Text(departmentField).equals(department)){
            click_Element(departmentField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+department+"', ' ')]")));
        }

        return this;
    }
    public ProductMaintenance_Page enterProductName(String name){
        write_Send_Keys(itemNameField, name);

        return this;
    }

    public ProductMaintenance_Page selectProductCareCode(String care) throws InterruptedException {
        if(!get_Text(careCodeField).equals(care)){
            click_Element(careCodeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+care+"', ' ')]")));
        }

        return this;
    }
    public ProductMaintenance_Page enterProductBinLocation(String bin){
        write_Send_Keys(binLocationField, bin);

        return this;
    }

    public ProductMaintenance_Page selectPurchaseUnit(String purchaseUnit) throws InterruptedException {
        if(!get_Text(purchaseUnitField).equals(purchaseUnit)){
            click_Element(purchaseUnitField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+purchaseUnit+"', ' ')]")));
        }

        return this;
    }
    public ProductMaintenance_Page enterPurchaseUnitValue(String purchaseValue){
        write_Send_Keys(purchaseUnitValueField, purchaseValue);

        return this;
    }

    public ProductMaintenance_Page selectSellingUnit(String sellingUnit) throws InterruptedException {
        if(!get_Text(sellingUnitField).equals(sellingUnit)){
            click_Element(sellingUnitField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+sellingUnit+"', ' ')]")));
        }

        return this;
    }
    public ProductMaintenance_Page enterSellingUnitValue(String sellingValue){
        write_Send_Keys(sellingUnitValueField, sellingValue);

        return this;
    }

    public ProductMaintenance_Page enterDescription(String description){
        write_Send_Keys(descriptionField, description);

        return this;
    }
    public ProductMaintenance_Page selectStore(String store) throws InterruptedException {
        click_Element(storeField);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+store+"')]")));

        return this;
    }

    public ProductMaintenance_Page selectProductCategoryType(String categoryType) throws InterruptedException {
        if(!get_Text(productCategoryField).equals(categoryType)){
            click_Element(productCategoryField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+categoryType+"', ' ')]")));
        }

        return this;
    }

    public ProductMaintenance_Page isCommisionable(String flag) throws InterruptedException {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(commisionableField);
        }

        Scroll(0, 600);
        SmallWait(500);

        return this;
    }
    public void isForceWireService(String flag, String wireService) throws InterruptedException {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(forceWireServiceField);
            SmallWait(500);

            click_Element(wireServiceField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+wireService+"', ' ')]")));
        }
    }

    public void enterGiftCardInfo(String categoryType, String type, String name, String description, String store) throws InterruptedException {
        selectProductCategoryType(categoryType).selectProductType(type).enterProductName(name).enterDescription(description).selectStore(store);
    }

    public void enterProductInfo(String code, String type, String status, String sku, String department, String name, String care,
                                 String bin, String purchaseUnit, String purchaseValue, String sellingUnit, String sellingValue,
                                 String description, String store, String categoryType, String flag, String wireFlag, String wireService) throws InterruptedException {

        enterProductCode(code).selectProductType(type).selectProductStatus(status).enterProductSKU(sku).selectProductDepartment(department).
                enterProductName(name).selectProductCareCode(care).enterProductBinLocation(bin).selectPurchaseUnit(purchaseUnit).
                enterPurchaseUnitValue(purchaseValue).selectSellingUnit(sellingUnit).enterSellingUnitValue(sellingValue).
                enterDescription(description).selectStore(store).selectProductCategoryType(categoryType).isCommisionable(flag).
                isForceWireService(wireFlag, wireService);
    }

    /*
     * Product Pricing Details
     */

    private final By nonTaxableField = By.xpath("//mat-checkbox[@formcontrolname='isNonTaxable']/div/div/input");

    private final By basePriceField = By.xpath("//input[@formcontrolname='basePrice']");
    private final By midPriceField = By.xpath("//input[@formcontrolname='midPrice']");

    private final By highPriceField = By.xpath("//input[@formcontrolname='highPrice']");
    private final By wireOutField = By.xpath("//input[@formcontrolname='wireOut']");
    private final By unitCostField = By.xpath("//input[@formcontrolname='unitCost']");

    private final By basePriceOverriddenField = By.xpath("//mat-checkbox[@formcontrolname='isBasePriceOverriden']/div/div/input");

    public ProductMaintenance_Page isNonTaxable(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(nonTaxableField);
        }
        return this;
    }

    public ProductMaintenance_Page enterBasePrice(String basePrice){
        write_Send_Keys(basePriceField, basePrice);

        return this;
    }
    public ProductMaintenance_Page enterMidPrice(String midPrice){
        write_Send_Keys(midPriceField, midPrice);

        return this;
    }

    public ProductMaintenance_Page enterHighPrice(String highPrice){
        write_Send_Keys(highPriceField, highPrice);

        return this;
    }
    public ProductMaintenance_Page enterWireOutPrice(String wireOut){
        write_Send_Keys(wireOutField, wireOut);

        return this;
    }
    public ProductMaintenance_Page enterUnitCost(String unitCost){
        write_Send_Keys(unitCostField, unitCost);

        return this;
    }

    public void isAllowBasePriceOverridden(String overFlag) {
        if(overFlag.equalsIgnoreCase("Yes")){
            selectCheckBox(basePriceOverriddenField);
        }
    }

    public void enterProductPricingDetails(String flag, String basePrice, String midPrice, String highPrice, String wireOut,
                                 String unitCost, String overFlag) {

        isNonTaxable(flag).enterBasePrice(basePrice).enterMidPrice(midPrice).enterHighPrice(highPrice).enterWireOutPrice(wireOut).
                enterUnitCost(unitCost).isAllowBasePriceOverridden(overFlag);
    }

    /*
     * Product Inventory Details
     */

    private final By trackInventoryField = By.xpath("//mat-checkbox[@formcontrolname='isTrackingInventoryEnabled']/div/div/input");

    private final By onHandField = By.xpath("//input[@formcontrolname='onHandQuantity']");
    private final By onOrderField = By.xpath("//input[@formcontrolname='onOrderQuantity']");
    private final By lowStockField = By.xpath("//input[@formcontrolname='lowStockQuantiy']");

    private final By ouOfStockSalesField = By.xpath("//mat-checkbox[@formcontrolname='isOutOfStockSalesEnabled']/div/div/input");

    public ProductMaintenance_Page isTrackInventory(String trackFlag) throws InterruptedException {
        Scroll(0, 400);
        SmallWait(500);

        if(trackFlag.equalsIgnoreCase("Yes")){
            selectCheckBox(trackInventoryField);
        }
        return this;
    }

    public ProductMaintenance_Page enterOnHandQuantity(String onHand){
        write_Send_Keys(onHandField, onHand);

        return this;
    }
    public ProductMaintenance_Page enterOnOrderQuantity(String onOrder){
        write_Send_Keys(onOrderField, onOrder);

        return this;
    }
    public ProductMaintenance_Page enterLowStockQuantity(String lowStock){
        write_Send_Keys(lowStockField, lowStock);

        return this;
    }

    public void isAllowOutOfStockSales(String trackFlag, String stockFlag) {
        if(trackFlag.equalsIgnoreCase("Yes")){
            if(stockFlag.equalsIgnoreCase("Yes")){
                selectCheckBox(ouOfStockSalesField);
            }
        }
    }

    public void enterProductInventoryDetails(String trackFlag, String onHand, String onOrder, String lowStock, String stockFlag) throws InterruptedException {

        isTrackInventory(trackFlag).enterOnHandQuantity(onHand).enterOnOrderQuantity(onOrder).enterLowStockQuantity(lowStock).
                isAllowOutOfStockSales(trackFlag, stockFlag);
    }

    /*
     * Product Other Details
     */

    private final By isSeasonalField = By.xpath("//mat-checkbox[@formcontrolname='isSeasonal']/div/div/input");

    private final By isSeasonalPricing = By.xpath("//mat-checkbox[@formcontrolname='isSeasonalPricingEnabled']/div/div/input");

    private final By seasonalPriceField = By.xpath("//input[@formcontrolname='seasonalPrice']");

    private final By isSeasonalAvailability = By.xpath("//mat-checkbox[@formcontrolname='isSeasonalAvailabilityEnabled']/div/div/input");

    private final By startDateField = By.xpath("//input[@formcontrolname='seasonalAvailabilityStartDate']");

    private final By endDateField = By.xpath("//input[@formcontrolname='seasonalAvailabilityEndDate']");

    private final By commentField = By.xpath("//textarea[@formcontrolname='comment']");

    public ProductMaintenance_Page isSeasonalField(String seasonalFlag) throws InterruptedException {
        Scroll(0, 650);
        SmallWait(500);

        if(seasonalFlag.equalsIgnoreCase("Yes")){
            selectCheckBox(isSeasonalField);
        }
        return this;
    }

    public ProductMaintenance_Page isSeasonalPricingField(String seasonalFlag, String seasonalPricingFlag) {
        if(seasonalFlag.equalsIgnoreCase("Yes")){
            if(seasonalPricingFlag.equalsIgnoreCase("Yes")){
                selectCheckBox(isSeasonalPricing);
            }
        }

        return this;
    }
    public ProductMaintenance_Page enterSeasonalPrice(String seasonalFlag, String seasonalPricingFlag, String price){
        if(seasonalFlag.equalsIgnoreCase("Yes")){
            if(seasonalPricingFlag.equalsIgnoreCase("Yes")){
                write_Send_Keys(seasonalPriceField, price);
            }
        }

        return this;
    }

    public ProductMaintenance_Page isSeasonalAvailabilityField(String seasonalFlag, String seasonalAvailabilityFlag) {
        if(seasonalFlag.equalsIgnoreCase("Yes")){
            if(seasonalAvailabilityFlag.equalsIgnoreCase("Yes")){
                selectCheckBox(isSeasonalAvailability);
            }
        }

        return this;
    }
    public ProductMaintenance_Page enterSeasonalStartDate(String seasonalFlag, String seasonalAvailabilityFlag, String startDate) {
        if(seasonalFlag.equalsIgnoreCase("Yes")){
            if(seasonalAvailabilityFlag.equalsIgnoreCase("Yes")){
                write_Send_Keys(startDateField, startDate);
            }
        }

        return this;
    }
    public ProductMaintenance_Page enterSeasonalEndDate(String seasonalFlag, String seasonalAvailabilityFlag, String endDate) {
        if(seasonalFlag.equalsIgnoreCase("Yes")){
            if(seasonalAvailabilityFlag.equalsIgnoreCase("Yes")){
                write_Send_Keys(endDateField, endDate);
            }
        }

        return this;
    }

    public void enterComment(String comment) {
        write_Send_Keys(commentField, comment);
    }

    public void enterProductOtherDetails(String seasonalFlag, String seasonalPricingFlag, String price, String seasonalAvailabilityFlag,
                                         String startDate, String endDate, String comment) throws InterruptedException {

        isSeasonalField(seasonalFlag).isSeasonalPricingField(seasonalFlag, seasonalPricingFlag).
                enterSeasonalPrice(seasonalFlag, seasonalPricingFlag, price).isSeasonalAvailabilityField(seasonalFlag,
                        seasonalAvailabilityFlag).enterSeasonalStartDate(seasonalFlag, seasonalAvailabilityFlag, startDate).
                enterSeasonalEndDate(seasonalFlag, seasonalAvailabilityFlag, endDate).enterComment(comment);
    }

    /*
     * Product Image Upload
     */

    private final By isImageURLField = By.cssSelector("#mat-mdc-checkbox-20-input");

    private final By imageURLField = By.xpath("//input[@formcontrolname='imageUrl']");

    private final By imageUploadField = By.xpath("(//*[@type='text' and @readonly='true'])[3]");

    public void uploadProductImage(String flag, String url, String fileName) throws InterruptedException {
        SmallWait(1500);

        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(isImageURLField);
            SmallWait(200);
            write_Send_Keys(imageURLField, url);

            SmallWait(1000);
            click_Element_Js(saveBtn);
        }
        else{
            upload_file(imageUploadField, fileName);

            SmallWait(3000);
            click_Element_Js(saveBtn);
        }
    }

    /*
     * Recipe Info
     */

    private final By recipeBtn = By.xpath("//mat-checkbox[@formcontrolname='hasRecipes']/div/div/input");

    private final By addExistingProductBtn = By.xpath("//span[contains(text(),'Add Existing Product')]");

    private final By productCodeField = By.xpath("//input[@formcontrolname='productCode']");

    private final By productQuantityField = By.xpath("//input[@formcontrolname='quantity']");

    private final By recipeSaveBtn = By.xpath("(//span[contains(text(),'Save')])[3]");

    public ProductMaintenance_Page isRecipeField(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(recipeBtn);
        }

        return this;
    }

    public ProductMaintenance_Page clickAddExistingProductButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(addExistingProductBtn);

        return this;
    }

    public ProductMaintenance_Page enterRecipeProductCode(String code) throws InterruptedException {
        SmallWait(3000);
        //write_Send_Keys(productCodeField, code);
        //write_ActionClass(productCodeField, code);

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(productCodeField));
        js.executeScript("arguments[0].value = arguments[1];", element, code);

        SmallWait(1500);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+code+"', ' ')]")));

        return this;
    }

    public ProductMaintenance_Page enterRecipeProductQuantity(String quantity) {
        write_Send_Keys(productQuantityField, quantity);

        return this;
    }

    public void clickRecipeSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element(recipeSaveBtn);
    }

    public void enterRecipeData(String code, String quantity) throws InterruptedException {
        clickAddExistingProductButton().enterRecipeProductCode(code).enterRecipeProductQuantity(quantity).
                clickRecipeSaveButton();
    }

    /*
     * Button and Message
     */

    private final By saveAndContBtn = By.xpath("//span[contains(text(),'Save And Continue')]");
    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    private final String productTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(productTable+"/tr");

    public void clickSaveAndContButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveAndContBtn);
    }

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public void verifyProductAddition(String code) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String productCode = driver.findElement(By.xpath(productTable+ "/tr["+l+"]/td[1]")).getText();

            if(productCode.equalsIgnoreCase(code)){
                logger.info("Product {} found", code);
                break;
            }
        }
    }

    private final By searchField = By.xpath("//input[@placeholder='Search by Product Code / Item Name or SKU']");

    public void clickActionButton(String code) throws InterruptedException {
        write_Send_Keys(searchField, code);
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String productCode = driver.findElement(By.xpath(productTable+ "/tr["+l+"]/td[1]")).getText();

            if(productCode.equalsIgnoreCase(code)){
                SmallWait(1000);

                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(productTable+ "/tr["+l+"]/td[11]"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Edit']"))).click();
                break;
            }
        }
    }
}