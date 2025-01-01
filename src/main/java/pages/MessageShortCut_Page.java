package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MessageShortCut_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public MessageShortCut_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newShortCutButton = By.xpath("//span[contains(text(),'New Message ShortCut')]");

    private final By shortCodeField = By.xpath("//input[@formcontrolname='shortCut']");

    private final By fullMessageField = By.xpath("//textarea[@formcontrolname='description']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public void clickNewShortCutButton() {
        click_Element(newShortCutButton);
    }

    public MessageShortCut_Page enterShortCode(String code){
        write_Send_Keys(shortCodeField, code);
        return this;
    }

    public MessageShortCut_Page enterFullMessage(String message) throws InterruptedException {
        SmallWait(500);
        write_Send_Keys(fullMessageField, message);
        return this;
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element_Js(saveBtn);
    }

    public void enterShortCode(String code, String message) throws InterruptedException {
        enterShortCode(code).enterFullMessage(message).clickSaveButton();
    }

    private final String shortcutTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(shortcutTable+"/tr");

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public void verifyShortCodeAddition(String shortcut) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String code = driver.findElement(By.xpath(shortcutTable+ "/tr["+l+"]/td[2]")).getText();

            if(code.equalsIgnoreCase(shortcut)){
                logger.info("Short Code {} found", shortcut);
                break;
            }
            else{
                logger.info("Short Code {} not found", shortcut);
            }
        }
    }
}