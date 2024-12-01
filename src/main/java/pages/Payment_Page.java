package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Payment_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Payment_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By paymentTab = By.xpath("//strong[normalize-space()='Payment']");

    private final By cardField = By.xpath("//input[@name='cardNumber']");

    private final By expirationField = By.xpath("//input[@name='cardExpiration']");

    private final By cvvField = By.xpath("//input[@name='cardCvv']");

    private final By promoField = By.xpath("//input[@name='PromoCode']");
    private final By applyPromoBtn = By.xpath("//button[@id='applypromocode']");

    private final By promoMessage = By.xpath("//div[contains(@class, 'message-')]");

    private final By termsField = By.cssSelector("#terms");

    private final By orderBtn = By.xpath("//button[@id='checkout-next-step-button']");

    private final By passwordField = By.cssSelector("#txtPassword");
    private final By passwordSubmitBtn = By.cssSelector("#submitPassword");

    private final By paymentFailedMessage = By.cssSelector("div[class='message-error validation-summary-errors'] ul li");

    private final By recurringHeader = By.xpath("//h2[normalize-space()='Recurring Order Summary']");
    private final By proratedHeader = By.xpath("//h2[normalize-space()='Current Order Summary Pro-Rated']");

    private final By recurringPackagePrice = By.xpath("//p[contains(normalize-space(), '/Monthly')]");
    private final By recurringLicensePrice = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[1]");
    private final By recurringSubtotal = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[2]");
    private final By promotionalDiscount = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[3]");
    private final By recurringMonthlyFee = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[4]");

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

    public String applyPromoAndGetMessage(String promo){
        write_Send_Keys(promoField, promo);
        click_Element(applyPromoBtn);

        return get_Text(promoMessage);
    }

    public void enterPaymentDetails(String cardNumber, String date, String cvv){
        enterCardNumber(cardNumber).enterExpirationDate(date).enterCVV(cvv).clickTermsBtn();
    }

    public void clickTermsBtn(){
        click_CheckBox(termsField);
    }

    public void clickSubmitOrderBtn() {
        click_Element(orderBtn);
    }

    public void enterPassword(String password){
        write_Send_Keys(passwordField, password);
    }
    public void clickConfirmBtn(){
        click_CheckBox(passwordSubmitBtn);
    }

    public void checkPaymentFailedMessage(){
        try {
            WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(paymentFailedMessage));

            if (message.isDisplayed()) {
                Assert.fail("Payment failed. Test stopped.");
            }
        }
        catch (Exception e) {
            logger.info("Payment failed message not found. Test continues.");
        }
    }

    public double fetchPackagePrice(){
        String text = get_Text(recurringPackagePrice);
        String priceText = text.replaceAll(".*\\$(\\d+\\.\\d+).*", "$1");

        return Double.parseDouble(priceText);
    }
    public double[] fetchLicenseDetails(){
        String text = get_Text(recurringLicensePrice);

        Pattern pattern = Pattern.compile("\\$(\\d+\\.\\d+).*\\$(\\d+\\.\\d+).*\\*\\s*(\\d+)\\s*users");
        Matcher matcher = pattern.matcher(text);

        double[] priceDetails = new double[3];

        if (matcher.find()) {
            priceDetails[0] = Double.parseDouble(matcher.group(1));
            priceDetails[1] = Double.parseDouble(matcher.group(2));
            priceDetails[2] = Integer.parseInt(matcher.group(3));
        }

        return priceDetails;
    }
    public double fetchSubtotal(){
        String text = get_Text(recurringSubtotal);
        String subTotalText = text.replaceAll(".*\\$(\\d+\\.\\d+).*", "$1");

        return Double.parseDouble(subTotalText);
    }
    public String[] fetchPromoDiscount(){
        String text = get_Text(promotionalDiscount);

        Pattern pattern = Pattern.compile("-\\$(\\d+\\.\\d+)\\s*\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(text);

        String[] priceAndPromo = new String[2];

        if (matcher.find()) {
            priceAndPromo[0] = matcher.group(1); //promoDiscount
            priceAndPromo[1] = matcher.group(2); //promoCode
        }

        return priceAndPromo;
    }
    public double fetchRecurringFee(){
        String text = get_Text(recurringMonthlyFee);
        String recurringFeeText = text.replaceAll(".*\\$(\\d+\\.\\d+).*", "$1");

        return Double.parseDouble(recurringFeeText);
    }
}
