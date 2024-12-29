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

    private final By Field = By.xpath("");

    private final By newCustomerButton = By.xpath("//span[contains(text(),'New Customer')]");

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

    private final By customerTypeField = By.xpath("//div[@id='mat-select-value-185']");

    private final By storeField = By.xpath("//div[@id='mat-select-value-191']");

    private final By taxYesField = By.xpath("//label[@for='mat-radio-92-input']");
    private final By taxNoField = By.xpath("//label[@for='mat-radio-93-input']");
    private final By taxNumberField = By.xpath("//input[@formcontrolname='taxCertificate']");

    private final By deliveryField = By.xpath("//*[@class='mdc-checkbox__native-control' and @type='checkbox']");
    private final By deliveryChargeField = By.xpath("//input[@formcontrolname='deliveryCharge']");

    private final By discountField = By.xpath("//input[@formcontrolname='discount']");

    private final By referenceField = By.xpath("//input[@formcontrolname='customerReference']");
    private final By commentField = By.xpath("//textarea[@formcontrolname='comment']");

    private final By newEmailButton = By.xpath("//span[contains(text(),'New Email')]");
    private final By emailField = By.xpath("//input[@formcontrolname='email']");
    private final By isPrimaryEmailField = By.xpath("//input[@id='mat-mdc-checkbox-23-input']");
    private final By saveEmailBtn = By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Save'])[2]");

    private final By newPhoneField = By.xpath("//span[contains(text(),'New Phone Number')]");
    private final By phoneField = By.xpath("//input[@formcontrolname='phoneNumber']");
    private final By isPrimaryPhoneField = By.xpath("//input[@id='mat-mdc-checkbox-26-input']");
    private final By savePhoneBtn = By.xpath("(//span[@class='mdc-button__label'][normalize-space()='Save'])[2]");

    private final By saveAndContBtn = By.xpath("//span[contains(text(),'Save And Continue')]");
    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    public void clickNewCustomerButton() { click_Element(newCustomerButton); }

    public void clickManualEntry() { click_Radio_Element(manualIdType, "false"); }
    public void clickAutoEntry() { click_Radio_Element(autoIdType, "true"); }

    public void enterId(String id){
        write_Send_Keys(customerIdField, id);
    }

    public CustomerMaintenance_Page enterCustomerId(String flag, String id){
        if(flag.equalsIgnoreCase("Yes")){
            clickAutoEntry();
        }
        else{
            clickManualEntry();
            enterId(id);
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
    public CustomerMaintenance_Page selectCustomerCountry(String country){
        write_Send_Keys(countryField, country);
        return this;
    }
    public CustomerMaintenance_Page selectCustomerState(String state){
        write_Send_Keys(stateField, state);
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
}