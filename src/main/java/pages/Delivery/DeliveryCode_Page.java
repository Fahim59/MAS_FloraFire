package pages.Delivery;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductMaintenance_Page;

import java.time.Duration;

public class DeliveryCode_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public DeliveryCode_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newDeliveryCodeButton = By.xpath("//span[contains(text(),'New Delivery Code')]");

    public void clickNewDeliveryCodeButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(newDeliveryCodeButton);
    }

    /*
     * Delivery Code
     */

    private final By codeField = By.xpath("//input[@formcontrolname='code']");
    private final By departmentResponsibleField = By.xpath("//input[@formcontrolname='departmentResponse']");
    private final By shortDescriptionField = By.xpath("//input[@formcontrolname='shortDescription']");
    private final By phoneSystemCodeField = By.xpath("//input[@formcontrolname='phoneSystemCode']");
    private final By longDescriptionField = By.xpath("//input[@formcontrolname='longDescription']");
    private final By phoneSystemMsgCodeField = By.xpath("//input[@formcontrolname='phoneSystemMsgCode']");

    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By wireServiceMessage1Field = By.xpath("//input[@formcontrolname='wsmCode1']");

    private final By sendToProblemBoxField = By.xpath("(//input[contains(@id,'mat-radio')])[1]");
    private final By wireServiceMessage2Field = By.xpath("//input[@formcontrolname='wsmCode2']");

    private final By forceSignEntryField = By.xpath("(//input[contains(@id,'mat-radio')])[2]");
    private final By emailFileNameField = By.xpath("//input[@formcontrolname='emailFileName']");

    private final By leftAtAddressField = By.xpath("(//input[contains(@id,'mat-radio')])[3]");
    private final By emailSubjectField = By.xpath("//input[@formcontrolname='emailSubject']");

    public DeliveryCode_Page enterDeliveryCode(String code) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(codeField, code);
        return this;
    }
    public DeliveryCode_Page enterResponsibleDepartment(String department) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(departmentResponsibleField, department);
        return this;
    }

    public DeliveryCode_Page enterShortDescription(String description) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(shortDescriptionField, description);
        return this;
    }
    public DeliveryCode_Page enterPhoneSystemCode(String phoneCode) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(phoneSystemCodeField, phoneCode);
        return this;
    }

    public DeliveryCode_Page enterLongDescription(String longDescription) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(longDescriptionField, longDescription);
        return this;
    }
    public DeliveryCode_Page enterPhoneSystemMessageCode(String messageCode) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(phoneSystemMsgCodeField, messageCode);
        return this;
    }

    public DeliveryCode_Page selectStatusCategory(String category) throws InterruptedException {
        click_Element(statusField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+category+"', ' ')]")));

        return this;
    }
    public DeliveryCode_Page enterWireServiceMessageOne(String msg1) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(wireServiceMessage1Field, msg1);
        return this;
    }

    public DeliveryCode_Page clickSendToProblemBox(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            click_Radio_Element(sendToProblemBoxField, "true");
        }
        else {
            click_Radio_Element(sendToProblemBoxField, "false");
        }
        return this;
    }
    public DeliveryCode_Page enterWireServiceMessageTwo(String msg2) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(wireServiceMessage2Field, msg2);
        return this;
    }

    public DeliveryCode_Page clickForceSignEntry(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            click_Radio_Element(forceSignEntryField, "true");
        }
        else {
            click_Radio_Element(forceSignEntryField, "false");
        }
        return this;
    }
    public DeliveryCode_Page enterEmailFileName(String file) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(emailFileNameField, file);
        return this;
    }

    public DeliveryCode_Page clickLeftAddressEntry(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            click_Radio_Element(leftAtAddressField, "true");
        }
        else {
            click_Radio_Element(leftAtAddressField, "false");
        }
        return this;
    }
    public DeliveryCode_Page enterEmailSubject(String subject) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(emailSubjectField, subject);
        return this;
    }
}