package pages.Settings.Delivery;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShortCode_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public ShortCode_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By Field = By.xpath("");
}