package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderEntry_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public OrderEntry_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By Field = By.xpath("");

    private final By searchProductField = By.xpath("(.//*[@placeholder='Search Keyword (Product Name, Code)'])[1]");
    private final By searchProductIconField = By.xpath("(.//mat-icon[text()='search'])[1]");

    private final By searchProductField2 = By.xpath("(.//*[@placeholder='Search Keyword (Product Name, Code or Description)'])[1]");
    private final By sortProductDropdown = By.xpath("(//div[contains(@id,'mat-select-value')])[7]");

    private final By productCode = By.xpath("(.//div[@class='info text-truncate']/span)[1]");
    private final By productQuantity = By.xpath("(.//div[@class='stock ng-star-inserted'])[1]");
    private final By addToOrderBtn = By.xpath("//span[contains(text(),'Add To Order')]");

    private final By searchCustomerIconField = By.xpath("(.//mat-icon[text()='search'])[2]");
}