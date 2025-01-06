package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Employee_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Employee_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By Field = By.xpath("");

    private final By newEmployeeButton = By.xpath("//span[contains(text(),'New Employee')]");

    public void clickNewEmployeeButton() throws InterruptedException {
        SmallWait(2000);
        click_Element(newEmployeeButton);
    }

    /*
     * Employee Personal Details
     */

    private final By firstNameField = By.xpath("//input[@formcontrolname='firstName']");
    private final By lastNameField = By.xpath("//input[@formcontrolname='lastName']");

    private final By addressField = By.xpath("//input[@formcontrolname='address1']");
    private final By addressContField = By.xpath("//input[@formcontrolname='address2']");
    private final By countryField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By stateField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By cityField = By.xpath("//input[@formcontrolname='city']");
    private final By zipField = By.xpath("//input[@formcontrolname='zipcode']");

    private final By phoneTypeField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");
    private final By phoneField = By.xpath("//input[@formcontrolname='phoneNumber']");

    private final By emailField = By.xpath("//input[@formcontrolname='email']");

    private final By hireDateField = By.xpath("//input[@formcontrolname='hireDate']");

    private final By commentField = By.xpath("//textarea[@formcontrolname='comment']");

    public Employee_Page enterFirstName(String firstName) {
        write_Send_Keys(firstNameField, firstName);
        return this;
    }
    public Employee_Page enterLastName(String lastName) {
        write_Send_Keys(lastNameField, lastName);
        return this;
    }

    public Employee_Page enterAddress(String address){
        write_Send_Keys(addressField, address);
        return this;
    }
    public Employee_Page enterAddressCont(String address){
        write_Send_Keys(addressContField, address);
        return this;
    }
    public Employee_Page selectCountry(String country) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(countryField).equals(country)){
            click_Element(countryField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+country+"']")));
        }

        return this;
    }
    public Employee_Page selectState(String state) throws InterruptedException {
        SmallWait(1500);

        if(!get_Text(stateField).equals(state)){
            click_Element(stateField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+state+"']")));
        }

        return this;
    }
    public Employee_Page enterCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public Employee_Page enterZip(String zip){
        write_Send_Keys(zipField, zip);
        return this;
    }

    public Employee_Page selectPhoneType(String type) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(phoneTypeField).equals(type)){
            click_Element(phoneTypeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
        }

        return this;
    }
    public Employee_Page enterPhone(String phone){
        write_Send_Keys(phoneField, phone);
        return this;
    }

    public Employee_Page enterEmail(String email){
        write_Send_Keys(emailField, email);
        return this;
    }

    public Employee_Page enterHireDate(String date){
        write_Send_Keys(hireDateField, date);
        return this;
    }

    public void enterComment(String comment) {
        write_Send_Keys(commentField, comment);
    }

    public void enterEmployeePersonalDetails(String firstName, String lastName, String address, String address_cont, String country,
                                           String state, String city, String zip, String type, String phone, String email, String date, String comment) throws InterruptedException {

        enterFirstName(firstName).enterLastName(lastName).enterAddress(address).enterAddressCont(address_cont).selectCountry(country).
                selectState(state).enterCity(city).enterZip(zip).selectPhoneType(type).enterPhone(phone).enterEmail(email).
                enterHireDate(date).enterComment(comment);
    }

    /*
     * Employee Other Details
     */

    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");
    private final By roleField = By.xpath("(//div[contains(@id,'mat-select-value')])[6]");

    private final By storeField = By.xpath("(//div[contains(@id,'mat-select-value')])[7]");
    private final By departmentField = By.xpath("(//div[contains(@id,'mat-select-value')])[8]");

    private final By reviewField = By.xpath("//*[@class='mdc-checkbox__native-control' and @type='checkbox']");

    public Employee_Page selectStatus(String status) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(statusField).equals(status)){
            click_Element(statusField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+status+"']")));
        }

        return this;
    }
    public Employee_Page selectRole(String role) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(roleField).equals(role)){
            click_Element(roleField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+role+"']")));
        }

        return this;
    }

    public Employee_Page selectStore(String store) throws InterruptedException {
        if(!get_Text(storeField).contains(store)){
            click_Element(storeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+store+"')]")));
        }

        return this;
    }
    public Employee_Page selectDepartment(String department) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(departmentField).equals(department)){
            click_Element(departmentField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+department+"']")));
        }

        return this;
    }

    public void selectOrderReview(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(reviewField);
        }
    }

    public void enterEmployeeOtherDetails(String status, String role, String store, String department, String flag) throws InterruptedException {
        selectStatus(status).selectRole(role).selectStore(store).selectDepartment(department).selectOrderReview(flag);
    }

    /*
     * User Details
     */

    private final By usernameField = By.xpath("//input[@formcontrolname='userName']");

    private final By passField = By.xpath("//input[@formcontrolname='password']");
    private final By confirmPassField = By.xpath("//input[@formcontrolname='confirmPassword']");

    private final By empIdField = By.xpath("//input[@formcontrolname='employeeId']");

    private final By pinField = By.xpath("//input[@formcontrolname='pin']");
    private final By confirmPinField = By.xpath("//input[@formcontrolname='confirmPin']");

    private final By contactPersonField = By.xpath("//input[@formcontrolname='contactPersonName']");
    private final By contactPersonPhoneField = By.xpath("//input[@formcontrolname='contactPersonPhone']");
    private final By contactPersonRelationField = By.xpath("(//div[contains(@id,'mat-select-value')])[9]");

    public Employee_Page enterUserName(String uname){
        write_Send_Keys(usernameField, uname);
        return this;
    }

    public Employee_Page enterPassword(String pass){
        write_Send_Keys(passField, pass);
        return this;
    }
    public Employee_Page enterConfirmPassword(String cpass){
        write_Send_Keys(confirmPassField, cpass);
        return this;
    }

    public Employee_Page enterEmployeeId(String id){
        write_Send_Keys(empIdField, id);
        return this;
    }

    public Employee_Page enterPin(String pin){
        write_Send_Keys(pinField, pin);
        return this;
    }
    public Employee_Page enterConfirmPin(String cPin){
        write_Send_Keys(confirmPinField, cPin);
        return this;
    }

    public Employee_Page enterContactPersonName(String name){
        write_Send_Keys(contactPersonField, name);
        return this;
    }
    public Employee_Page enterContactPersonPhone(String phone){
        write_Send_Keys(contactPersonPhoneField, phone);
        return this;
    }
    public void selectRelation(String relation) throws InterruptedException {
        if(!get_Text(contactPersonRelationField).contains(relation)){
            click_Element(contactPersonRelationField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+relation+"')]")));
        }
    }

    public void enterUserDetails(String uname, String pass, String id, String pin,
                                 String name, String phone, String relation) throws InterruptedException {

        enterUserName(uname).enterPassword(pass).enterConfirmPassword(pass).enterEmployeeId(id).enterPin(pin).
                enterConfirmPin(pin).enterContactPersonName(name).enterContactPersonPhone(phone).selectRelation(relation);
    }
}