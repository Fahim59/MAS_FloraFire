package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Payment_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Payment_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By paymentTab = By.xpath("//strong[normalize-space()='Payment']");

    private final By cardField = By.xpath("//input[@name='cardNumber']");

    private final By expirationField = By.xpath("//input[@name='cardExpiration']");

    private final By cvvField = By.xpath("//input[@name='cardCvv']");

    private final By promoField = By.xpath("//input[@name='PromoCode']");
    private final By applyPromoBtn = By.xpath("//button[@id='applypromocode']");

    private final By promoSuccessMessage = By.cssSelector(".message-success");

    private final By termsField = By.cssSelector("#terms");

    private final By orderBtn = By.xpath("//button[@id='checkout-next-step-button']");

    public void clickPaymentTab() { click_Element(paymentTab); }

    public Payment_Page enterCardNumber(String cardNumber){
        driver.switchTo().frame("heartland-frame-cardNumber");
        write_Send_Keys(cardField, cardNumber);
        driver.switchTo().defaultContent();
        return this;
    }
    public Payment_Page enterExpirationDate(String date){
        driver.switchTo().frame("heartland-frame-cardExpiration");
        write_Send_Keys(expirationField, date);
        driver.switchTo().defaultContent();
        return this;
    }
    public Payment_Page enterCVV(String cvv){
        driver.switchTo().frame("heartland-frame-cardCvv");
        write_Send_Keys(cvvField, cvv);
        driver.switchTo().defaultContent();
        return this;
    }

    public Payment_Page applyPromo(String promo, String message){
        write_Send_Keys(promoField, promo);
        click_Element(applyPromoBtn);
        Assert.assertEquals(message, get_Text(promoSuccessMessage));
        return this;
    }

    public Payment_Page enterPaymentDetails(String cardNumber, String date, String cvv){
        return enterCardNumber(cardNumber).enterExpirationDate(date).enterCVV(cvv).clickTermsBtn();
    }

    public Payment_Page clickTermsBtn(){
        click_CheckBox(termsField);
        return this;
    }

    public void clickSubmitOrderBtn() {
        click_Element(orderBtn);
    }
}
