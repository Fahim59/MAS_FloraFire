package pages.Settings.Delivery;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShortCode_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public ShortCode_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newCodeButton = By.xpath("//span[contains(text(),'Add New')]");

    public void clickNewCodeButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(newCodeButton);

        SmallWait(1000);
    }

    /*
     * Short Code
     */

    private final By codeField = By.xpath("//input[@formcontrolname='code']");

    private final By locationNameField = By.xpath("//input[@formcontrolname='name']");

    private final By addressField = By.xpath("//input[@formcontrolname='address1']");
    private final By addressContField = By.xpath("//input[@formcontrolname='address2']");

    private final By countryField = By.xpath("//input[@formcontrolname='countryId']");
    private final By stateField = By.xpath("//input[@formcontrolname='stateProvinceId']");
    private final By cityField = By.xpath("//input[@formcontrolname='city']");
    private final By zipField = By.xpath("//input[@formcontrolname='zipCode']");

    private final By phoneField = By.xpath("//input[@formcontrolname='phoneNumber']");

    private final By specialInstructionField = By.xpath("//textarea[@formcontrolname='specialInstruction']");

    private final By storeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By deliveryTypeField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By deliveryZoneField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");

    private final By defaultDeliveryChargeField = By.xpath("//input[@type='checkbox']");

    private final By specialDeliveryChargeField = By.xpath("//input[@formcontrolname='specialDeliveryCharge']");

    public ShortCode_Page enterShortCode(String code) {
        write_Send_Keys(codeField, code);
        return this;
    }

    public ShortCode_Page enterLocationName(String location) {
        write_Send_Keys(locationNameField, location);
        return this;
    }

    public ShortCode_Page enterAddress(String address){
        write_Send_Keys(addressField, address);
        return this;
    }
    public ShortCode_Page enterAddressCont(String addressCont){
        write_Send_Keys(addressContField, addressCont);
        return this;
    }

    public ShortCode_Page selectCountry(String country) throws InterruptedException {
        click_Element(countryField);
        //write_Send_Keys(countryField, country);

        SmallWait(1500);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+country+"')]")));
        return this;
    }
    public ShortCode_Page selectState(String state) throws InterruptedException {
        SmallWait(1000);
        click_Element(stateField);
        SmallWait(500);
        write_Send_Keys(stateField, state);

        SmallWait(500);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+state+"')]")));
        return this;
    }

    public ShortCode_Page enterCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public ShortCode_Page enterZip(String zip){
        write_Send_Keys(zipField, zip);
        return this;
    }

    public ShortCode_Page enterPhone(String phone){
        write_Send_Keys(phoneField, phone);
        return this;
    }

    public ShortCode_Page enterSpecialInstruction(String instruction) {
        write_Send_Keys(specialInstructionField, instruction);
        return this;
    }

    public ShortCode_Page selectStore(String store) throws InterruptedException {
        click_Element(storeField);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+store+"')]")));

        return this;
    }

    public ShortCode_Page selectDeliveryType(String type) throws InterruptedException {
        click_Element(deliveryTypeField);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+type+"')]")));

        return this;
    }

    public ShortCode_Page selectDeliveryZone(String zone) throws InterruptedException {
        click_Element(deliveryZoneField);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+zone+"')]")));

        return this;
    }

    public ShortCode_Page defaultDeliveryCharge(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(defaultDeliveryChargeField);
        }
        return this;
    }

    public void enterSpecialDeliveryCharge(String flag, String charge) {
        if(flag.equalsIgnoreCase("No")){
            write_Send_Keys(specialDeliveryChargeField, charge);
        }
    }

    public void enterDeliveryShortCodeDetails(String code, String location, String address, String addressCont, String country,
                                              String state, String city, String zip, String phone, String instruction, String store,
                                              String type, String zone, String flag, String charge) throws InterruptedException {

        enterShortCode(code).enterLocationName(location).enterAddress(address).enterAddressCont(addressCont).selectCountry(country).
                selectState(state).enterCity(city).enterZip(zip).enterPhone(phone).enterSpecialInstruction(instruction).
                selectStore(store).selectDeliveryType(type).selectDeliveryZone(zone).defaultDeliveryCharge(flag).
                enterSpecialDeliveryCharge(flag, charge);
    }

    /*
     * Button and Message
     */

    private final By validateBtn = By.xpath("//span[contains(text(),'Validate')]");

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    private final By suggestionText = By.xpath("//div[@class='cdk-overlay-container']//li[1]");

    public String getValidationMessage(){
        return get_Text(successMessage);
    }

    public void clickValidateButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(validateBtn);

        SmallWait(2000);
    }

    public void clickSaveButton() throws InterruptedException {
        click_Element_Js(saveBtn);
        SmallWait(2000);
    }

    public void clickSuggestionText() throws InterruptedException {
        SmallWait(1000);
        click_Element(suggestionText);
    }

    private final String codeTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(codeTable+"/tr");

    public void verifyDeliveryShortCodeAddition(String code) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String deliveryCode = driver.findElement(By.xpath(codeTable+ "/tr["+l+"]/td[2]")).getText();

            if(deliveryCode.equalsIgnoreCase(code)){
                logger.info("Delivery Code {} found", code);
                break;
            }
        }
    }
}