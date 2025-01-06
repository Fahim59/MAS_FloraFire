package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DiscountMaintenance_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public DiscountMaintenance_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newDiscountButton = By.xpath("//span[contains(text(),'New Discount')]");

    private final By codeField = By.xpath("//input[@formcontrolname='discountCode']");
    private final By typeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");

    private final By amountField = By.xpath("//input[@formcontrolname='discountAmount']");
    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");

    private final By endDateField = By.xpath("//input[@formcontrolname='endDate']");

    private final By appliesOnField = By.xpath("//input[@type='radio']");

    private final By descriptionField = By.xpath("//textarea[@formcontrolname='description']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    private final String discountTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(discountTable+"/tr");

    public void clickNewDiscountButton() throws InterruptedException {
        SmallWait(2000);
        click_Element(newDiscountButton);
    }

    public DiscountMaintenance_Page enterDiscountCode(String code){
        write_Send_Keys(codeField, code);

        return this;
    }
    public DiscountMaintenance_Page selectDiscountType(String type) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(typeField).equals(type)){
            click_Element(typeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
        }

        return this;
    }

    public DiscountMaintenance_Page enterDiscountAmount(String amount){
        write_Send_Keys(amountField, amount);

        return this;
    }
    public DiscountMaintenance_Page selectDiscountStatus(String status) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(statusField).equals(status)){
            click_Element(statusField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+status+"']")));
        }

        return this;
    }

    public DiscountMaintenance_Page enterEndDate(String date){
        write_JS_Executor(endDateField, date);

        return this;
    }

    public DiscountMaintenance_Page selectAppliesOn(String applies) {
        if(applies.equalsIgnoreCase("Item")){
            click_Radio_Element(appliesOnField, "1");
        }
        else if(applies.equalsIgnoreCase("Order")){
            click_Radio_Element(appliesOnField, "2");
        }
        else {
            click_Radio_Element(appliesOnField, "3");
        }

        return this;
    }

    public DiscountMaintenance_Page enterDescription(String description){
        write_Send_Keys(descriptionField, description);

        return this;
    }

    public void enterDiscountDetails(String code, String type, String amount, String status, String date,
                                     String applies, String description) throws InterruptedException {

        enterDiscountCode(code).selectDiscountType(type).enterDiscountAmount(amount).selectDiscountStatus(status).
                enterEndDate(date).selectAppliesOn(applies).enterDescription(description).clickSaveButton();
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);
    }

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public void verifyDiscountAddition(String code) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String cardNumber = driver.findElement(By.xpath(discountTable+ "/tr["+l+"]/td[2]")).getText();

            if(cardNumber.equalsIgnoreCase(code)){
                logger.info("Discount {} found", code);
                break;
            }
        }
    }
}