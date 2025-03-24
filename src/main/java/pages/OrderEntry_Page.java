package pages;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderEntry_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public OrderEntry_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final String cartTable = "//table[@role='table']/tbody";
    private final By productRows = By.xpath(cartTable+"/tr");

    /*
     * Product Addition in Cart
     */

    private final By searchProductField = By.xpath("(.//*[@placeholder='Search Keyword (Product Name, Code)'])[1]");
    private final By searchProductIconField = By.xpath("(.//mat-icon[text()='search'])[1]");

    private final By searchProductField2 = By.xpath("(.//*[@placeholder='Search Keyword (Product Name, Code or Description)'])[1]");
    private final By sortProductDropdown = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");

    private final By productCode = By.xpath("(.//div[@class='info text-truncate']/span)[1]");
    private final By productQuantity = By.xpath("(.//div[@class='stock ng-star-inserted'])[1]");
    private final By addToOrderBtn = By.xpath("//span[contains(text(),'Add To Order')]");
    private final By closeBtn = By.xpath("//span[contains(text(),'Close')]");

    public void searchProductEnter(String code) throws InterruptedException {
        SmallWait(1000);

        WebElement searchField = wait_for_presence(searchProductField);
        searchField.clear();
        searchField.sendKeys(code);
        searchField.sendKeys(Keys.ENTER);

        SmallWait(2000);
    }

    public void searchProductIcon(String code) throws InterruptedException {
        SmallWait(1000);

        click_Element_Js(searchProductIconField);

        WebElement searchField = wait_for_presence(searchProductField2);
        searchField.clear();
        searchField.sendKeys(code);
        SmallWait(2000);

        clickAddToOrderBtn(code);

        SmallWait(2000);
    }
    public void clickAddToOrderBtn(String code) throws InterruptedException {
        SmallWait(500);
        String proCode = get_Text(productCode);

        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(get_Text(productQuantity));
        int proStock = 0;

        if (matcher.find()) {
            proStock = Integer.parseInt(matcher.group());
        }

        if(proCode.equalsIgnoreCase(code)){
            if(proStock > 0){
                click_Element_Js(addToOrderBtn);
            }
            else{
                logger.info("Insufficient Stock");
            }
        }
        else{
            logger.info("Product Code Mismatch");
        }

        SmallWait(2000);

        click_Element_Js(closeBtn);
    }
    public void sortProduct(String type) throws InterruptedException {
        SmallWait(2000);

        if(!get_Text(sortProductDropdown).equals(type)){
            click_Element(sortProductDropdown);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
        }
    }

    /*
     * Price Type, Quantity, Line Item Discount
     */

    public void setPriceType(List<String> types) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement priceTypeField = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[4]"));
            String priceType = priceTypeField.getText();

            System.out.println("Price Type: " +priceType);

            String type = (l <= types.size()) ? types.get(l - 1) : "BP";

            if(!priceType.equals(type)){
                priceTypeField.click();
                SmallWait(200);
                js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
            }
        }
    }

    public void setProductQuantity(List<String> quantities) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement quantityField = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[5]//input"));

            String quantity = (l <= quantities.size()) ? quantities.get(l - 1) : "1";

            quantityField.clear();
            quantityField.sendKeys(quantity);
        }
    }

    public void setProductDiscount(List<String> discounts) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement discountField = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[6]//input"));

            String discount = (l <= discounts.size()) ? discounts.get(l - 1) : "";

            discountField.click();
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+discount+"']")));
        }
    }

    /*
     * Delivery Method
     */

    // * Carryout

    private final By occasionDropdown = By.xpath("//mat-select[@formcontrolname='occasion']");

    private final By recipientNameField = By.xpath("//input[@formcontrolname='recipientName']");
    private final By shortCodeDropdown = By.xpath("//mat-select[@formcontrolname='shortCodeId']");

    private final By saveBtn = By.xpath("(.//span[contains(text(),'Save')])[2]");

    public void selectOccasion(String occasion) throws InterruptedException {
        SmallWait(1000);

        click_Element(occasionDropdown);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+occasion+"', ' ')]")));
    }

    public void enterRecipientName(String name){
        write_Send_Keys(recipientNameField, name);
    }
    public void selectShortCode(String code) throws InterruptedException {
        SmallWait(1000);

        click_Element(shortCodeDropdown);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+code+"', ' ')]")));
    }

    public void carryOutDelivery(String flag, String occasion, String name, String code) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement carryOutBtn = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[9]//button[1]"));

            if(flag.equalsIgnoreCase("true")){
                carryOutBtn.click();
            }

            SmallWait(500);

            selectOccasion(occasion);
            enterRecipientName(name);
            selectShortCode(code);
            click_Element_Js(saveBtn);
        }
    }

    // * WillCall

    private final By deliveryFromField = By.xpath("//input[@formcontrolname='deliveryFromDate']");
    private final By deliveryToField = By.xpath("//input[@formcontrolname='deliveryToDate']");

    private final By pickUpLocationDropdown = By.xpath("//mat-select[@formcontrolname='pickupLocationId']");

    private final By personPickUpField = By.xpath("//input[@formcontrolname='personPickingUp']");

    private final By timeReqField = By.xpath("(.//*[@type='checkbox'])[3]");

    private final By hourField = By.xpath("//input[@formcontrolname='deliveryTimeHour']");
    private final By minuteField = By.xpath("//input[@formcontrolname='defaultDeliveryMinute']");
    private final By timingDropdown = By.xpath("//mat-select[@formcontrolname='deliveryTimeType']");

    private final By specialInstructionField = By.xpath("//textarea[@formcontrolname='specialInstruction']");

    private final By nextBtn = By.xpath("(.//span[contains(text(),'Next')])[1]");

    public void enterDeliveryFromDate(String fromDate){
        write_Send_Keys(deliveryFromField, fromDate);
    }
    public void enterDeliveryToDate(String toDate){
        write_Send_Keys(deliveryToField, toDate);
    }

    public void selectPickUpLocation(String location) throws InterruptedException {
        SmallWait(500);

        click_Element(pickUpLocationDropdown);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()='"+location+"']")));
    }

    public void enterPersonPickingUp(String person){
        write_Send_Keys(personPickUpField, person);
    }

    public void isTimeRequirement(String flag, String hour, String minute, String timing) throws InterruptedException {
        if(flag.equalsIgnoreCase("true")){
            selectCheckBox(timeReqField);
            SmallWait(200);

            write_Send_Keys(hourField, hour);
            write_Send_Keys(minuteField, minute);

            click_Element_Js(timingDropdown);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+timing+"', ' ')]")));
        }
    }

    public void enterSpecialInstruction(String instruction){
        write_Send_Keys(specialInstructionField, instruction);
    }

    public void willCallDelivery(String flag, String store) throws InterruptedException {
        SmallWait(1000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement willCallBtn = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[9]//button[2]"));
            willCallBtn.click();

            if(flag.equalsIgnoreCase("true")){
                willCallBtn.click();
            }

            SmallWait(500);
            enterDeliveryFromDate(formattedDate);
            enterDeliveryToDate(formattedDate);

            selectPickUpLocation(store);

            enterPersonPickingUp("Mustafizur Rahman");

            isTimeRequirement("true","03","30", "Before");

            enterSpecialInstruction("Call before coming");

            click_Element_Js(nextBtn);

            SmallWait(200);

            carryOutDelivery("false","Holiday", "Mustafizur Rahman", "AOL - All Our Love");
        }
    }

    // * Recipient

    private final By recipientRows = By.xpath("(.//table[@role='table']/tbody/tr)[2]");

    private final By systemResourceTab = By.xpath("//span[contains(text(),'System Resources')]");

    private final By validationMessage = By.xpath("//p[@class='abp-toast-message']");
    private final By validateBtn = By.xpath("//span[normalize-space()='Validate']");
    public void recipientDelivery(String type, String source, String name) throws InterruptedException {
        SmallWait(1000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement recipientBtn = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[9]//button[3]"));
            recipientBtn.click();

            if(type.equalsIgnoreCase("old")){
                if(source.equalsIgnoreCase("system")){
                    click_Element(systemResourceTab);

                    for(int r = 1; r <= get_Size(recipientRows); l++){
                        WebElement recipientName = driver.findElement(By.xpath("(.//table[@role='table']/tbody/tr["+r+"]/td[1])[2]"));
                        WebElement actionBtn = driver.findElement(By.xpath("(.//table[@role='table']/tbody/tr["+r+"]/td[5])[2]"));

                        if(recipientName.getText().equalsIgnoreCase(name)){
                            actionBtn.click();

                            click_Element(validateBtn);

                            //String text = wait_for_visibility(validationMessage).getText();

                            while(!wait_for_visibility(validationMessage).getText().contains("successfully")){
                                click_Element(validateBtn);
                            }

                            click_Element_Js(nextBtn);

                            SmallWait(500);
                            enterDeliveryFromDate(formattedDate);
                            enterDeliveryToDate(formattedDate);

                            isTimeRequirement("false","03","30", "Before");

                            enterSpecialInstruction("Call before coming");

                            click_Element_Js(nextBtn);

                            SmallWait(200);

                            carryOutDelivery("false","Holiday", "Mustafizur Rahman", "AOL - All Our Love");

                            break;
                        }
                    }
                }
            }

//            SmallWait(500);
//            enterDeliveryFromDate(formattedDate);
//            enterDeliveryToDate(formattedDate);
//
//            selectPickUpLocation(store);
//
//            enterPersonPickingUp("Mustafizur Rahman");
//
//            isTimeRequirement("true","03","30", "Before");
//
//            click_Element_Js(nextBtn);
//
//            SmallWait(200);
//
//            carryOutDelivery("false","Holiday", "Mustafizur Rahman", "AOL - All Our Love");

            break;
        }
    }

    /*
     * Customer Selection
     */

    private final By searchCustomerIconField = By.xpath("(.//mat-icon[text()='search'])[2]");
    private final By searchCustomerField = By.xpath("(.//*[contains(@placeholder, 'Search Keyword (Customer Name')])[2]");

    private final String customerTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(customerTable+"/tr");

    public OrderEntry_Page clickCustomerSearchIcon() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(searchCustomerIconField);

        return this;
    }
    public OrderEntry_Page enterCustomerName(String name) throws InterruptedException {
        SmallWait(1000);
        write_Send_Keys(searchCustomerField, name);

        return this;
    }
    public void customerInfo(String id) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String customerId = driver.findElement(By.xpath(customerTable+ "/tr["+l+"]/td[2]")).getText();
            WebElement selectBtn = driver.findElement(By.xpath(customerTable+ "/tr["+l+"]/td[6]"));

            if(customerId.equalsIgnoreCase(id)){
                selectBtn.click();
                SmallWait(2000);

                customerType = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Type')]]/span[2]")).getText();
                discount = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Discount')]]/span[2]")).getText();
                deliveryCharge = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Delivery Charge')]]/span[2]")).getText().replaceAll("[^0-9.]", "");
                taxExempt = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Tax Exempt')]]/span[2]")).getText();

                logger.info("Customer Type: {} \nDiscount Amount: {} \nDelivery Charge: {} \nTax Exempt: {}",
                        customerType, discount, deliveryCharge, taxExempt);

                WebElement addBtn = driver.findElement(By.xpath("(//span[contains(text(),'Add')])[3]"));
                addBtn.click();

                logger.info("Customer {} added", id);

                break;
            }
        }
    }

    public void selectCustomer(String name, String id) throws InterruptedException {
        clickCustomerSearchIcon().enterCustomerName(name).customerInfo(id);
    }

    /*
     * Order Type
     */

    private final By orderTypeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");

    public OrderEntry_Page selectOrderType(String type) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(orderTypeField).equals(type)){
            click_Element(orderTypeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
        }

        return this;
    }

    /*
     * Moneybox
     */

    private final By subTotalField = By.cssSelector("#mat-input-1");
    private final By deliveryChargeField = By.cssSelector("#mat-input-2");
    private final By discountField = By.cssSelector("#mat-input-3");
    private final By taxField = By.cssSelector("#mat-input-4");

    public void moneyboxData() throws InterruptedException {
        SmallWait(1000);

        WebElement subTotal = wait_for_visibility(subTotalField);
        WebElement deliveryCharge = wait_for_visibility(deliveryChargeField);
        WebElement discount = wait_for_visibility(discountField);
        WebElement tax = wait_for_visibility(taxField);

        String subTotalValue = subTotal.getAttribute("value");
        String deliveryChargeValue = deliveryCharge.getAttribute("value");
        String discountValue = discount.getAttribute("value");
        String taxValue = tax.getAttribute("value");

        logger.info("SubTotal is:{}", subTotalValue);
        logger.info("Delivery Charge is:{}", deliveryChargeValue);
        logger.info("Discount is:{}", discountValue);
        logger.info("Tax is:{}", taxValue);
    }

    /*
     *  Whole Order Discount or Delivery Discount
     */

    private final By wholeOrderDiscount = By.xpath("(.//mat-label[text()='Discount Code'])[1]");

    public OrderEntry_Page selectWholeOrderDiscount(String discount) throws InterruptedException {
        SmallWait(1000);

        click_Element(wholeOrderDiscount);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+discount+"']")));

        return this;
    }

    /*
     * Tip
     */

    private final By addTipBtn = By.xpath("//div[@class='mat-ripple add-tip mb-2 ng-star-inserted']");
    private final By departmentField = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");
    private final By tipAmountField = By.xpath("//input[@formcontrolname='tipAmount']");

    private final By confirmBtn = By.xpath("//span[normalize-space()='Confirm']");

    public OrderEntry_Page clickTipButton() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(addTipBtn);

        return this;
    }

    public OrderEntry_Page selectDepartment(String dept) throws InterruptedException {
        SmallWait(1500);

        if(!get_Text(departmentField).equals(dept)){
            click_Element(departmentField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+dept+"']")));
        }

        return this;
    }

    public OrderEntry_Page enterTipAmount(String amount){
        write_Send_Keys(tipAmountField, amount);

        return this;
    }

    public void clickConfirmButton() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(confirmBtn);

        tip = true;
    }

    public void enterTip(String dept, String amount) throws InterruptedException {
        clickTipButton().selectDepartment(dept).enterTipAmount(amount).clickConfirmButton();
    }

    /*
     * Payment
     */
}