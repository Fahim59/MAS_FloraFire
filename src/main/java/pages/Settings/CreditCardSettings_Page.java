package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreditCardSettings_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public CreditCardSettings_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By publicKeyField = By.xpath("//input[@formcontrolname='publicKey']");

    private final By secretKeyField = By.xpath("//input[@formcontrolname='secretKey']");

    private final By developerIdField = By.xpath("//input[@formcontrolname='developerId']");
    private final By versionNumberField = By.xpath("//input[@formcontrolname='versionNumber']");

    private final By serviceUrlField = By.xpath("//input[@formcontrolname='serviceUrl']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public CreditCardSettings_Page enterPublicKey(String publicKey){
        write_Send_Keys(publicKeyField, publicKey);
        return this;
    }

    public CreditCardSettings_Page enterSecretKey(String secretKey){
        write_Send_Keys(secretKeyField, secretKey);
        return this;
    }

    public CreditCardSettings_Page enterDeveloperId(String devId){
        write_Send_Keys(developerIdField, devId);
        return this;
    }
    public CreditCardSettings_Page enterVersionNumber(String version){
        write_Send_Keys(versionNumberField, version);
        return this;
    }

    public CreditCardSettings_Page enterServiceUrl(String url){
        write_Send_Keys(serviceUrlField, url);
        return this;
    }

    public void enterCreditCardSettings(String publicKey, String secretKey, String devId, String version, String url) throws InterruptedException {
        enterPublicKey(publicKey).enterSecretKey(secretKey).enterDeveloperId(devId).enterVersionNumber(version).
                enterServiceUrl(url).clickSaveButton();
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element_Js(saveBtn);
    }

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }
}