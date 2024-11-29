package pages;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageSelection_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    private static final Logger logger = LogManager.getLogger(PackageSelection_Page.class);

    public PackageSelection_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By packageTab = By.xpath("//strong[normalize-space()='Package']");

    private final By packageSelectionBox = By.cssSelector("input[type='radio']");

    private final By saveBtn = By.xpath("//button[normalize-space()='Save And Next']");

    private final By packageDivText = By.xpath("(.//*[@class='card-description-item'])[1]");
    private final By trialStartDivText = By.xpath("(.//*[@class='card-description-item'])[2]");
    private final By trialEndDivText = By.xpath("(.//*[@class='card-description-item'])[3]");

    private final By subscriptionText = By.xpath("//div[contains(text(),'Your current subscription')]");

    private final By startPaidSubscriptionBtn = By.xpath("//button[@id='startPaidSubscriptionBtn']");
    private final By packageChangeBtn = By.xpath("//button[normalize-space()='Package Change']");
    private final By packageCancelBtn = By.xpath("//button[normalize-space()='Package Cancel']");

    public double selectPackageAndGetValue(String expectedTooltip) throws InterruptedException {
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(saveBtn));

        List<WebElement> packageLists = wait_for_presence_list(packageSelectionBox);
        double packageFee = 0.00;

        for (WebElement tryNow : packageLists) {
            actions.moveToElement(tryNow).perform();

            SmallWait(1000);

            WebElement packageTooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ui-tooltip-content']")));
            String packageTooltipText = packageTooltip.getText();

            if (packageTooltipText != null && packageTooltipText.contains(expectedTooltip)) {

                packageFee = Double.parseDouble(packageTooltipText.replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
                //System.out.println("Package Fee: "+packageFee);

                tryNow.click();
                break;
            }
        }
        return packageFee;
    }

    public void clickSaveBtn() { click_Element(saveBtn); }

    public void clickPackageTab() { click_Element(packageTab); }

    public String getPackageText(){
        return get_Text(packageDivText);
    }
    public String getTrialStartText(){
        return get_Text(trialStartDivText);
    }
    public String getTrialEndText(){
        return get_Text(trialEndDivText);
    }

    public String getSubscriptionText(){
        return get_Text(subscriptionText);
    }

    public void paidStartSubscriptionButtonVisibility(){
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(startPaidSubscriptionBtn));

            if (!button.isDisplayed()) {
                Assert.fail("Paid Subscription Button is not visible");
            }
        }
        catch (Exception e) {
            logger.info("Paid Subscription Button is not visible");
        }
    }
    public void packageChangeButtonVisibility(){
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(packageChangeBtn));

            if (!button.isDisplayed()) {
                Assert.fail("Package Change Button is not visible");
            }
        }
        catch (Exception e) {
            logger.info("Package Change Button is not visible");
        }
    }
    public void packageCancelButtonVisibility(){
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(packageCancelBtn));

            if (!button.isDisplayed()) {
                Assert.fail("Package Cancel Button is not visible");
            }
        }
        catch (Exception e) {
            logger.info("Package Cancel Button is not visible");
        }
    }
}
