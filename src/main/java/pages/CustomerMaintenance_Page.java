package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerMaintenance_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public CustomerMaintenance_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newCustomerButton = By.xpath("//span[contains(text(),'New Customer')]");

    public void clickNewCustomerButton() throws InterruptedException {
        SmallWait(2000);
        click_Element(newCustomerButton);
    }

    /*
     * Personal Details
     */

    private final By manualIdType = By.xpath("(.//*[@type='radio'])[1]");
    private final By autoIdType = By.xpath("(.//*[@type='radio'])[2]");

    private final By customerIdField = By.xpath("//input[@formcontrolname='customerId']");

    private final By nameField = By.xpath("//input[@formcontrolname='name']");

    private final By addressField = By.xpath("//input[@formcontrolname='address1']");
    private final By addressContField = By.xpath("//input[@formcontrolname='address2']");
    private final By countryField = By.xpath("//input[@formcontrolname='countryId']");
    private final By stateField = By.xpath("//input[@formcontrolname='stateProvinceId']");
    private final By cityField = By.xpath("//input[@formcontrolname='city']");
    private final By zipField = By.xpath("//input[@formcontrolname='zip']");

    private final By faxField = By.xpath("//input[@formcontrolname='fax']");

    public CustomerMaintenance_Page enterCustomerId(String flag, String id){
        if(flag.equalsIgnoreCase("Yes")){
            click_Radio_Element(autoIdType, "true");
        }
        else{
            click_Radio_Element(manualIdType, "false");
            write_Send_Keys(customerIdField, id);
        }

        return this;
    }

    public CustomerMaintenance_Page enterCustomerName(String name){
        write_Send_Keys(nameField, name);
        return this;
    }

    public CustomerMaintenance_Page enterCustomerAddress(String address){
        write_Send_Keys(addressField, address);
        return this;
    }
    public CustomerMaintenance_Page enterCustomerAddressCont(String address){
        write_Send_Keys(addressContField, address);
        return this;
    }

    public CustomerMaintenance_Page selectCustomerCountry(String country) throws InterruptedException {
        click_Element(countryField);
        write_Send_Keys(countryField, country);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+country+"')]")));
        return this;
    }
    public CustomerMaintenance_Page selectCustomerState(String state) throws InterruptedException {
        SmallWait(1500);
        click_Element(stateField);
        SmallWait(500);
        write_Send_Keys(stateField, state);

        SmallWait(500);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+state+"')]")));
        return this;
    }

    public CustomerMaintenance_Page enterCustomerCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public CustomerMaintenance_Page enterCustomerZip(String zip){
        write_Send_Keys(zipField, zip);
        return this;
    }

    public void enterCustomerFax(){
        write_Send_Keys(faxField, "+1-212-555-1234");
    }

    public void enterCustomerPersonalDetails(String flag, String id, String name, String address, String addressCont, String country,
                                             String state, String city, String zip) throws InterruptedException {

        enterCustomerId(flag, id).enterCustomerName(name).enterCustomerAddress(address).enterCustomerAddressCont(addressCont).
                selectCustomerCountry(country).selectCustomerState(state).enterCustomerCity(city).enterCustomerZip(zip).
                enterCustomerFax();
    }

    private final By newEmailButton = By.xpath("//span[contains(text(),'New Email')]");
    private final By emailField = By.xpath("//input[@formcontrolname='email']");
    private final By isPrimaryEmailField = By.xpath("(.//*[@type='checkbox'])[2]");
    private final By saveEmailBtn = By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Save'])[2]");

    private final By newPhoneButton = By.xpath("//span[contains(text(),'New Phone Number')]");
    private final By phoneField = By.xpath("//input[@formcontrolname='phoneNumber']");
    private final By isPrimaryPhoneField = By.xpath("(.//*[@type='checkbox'])[2]");
    private final By savePhoneBtn = By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Save'])[2]");

    public CustomerMaintenance_Page clickEmailButton() {
        click_Element(newEmailButton);
        return this;
    }
    public void enterEmail(String email) throws InterruptedException {
        write_Send_Keys(emailField, email);
        selectCheckBox(isPrimaryEmailField);

        SmallWait(500);
        click_Element(saveEmailBtn);
    }

    public void enterCustomerEmail(String email) throws InterruptedException {
        SmallWait(1000);
        clickEmailButton().enterEmail(email);
    }

    public CustomerMaintenance_Page clickPhoneButton() {
        click_Element(newPhoneButton);
        return this;
    }
    public void enterPhone(String phone) throws InterruptedException {
        write_Send_Keys(phoneField, phone);
        selectCheckBox(isPrimaryPhoneField);

        SmallWait(500);
        click_Element(savePhoneBtn);
    }

    public void enterCustomerPhone(String phone) throws InterruptedException {
        SmallWait(1000);
        clickPhoneButton().enterPhone(phone);
    }

    /*
     * Other Details
     */

    private final By customerStatusField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By customerTypeField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By customerAccountClassField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");
    private final By storeField = By.xpath("(//div[contains(@id,'mat-select-value')])[6]");

    private final By taxExemptField = By.xpath("//mat-radio-group[@formcontrolname='taxExempt']//input[contains(@name, 'mat-radio-group')]");
    private final By taxNumberField = By.xpath("//input[@formcontrolname='taxCertificate']");

    private final By deliveryField = By.xpath("//mat-checkbox[@formcontrolname='hasDeliveryCharge']/div/div/input");
    private final By deliveryChargeField = By.xpath("//input[@formcontrolname='deliveryCharge']");

    private final By discountField = By.xpath("//input[@formcontrolname='discount']");

    private final By wireOutDiscountField = By.xpath("//input[@formcontrolname='discountOnWireout']");

    private final By referenceField = By.xpath("//input[@formcontrolname='customerReference']");
    private final By commentField = By.xpath("//textarea[@formcontrolname='comment']");

    public CustomerMaintenance_Page selectCustomerStatus(String status) throws InterruptedException {
        if(!get_Text(customerStatusField).equals(status)){
            click_Element(customerStatusField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+status+"', ' ')]")));
        }

        return this;
    }
    public CustomerMaintenance_Page selectCustomerType(String type) throws InterruptedException {
        if(!get_Text(customerTypeField).equals(type)){
            click_Element(customerTypeField);

            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+type+"', ' ')]")));
        }

        return this;
    }

    public CustomerMaintenance_Page selectCustomerClass(String accClass) throws InterruptedException {
        if(!get_Text(customerAccountClassField).equals(accClass)){
            click_Element(customerAccountClassField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+accClass+"', ' ')]")));
        }

        return this;
    }
    public CustomerMaintenance_Page selectStore(String store) throws InterruptedException {
        if(!get_Text(storeField).contains(store)){
            click_Element(storeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+store+"')]")));
        }

        return this;
    }

    public CustomerMaintenance_Page enterTaxDetails(String flag) throws InterruptedException {
        if(flag.equalsIgnoreCase("No")){
            click_Radio_Element(taxExemptField, "false");
        }
        else{
            click_Radio_Element(taxExemptField, "true");

            SmallWait(500);
            write_Send_Keys(taxNumberField, "9xx-xx-xxxx");
        }

        Scroll(0, 500);

        SmallWait(500);

        return this;
    }

    public CustomerMaintenance_Page addDeliveryCharge(String flag, String charge) throws InterruptedException {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(deliveryField);

            SmallWait(500);
            write_Send_Keys(deliveryChargeField, charge);
        }
        return this;
    }

    public CustomerMaintenance_Page enterCustomerDiscount(String discount){
        write_Send_Keys(discountField, discount);
        return this;
    }
    public CustomerMaintenance_Page enterWireOutDiscount(String discount){
        write_Send_Keys(wireOutDiscountField, discount);
        return this;
    }

    public CustomerMaintenance_Page enterCustomerReference(String reference){
        write_Send_Keys(referenceField, reference);
        return this;
    }

    public void enterComment(String comment) throws InterruptedException {
        write_Send_Keys(commentField, comment);

        Scroll(0, 500);
    }

    public void enterCustomerOtherDetails(String status, String type, String accClass, String store, String flag, String deliveryFlag,
                                          String charge, String discount, String wireDiscount, String reference, String comment) throws InterruptedException {

        selectCustomerStatus(status).selectCustomerType(type).selectCustomerClass(accClass).selectStore(store).
                enterTaxDetails(flag).addDeliveryCharge(deliveryFlag, charge).enterCustomerDiscount(discount).
                enterWireOutDiscount(wireDiscount).enterCustomerReference(reference).enterComment(comment);
    }

    /*
     * Buttons
     */

    private final By saveAndContBtn = By.xpath("//span[contains(text(),'Save And Continue')]");
    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    public void clickSaveAndContButton() throws InterruptedException {
        SmallWait(2000);
        click_Element(saveAndContBtn);
    }

    public void clickSaveButton() {
        click_Element_Js(saveBtn);
    }
}