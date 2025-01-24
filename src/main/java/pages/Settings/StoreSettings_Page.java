package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductMaintenance_Page;

import java.time.Duration;

public class StoreSettings_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public StoreSettings_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By managerField = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");

    private final By salesTaxField = By.xpath("//input[@formcontrolname='salesTax']");

    private final By isTrackInventoryField = By.xpath("//mat-radio-group[@formcontrolname='isTrackInventory']//input[contains(@name, 'mat-radio-group')]");

    private final By timeFormatField = By.xpath("//mat-radio-group[@formcontrolname='timeFormateId']//input[contains(@name, 'mat-radio-group')]");

    private final By dateFormatField = By.xpath("//mat-radio-group[@formcontrolname='dateTimeFormate']//input[contains(@name, 'mat-radio-group')]");

    private final By facebookField = By.xpath("//input[@formcontrolname='facebookUrl']");
    private final By twitterField = By.xpath("//input[@formcontrolname='twitterUrl']");
    private final By pinterestField = By.xpath("//input[@formcontrolname='pinterestUrl']");

    private final By imageUploadField = By.xpath("//input[@type='file']");

    public StoreSettings_Page selectManager(String name) throws InterruptedException {
        click_Element(managerField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+name+"')]")));

        return this;
    }

    public StoreSettings_Page enterSalesTax(String tax){
        write_Send_Keys(salesTaxField, tax);
        return this;
    }

    public StoreSettings_Page clickIsTrackInventory(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            click_Radio_Element(isTrackInventoryField, "true");
        }
        else {
            click_Radio_Element(isTrackInventoryField, "false");
        }
        return this;
    }

    public StoreSettings_Page selectTimeFormat(String timeFormat) {
        if(timeFormat.equalsIgnoreCase("12")){
            click_Radio_Element(timeFormatField, "12");
        }
        else {
            click_Radio_Element(timeFormatField, "24");
        }
        return this;
    }

    public StoreSettings_Page selectDateFormat(String dateFormat) {
        if(dateFormat.equalsIgnoreCase("MM/DD/YYYY")){
            click_Radio_Element(dateFormatField, "MM/DD/YYYY");
        }
        else {
            click_Radio_Element(dateFormatField, "YYYY/MM/DD");
        }
        return this;
    }

    public StoreSettings_Page enterFacebookUrl(String fbUrl){
        write_Send_Keys(facebookField, fbUrl);
        return this;
    }
    public StoreSettings_Page enterTwitterUrl(String twitterUrl){
        write_Send_Keys(twitterField, twitterUrl);
        return this;
    }
    public StoreSettings_Page enterPinterestUrl(String pinterestUrl){
        write_Send_Keys(pinterestField, pinterestUrl);
        return this;
    }

    public void uploadStoreImage(String fileName) throws InterruptedException {
        SmallWait(1000);
        upload_file(imageUploadField, fileName);
        SmallWait(2000);
    }

    public void enterStoreDetails(String name, String tax, String flag, String timeFormat, String dateFormat, String fbUrl,
                                  String twitterUrl, String pinterestUrl, String fileName) throws InterruptedException {

        selectManager(name).enterSalesTax(tax).clickIsTrackInventory(flag).selectTimeFormat(timeFormat).selectDateFormat(dateFormat).
                enterFacebookUrl(fbUrl).enterTwitterUrl(twitterUrl).enterPinterestUrl(pinterestUrl).uploadStoreImage(fileName);
    }
}