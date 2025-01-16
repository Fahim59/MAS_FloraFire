package pages.Delivery;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    private final By codeField = By.xpath("(//input[@formcontrolname='code'])[2]");
    private final By departmentResponsibleField = By.xpath("//input[@formcontrolname='departmentResponse']");
    private final By shortDescriptionField = By.xpath("//input[@formcontrolname='shortDescription']");
    private final By phoneSystemCodeField = By.xpath("//input[@formcontrolname='phoneSystemCode']");
    private final By longDescriptionField = By.xpath("//input[@formcontrolname='longDescription']");
    private final By phoneSystemMsgCodeField = By.xpath("//input[@formcontrolname='phoneSystemMsgCode']");

    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By wireServiceMessage1Field = By.xpath("//input[@formcontrolname='wsmCode1']");

    private final By sendToProblemBoxField = By.xpath("//mat-radio-group[@formcontrolname='sendProblemBox']//input[contains(@name, 'mat-radio-group')]");
    private final By wireServiceMessage2Field = By.xpath("//input[@formcontrolname='wsmCode2']");

    private final By forceSignEntryField = By.xpath("//mat-radio-group[@formcontrolname='forceSignByEntry']//input[contains(@name, 'mat-radio-group')]");
    private final By emailFileNameField = By.xpath("//input[@formcontrolname='emailFileName']");

    private final By leftAtAddressField = By.xpath("//mat-radio-group[@formcontrolname='leftAtAddressEntry']//input[contains(@name, 'mat-radio-group')]");
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

    public DeliveryCode_Page selectStatusCategory(String status) throws InterruptedException {
        click_Element(statusField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+status+"', ' ')]")));

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

    public void enterDeliveryCodeDetails(String code, String department, String description, String phoneCode, String longDescription,
                                         String messageCode, String status, String msg1, String flag, String msg2,
                                         String flag2, String file, String flag3, String subject) throws InterruptedException {

        enterDeliveryCode(code).enterResponsibleDepartment(department).enterShortDescription(description).enterPhoneSystemCode(phoneCode).
                enterLongDescription(longDescription).enterPhoneSystemMessageCode(messageCode).selectStatusCategory(status).
                enterWireServiceMessageOne(msg1).clickSendToProblemBox(flag).enterWireServiceMessageTwo(msg2).clickForceSignEntry(flag2).
                enterEmailFileName(file).clickLeftAddressEntry(flag3).enterEmailSubject(subject).clickSaveButton();
    }

    /*
     * Button and Message
     */

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);

        SmallWait(2000);
    }

    private final String codeTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(codeTable+"/tr");

    public void verifyDeliveryCodeAddition(String code) throws InterruptedException {
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