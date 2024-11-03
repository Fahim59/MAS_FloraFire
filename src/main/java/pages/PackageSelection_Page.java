package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PackageSelection_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public PackageSelection_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By packageTab = By.xpath("//strong[normalize-space()='Package']");

    private final By packageSelectionBox = By.cssSelector("input[type='radio']");

    private final By saveBtn = By.xpath("//button[normalize-space()='Save And Next']");

    public void selectPackage(String expectedTooltip) throws InterruptedException {
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(saveBtn));

        List<WebElement> packageLists = wait_for_presence_list(packageSelectionBox);

        for (WebElement tryNow : packageLists) {
            actions.moveToElement(tryNow).perform();

            SmallWait(500);

            WebElement packageTooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ui-tooltip-content']")));
            String packageTooltipText = packageTooltip.getText();

            if (packageTooltipText != null && packageTooltipText.contains(expectedTooltip)) {
                tryNow.click();
                break;
            }
        }
    }

    public void clickSaveBtn() { click_Element(saveBtn); }

    public void clickPackageTab() { click_Element(packageTab); }
}
