package pages.Delivery;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DeliveryMode_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public DeliveryMode_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newModeButton = By.xpath("//button[@aria-label='Add Delivery Mode']");

    public void clickNewModeButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(newModeButton);
    }

    /*
     * Delivery Mode
     */

    private final By modeNameField = By.xpath("//input[@formcontrolname='name']");

    private final By slotField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");

    public DeliveryMode_Page enterModeName(String name) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(modeNameField, name);
        return this;
    }

    public void selectDeliverySlot(String... slotOptions) throws InterruptedException {
        click_Element(slotField);
        SmallWait(1000);

        try {
            for (String option : slotOptions) {
                WebElement checkbox = driver.findElement(By.xpath("//span[text()=concat(' ', '"+option+"', ' ')]"));

                if (checkbox == null) {
                    throw new Exception("Invalid slot option: " +option);
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
    }

    public void enterDeliveryModeDetails(String name, String... slotOptions) throws InterruptedException {
        enterModeName(name).clickSaveButton().selectDeliverySlot(slotOptions);
    }

    /*
     * Button and Message
     */

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[2]");

    private final By finalSaveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    public DeliveryMode_Page clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);

        SmallWait(2000);

        return this;
    }

    public void clickFinalSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(finalSaveBtn);

        SmallWait(1000);
    }
}