package pages.Settings.Delivery;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public void enterDeliveryModeData(String name) throws InterruptedException {
        enterModeName(name).clickSaveButton();
    }

    /*
     * Delivery Slot
     */

    private final By addDeliverySlotButton = By.xpath("(//span[contains(text(),'Add Delivery Slot')])[1]");

    private final By slotNameField = By.xpath("//input[@formcontrolname='name']");

    private final By slotSaveBtn = By.xpath("(//span[contains(text(),'Save')])[2]");

    private final String slotTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(slotTable+"/tr");

    private final By zoneField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");
    private final By slotTimeField = By.xpath("//input[@placeholder='HH:MM']");
    private final By amPmField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");

    public DeliveryMode_Page clickAddSlotButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(addDeliverySlotButton);

        SmallWait(2000);

        return this;
    }

    public DeliveryMode_Page enterSlotName(String name) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(slotNameField, name);
        return this;
    }

    public DeliveryMode_Page clickSlotSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(slotSaveBtn);

        SmallWait(2000);

        return this;
    }

    public void selectDeliveryZone(String... zoneOptions) throws InterruptedException {
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
    }

    public void enterSlotTime(String hours, String minute) {
        String time = hours + ":" + minute;
        write_Send_Keys(slotTimeField, time);
    }
    public void selectAmPm(String text) throws InterruptedException {
        if(!get_Text(amPmField).equals(text)){
            click_Element(amPmField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+text+"']")));
        }
    }

    public void enterSlotDetails(String slotName, String hours, String minute, String text, String... zoneOptions) throws InterruptedException {
        SmallWait(2000);
        for(int l = 1; l<= get_Size(rows); l++){

            String name = driver.findElement(By.xpath(slotTable+ "/tr["+l+"]/td[1]")).getText();
            WebElement actionButton = driver.findElement(By.xpath("(//span[contains(text(),'Action')])["+l+"]"));

            if(name.equalsIgnoreCase(slotName)){
                actionButton.click();

                WebElement configButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(text(),'Configure')])")));
                configButton.click();

                SmallWait(1000);

                selectDeliveryZone(zoneOptions);
                enterSlotTime(hours, minute);
                selectAmPm(text);
                clickSaveButton();

                logger.info("Slot {} found", slotName);
                break;
            }
            else{
                System.out.println("Slot Not Found");
            }
        }
    }

    public void enterDeliverySlotData(String name) throws InterruptedException {
        clickAddSlotButton().enterSlotName(name).clickSlotSaveButton();
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