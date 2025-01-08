package pages.Delivery;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DeliverySlot_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public DeliverySlot_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newSlotButton = By.xpath("//button[@aria-label='Add Delivery Slot']");

    public void clickNewSlotButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(newSlotButton);
    }

    /*
     * Delivery Slot
     */

    private final By slotNameField = By.xpath("//input[@formcontrolname='name']");

    private final By zoneField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");

    private final By slotTimeField = By.xpath("//input[@placeholder='HH:MM']");

    private final By amPmField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");

    public DeliverySlot_Page enterSlotName(String name) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(slotNameField, name);
        return this;
    }

    public DeliverySlot_Page selectDeliveryZone(String... zoneOptions) throws InterruptedException {
        click_Element(zoneField);
        SmallWait(200);

        try {
            for (String option : zoneOptions) {
                WebElement checkbox = driver.findElement(By.xpath("//span[text()=concat(' ', '"+option+"', ' ')]"));

                if (checkbox == null) {
                    throw new Exception("Invalid zone option: " +option);
                }

                if (!checkbox.isSelected()) {
                    js.executeScript("arguments[0].click();", checkbox);
                }
            }
            actions.sendKeys(Keys.TAB).perform();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return this;
    }

    public DeliverySlot_Page enterSlotTime(String hours, String minute) {
        String time = hours + ":" + minute;
        write_Send_Keys(slotTimeField, time);
        return this;
    }

    public void selectAmPm(String text) throws InterruptedException {
        if(!get_Text(amPmField).equals(text)){
            click_Element(amPmField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+text+"']")));
        }
    }

    public void enterDeliverySlotDetails(String name, String zone, String hours, String minute, String text) throws InterruptedException {
        enterSlotName(name).clickSaveButton().selectDeliveryZone(zone).enterSlotTime(hours, minute).selectAmPm(text);
    }

    /*
     * Button and Message
     */

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[2]");

    private final By finalSaveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public DeliverySlot_Page clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);

        SmallWait(1000);

        return this;
    }

    public void clickFinalSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(finalSaveBtn);

        SmallWait(1000);
    }
}