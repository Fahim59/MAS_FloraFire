package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MapOptionSettings_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public MapOptionSettings_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By apiKeyField = By.xpath("//input[@formcontrolname='apiKey']");

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    public MapOptionSettings_Page enterAPIKey(String key){
        write_Send_Keys(apiKeyField, key);
        return this;
    }

    public void clickSaveButton() {
        click_Element_Js(saveBtn);
    }

    public void enterAPIKeySettings(String key){
        enterAPIKey(key).clickSaveButton();
    }
}