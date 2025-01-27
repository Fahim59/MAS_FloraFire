package pages.Settings.Delivery;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Calendar_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Calendar_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final String calendarTable = "//table/tbody";
    private final By rows = By.xpath(calendarTable+"/tr");

    private final By deliveryModeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    public void saveDeliveryMoodOnCalendar(String date, String mode) throws InterruptedException {
        SmallWait(1500);

        outerLoop:
        for(int l = 1; l<= get_Size(rows); l++){

            int values = driver.findElements(By.xpath(calendarTable+ "/tr["+l+"]/td/div[2]")).size();

            for (int j = 1; j <= values; j++) {

                WebElement dateField = driver.findElement(By.xpath(calendarTable+ "/tr["+l+"]/td["+j+"]/div[1]"));
                WebElement dateTextField = driver.findElement(By.xpath(calendarTable+ "/tr["+l+"]/td["+j+"]/div[2]"));
                String dateText = dateTextField.getText();

                if(dateText.equalsIgnoreCase(date)){
                    dateField.click();

                    SmallWait(1000);
                    selectDeliveryMode(mode);

                    SmallWait(2000);

                    WebElement deliveryModeText = driver.findElement(By.xpath(calendarTable+ "/tr["+l+"]/td["+j+"]/div[3]/div/span"));
                    Assert.assertEquals(mode, deliveryModeText.getText());

                    logger.info("Date {} found on the calendar", date);
                    break outerLoop;
                }
                else{
                    logger.info("Date {} not found on the calendar", date);
                }
            }
        }
    }

    public Calendar_Page selectDelivery_Mode(String mode) throws InterruptedException {
        click_Element(deliveryModeField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()='"+mode+"']")));

        return this;
    }

    public void selectDeliveryMode(String mode) throws InterruptedException {
        selectDelivery_Mode(mode).clickSaveButton();
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element_Js(saveBtn);
    }
}