package pages;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void setZoom80Percent() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
    }

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
                Assert.fail("Insufficient Stock");
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

    public void clickSuggestionText(){
        List<WebElement> suggestions = driver.findElements(By.xpath("//div[@class='cdk-overlay-container']//li/strong"));

        for (WebElement suggestion : suggestions) {

            String text = suggestion.getText();
            System.out.println(text);
            String[] parts = text.split(", ");

            if (parts.length == 3) {
                suggestion.click();
                break;
            }
        }
    }
    public void enterRecipientDeliveryAddress(String type) throws InterruptedException, IOException {
        SmallWait(1000);

        if(type.equalsIgnoreCase("new")){
            WebElement firstNameField = driver.findElement(By.xpath("//input[@formcontrolname='firstName']"));
            WebElement lastNameField = driver.findElement(By.xpath("//input[@formcontrolname='lastName']"));

            WebElement addressField = driver.findElement(By.xpath("//input[@formcontrolname='address1']"));
            WebElement countryField = driver.findElement(By.xpath("//input[@formcontrolname='address2']/following::input[1]"));
            WebElement stateField = driver.findElement(By.xpath("//input[@formcontrolname='city']/preceding::input[1]"));
            WebElement cityField = driver.findElement(By.xpath("//input[@formcontrolname='city']"));
            WebElement zipField = driver.findElement(By.xpath("//input[@formcontrolname='zipCode']"));

            WebElement locationTypeField = driver.findElement(By.xpath("//mat-select[@formcontrolname='locationType']"));

            WebElement emailField = driver.findElement(By.xpath("//input[@formcontrolname='email']"));
            WebElement phoneField = driver.findElement(By.xpath("//input[@formcontrolname='phoneNumber']"));

            firstNameField.sendKeys(getFirstName());
            lastNameField.sendKeys(getLastName());

            addressField.sendKeys("10311 Garland Road"); //getAddress()

            //countryField.sendKeys();

            stateField.click();  //getState()
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', 'Texas', ' ')]")));

            cityField.sendKeys("Dallas"); //getCity()

            zipField.sendKeys("75218");

            locationTypeField.click();
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', 'Home', ' ')]")));

            emailField.sendKeys(getEmail());
            phoneField.sendKeys(getPhone());
        }

        WebElement validateBtn = driver.findElement(By.xpath("//span[normalize-space()='Validate']"));

        validateBtn.click();
        SmallWait(3000);

        try{
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='abp-toast-message']"))).getText();
            System.out.println(text);

            while(!text.contains("successfully")){
                logger.info("Validation Failed. Retrying...");

                SmallWait(3000);
                clickSuggestionText();
            }

        }
        catch (Exception e) {
            logger.info("Validation Message not found - {}", e.getMessage());
        }

        SmallWait(1000);

        takeScreenshot();
    }

    public void enterRecipientDeliveryDetails(String type, String store) throws InterruptedException, IOException {
        SmallWait(1000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        enterDeliveryFromDate(formattedDate);
        enterDeliveryToDate(formattedDate);

        SmallWait(500);

        if(type.equalsIgnoreCase("new")){
            WebElement deliveryTypeField = driver.findElement(By.xpath("//mat-select[@formcontrolname='deliveryType']"));

            deliveryTypeField.click();
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', 'Delivery', ' ')]")));

            WebElement deliveryZoneField = driver.findElement(By.xpath("//mat-select[@formcontrolname='deliveryZoneId']"));

            deliveryZoneField.click();
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), 'Garland Road')]")));

            WebElement fulfillingStoreField = driver.findElement(By.xpath("//mat-select[@formcontrolname='fulfillingStoreId']"));

            fulfillingStoreField.click();
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+store+"')]")));
        }

        SmallWait(1000);

        takeScreenshot();
    }

    public void enterOrderPersonalizationDetails() throws InterruptedException, IOException {
        selectOccasion("Business");
        selectShortCode("BR - Best Regards");

        SmallWait(1000);

        takeScreenshot();
    }

    public void addNewRecipient(String store) throws InterruptedException, IOException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement recipientBtn = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[9]//button[3]"));
            recipientBtn.click();

            SmallWait(1000);

            WebElement newRecipientBtn = driver.findElement(By.xpath("//span[contains(text(),'Add New Recipient')]"));
            newRecipientBtn.click();

            enterRecipientDeliveryAddress("new");

            WebElement nextBtn = driver.findElement(By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Next'])[1]"));
            nextBtn.click();

            enterRecipientDeliveryDetails("new", store);

            WebElement nextBtn2 = driver.findElement(By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Next'])[2]"));
            nextBtn2.click();

            enterOrderPersonalizationDetails();

            click_Element_Js(saveBtn);

            break;
        }
    }

    public void addExistingRecipient(String name, String store) throws InterruptedException, IOException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement recipientBtn = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[9]//button[3]"));
            recipientBtn.click();

            SmallWait(1000);

            click_Element(systemResourceTab);

            SmallWait(1000);

            WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Enter Keyword (Search by Name or Phone Number)']"));
            searchBox.sendKeys(name);

            SmallWait(2000);

            for(int r = 1; r <= get_Size(recipientRows); l++){
                WebElement recipientName = driver.findElement(By.xpath("(.//table[@role='table']/tbody/tr["+r+"]/td[1])[2]"));
                WebElement actionBtn = driver.findElement(By.xpath("(.//table[@role='table']/tbody/tr["+r+"]/td[5])[2]"));

                if(recipientName.getText().equalsIgnoreCase(name)) {
                    actionBtn.click();

                    SmallWait(2000);
                    break;
                }
            }

            enterRecipientDeliveryAddress("old");

            WebElement nextBtn = driver.findElement(By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Next'])[1]"));
            nextBtn.click();

            enterRecipientDeliveryDetails("old", store);

            WebElement nextBtn2 = driver.findElement(By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Next'])[2]"));
            nextBtn2.click();

            enterOrderPersonalizationDetails();

            click_Element_Js(saveBtn);

            break;
        }
    }

    /*
     * Customer Selection
     */

    private final By searchCustomerIconField = By.xpath("(.//mat-icon[text()='search'])[2]");
    private final By searchCustomerField = By.xpath("(.//*[contains(@placeholder, 'Search Keyword (Customer Name')])[2]");

    private final String customerTable = "//div[@class='customer-list']//table[@role='table']/tbody";
    private final By rows = By.xpath(customerTable+"/tr");

    private final By addCustomerBtn = By.xpath("//span[contains(text(),'Add New Customer')]");

    private final By nameField = By.xpath("//input[@formcontrolname='name']");
    private final By addressField = By.xpath("//input[@formcontrolname='address1']");
    private final By countryField = By.xpath("(//input[contains(@role, 'combobox')])[2]");
    private final By stateField = By.xpath("//input[@formcontrolname='city']/preceding::input[1]");
    private final By cityField = By.xpath("//input[@formcontrolname='city']");
    private final By zipField = By.xpath("//input[@formcontrolname='zip']");
    private final By phoneField = By.xpath("//input[@formcontrolname='phoneNumber']");
    private final By emailField = By.xpath("//input[@formcontrolname='email']");

    private final By taxExemptField = By.xpath("//mat-radio-group[@formcontrolname='taxExempt']//input[contains(@name, 'mat-radio-group')]");
    private final By taxNumberField = By.xpath("//input[@formcontrolname='taxCertificate']");

    private final By customerSaveBtn = By.xpath("(//span[contains(text(),'Save')])[2]");

    public OrderEntry_Page clickCustomerSearchIcon(String... id) throws InterruptedException {
        SmallWait(1000);

        if (id.length > 0) {
            WebElement searchField = driver.findElement(By.xpath("(//*[contains(@placeholder, 'Search Keyword')])[2]"));
            searchField.sendKeys(id);
        }

        click_Element_Js(searchCustomerIconField);

        return this;
    }
    public void customerInfo(String id) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(rows); l++){
            String customerId = driver.findElement(By.xpath(customerTable+ "/tr["+l+"]/td[2]")).getText();
            WebElement selectBtn = driver.findElement(By.xpath(customerTable+ "/tr["+l+"]/td[6]"));

            if(customerId.equalsIgnoreCase(id)){
                selectBtn.click();
                SmallWait(2000);

                customerType = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Type')]]/span[2]")).getText();
                discount = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Discount')]]/span[2]")).getText().replaceAll("[^0-9.]", "");
                deliveryCharge = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Delivery Charge')]]/span[2]")).getText().replaceAll("[^0-9.]", "");
                taxExempt = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Tax Exempt')]]/span[2]")).getText();

                logger.info("Customer Type: {} \nDiscount Amount: {} \nDelivery Charge: {} \nTax Exempt: {}",
                        customerType, discount, deliveryCharge, taxExempt);

                WebElement addBtn = driver.findElement(By.xpath("(//div[@class='customer-details']//span[contains(text(),'Add')])[2]"));
                addBtn.click();

                logger.info("Customer {} added", id);

                break;
            }
        }
    }

    public void selectCustomer(String id) throws InterruptedException {
        clickCustomerSearchIcon(id).customerInfo(id);
    }

    public OrderEntry_Page clickAddCustomerButton() throws InterruptedException {
        click_Element(addCustomerBtn);
        SmallWait(2000);
        return this;
    }

    public OrderEntry_Page enterCustomerName(String name){
        write_Send_Keys(nameField, name);
        return this;
    }

    public OrderEntry_Page enterCustomerAddress(String address){
        write_Send_Keys(addressField, address);
        return this;
    }

    public OrderEntry_Page selectCustomerCountry(String country) throws InterruptedException {
        SmallWait(4000);

        if(!get_Text(countryField).equals(country)){
            click_Element(countryField);
            write_Send_Keys(countryField, country);

            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+country+"')]")));
        }

        return this;
    }
    public OrderEntry_Page selectCustomerState(String state) throws InterruptedException {
        SmallWait(2000);

        if(!get_Text(stateField).equals(state)){
            click_Element(stateField);

            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+state+"')]")));
        }
        return this;
    }
    public OrderEntry_Page enterCustomerCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public OrderEntry_Page enterCustomerZip(String zip){
        write_Send_Keys(zipField, zip);
        return this;
    }

    public OrderEntry_Page enterCustomerPhone(String phone){
        write_Send_Keys(phoneField, phone);
        return this;
    }
    public OrderEntry_Page enterCustomerEmail(String email){
        write_Send_Keys(emailField, email);
        return this;
    }

    public OrderEntry_Page enterTaxDetails(String flag) throws InterruptedException {
        if(flag.equalsIgnoreCase("No")){
            click_Radio_Element(taxExemptField, "false");
        }
        else{
            click_Radio_Element(taxExemptField, "true");

            SmallWait(500);
            write_Send_Keys(taxNumberField, "9xx-xx-xxxx");
        }

        SmallWait(500);

        return this;
    }

    public void clickCustomerSaveBtn() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(customerSaveBtn);
    }

    public void addNewCustomer(String name, String address, String state, String city, String zip, String phone, String email, String flag) throws InterruptedException {
        clickCustomerSearchIcon().clickAddCustomerButton().enterCustomerName(name).enterCustomerAddress(address).
                selectCustomerState(state).enterCustomerCity(city).enterCustomerZip(zip).enterCustomerPhone(phone).enterCustomerEmail(email).
                enterTaxDetails(flag).clickCustomerSaveBtn();
    }

    /*
     * Order Type
     */

    private final By orderTypeField = By.xpath("(//mat-select[contains(@role, 'combobox')])[4]");

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

    private final By amountPayableField = By.xpath("//div[@class='amount-payable']//div[2]");
    private final By amountPaidField = By.xpath("//div[@class='amount-pay']//div[2]");
    private final By amountDueField = By.xpath("//div[@class='amount-due']//div[2]");
    private final By changeDueField = By.xpath("//div[@class='change-due']//div[2]");

    public void moneyboxData() throws InterruptedException {
        SmallWait(1000);

        WebElement subTotal = wait_for_visibility(subTotalField);
        WebElement deliveryCharge = wait_for_visibility(deliveryChargeField);
        WebElement discount = wait_for_visibility(discountField);
        WebElement tax = wait_for_visibility(taxField);

        subTotalAmount = subTotal.getAttribute("value");
        deliveryAmount = deliveryCharge.getAttribute("value");
        discountAmount = discount.getAttribute("value");
        taxAmount = tax.getAttribute("value");

        logger.info("SubTotal is:{}", subTotalAmount);
        logger.info("Delivery Charge is:{}", deliveryAmount);
        logger.info("Discount is:{}", discountAmount);
        logger.info("Tax is:{}", taxAmount);

        WebElement amountPayableAmount = wait_for_visibility(amountPayableField);
        WebElement amountPaidAmount = wait_for_visibility(amountPaidField);
        WebElement amountDueAmount = wait_for_visibility(amountDueField);
        WebElement changeDueAmount = wait_for_visibility(changeDueField);

        amountPayable = amountPayableAmount.getText().replaceAll("[^0-9.]", "");
        amountPaid = amountPaidAmount.getText().replaceAll("[^0-9.]", "");
        amountDue = amountDueAmount.getText().replaceAll("[^0-9.]", "");
        changeDue = changeDueAmount.getText().replaceAll("[^0-9.]", "");

        logger.info("Amount Payable is:{}", amountPayable);
        logger.info("Amount Paid is:{}", amountPaid);
        logger.info("Amount Due is:{}", amountDue);
        logger.info("Change Due is:{}", changeDue);
    }

    /*
     *  Whole Order Discount or Delivery Discount
     */

    private final By wholeOrderDiscount = By.xpath("(.//mat-label[text()='Discount Code'])[1]");

    public void selectWholeOrderDiscount(String discount) throws InterruptedException {
        SmallWait(1000);

        click_Element(wholeOrderDiscount);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+discount+"']")));
    }

    /*
     * Tip
     */

    private final By addTipBtn = By.xpath("//div[@class='mat-ripple add-tip mb-2 ng-star-inserted']");
    private final By departmentField = By.xpath("//mat-select[@formcontrolname='tipValueTypeId']");
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

    private final By paymentBtn = By.xpath("//div[@class='mat-ripple pos-payment mb-2']");
    private final By paymentOptionsField = By.xpath("(//mat-select[contains(@role, 'combobox')])[7]");

    private final By acceptBtn = By.xpath("//span[contains(text(),'Accept')]");
    private final By doneBtn = By.xpath("//span[contains(text(),'Done')]");

    public OrderEntry_Page clickPaymentBtn() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(paymentBtn);

        return this;
    }

    public void selectPaymentOption(String option, String... splitOptions) throws InterruptedException, IOException {
        SmallWait(1500);

        if(!get_Text(paymentOptionsField).equals(option)){
            click_Element(paymentOptionsField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+option+"']")));
        }

        SmallWait(1000);

        switch (option) {
            case "Cash":
                WebElement cashPaidField = driver.findElement(By.xpath("(//input[@type='number'])[5]"));
                cashPaidField.sendKeys(amountPayable);

                SmallWait(1000);
                click_Element(acceptBtn);

                SmallWait(1000);
                takeScreenshot();
                click_Element(doneBtn);
                break;

            case "Check":
                WebElement checkNumberField = driver.findElement(By.xpath("//input[@formcontrolname='checkNumber']"));
                checkNumberField.sendKeys("547859");

                SmallWait(1000);
                click_Element(acceptBtn);

                SmallWait(1000);
                takeScreenshot();
                click_Element(doneBtn);
                break;

            case "Gift Card":
                WebElement gcNumberField = driver.findElement(By.xpath("//span[contains(text(),'Accept')]/preceding::input[1]"));
                gcNumberField.sendKeys("1001");

                SmallWait(2000);
                WebElement gcAmountField = driver.findElement(By.xpath("//span[contains(text(),'Accept')]/preceding::input[1]"));
                gcAmountField.sendKeys(amountPayable);

                SmallWait(1000);
                click_Element(acceptBtn);

                SmallWait(2000);
                takeScreenshot();
                click_Element(doneBtn);
                break;

            case "Credit / Debit Card":
                SmallWait(2000);

                driver.switchTo().frame("heartland-frame-cardNumber");
                WebElement cardNumberField = driver.findElement(By.xpath("//input[@name='cardNumber']"));
                cardNumberField.sendKeys("3782 822463 10005");
                driver.switchTo().defaultContent();

                driver.switchTo().frame("heartland-frame-cardExpiration");
                WebElement expirationField = driver.findElement(By.xpath("//input[@name='cardExpiration']"));
                expirationField.sendKeys("01 / 2026");
                driver.switchTo().defaultContent();

                driver.switchTo().frame("heartland-frame-cardCvv");
                WebElement cvvField = driver.findElement(By.xpath("//input[@name='cardCvv']"));
                cvvField.sendKeys("1234");
                driver.switchTo().defaultContent();

                SmallWait(1000);
                click_Element(acceptBtn);

                WebElement paymentItems = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='payment-item']")));

                if(paymentItems.isDisplayed()){
                    takeScreenshot();
                    click_Element(doneBtn);
                }

                break;

            case "House":
                SmallWait(1000);
                click_Element(acceptBtn);

                SmallWait(2000);
                click_Element(doneBtn);
                break;

            default:
                logger.info("Invalid Payment Method...!");
                break;
        }
    }

    public void splitPayment(String... splitOptions) throws InterruptedException {

        if (splitOptions.length % 2 != 0) {                                                           //validating we have pairs of payment methods and amounts
            logger.error("Invalid split options. Must provide payment method and amount pairs.");
            return;
        }

        clickPaymentBtn();
        SmallWait(2000);

        for (int i = 0; i < splitOptions.length; i += 2) {
            String method = splitOptions[i];
            String amount = splitOptions[i+1];

            click_Element(paymentOptionsField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+method+"']")));

            SmallWait(1000);

            switch (method) {
                case "Cash":
                    WebElement cashPaidField = driver.findElement(By.xpath("(//input[@type='number'])[5]"));
                    cashPaidField.sendKeys(amount);

                    click_Element(acceptBtn);

                    SmallWait(1500);
                    break;

                case "House Account":
                    click_Element(acceptBtn);

                    break;
            }
        }

        SmallWait(3000);
        click_Element(doneBtn);
    }

    public void makePayment(String option) throws InterruptedException, IOException {
        clickPaymentBtn().selectPaymentOption(option);
    }
}