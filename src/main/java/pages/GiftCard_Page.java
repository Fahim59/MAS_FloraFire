package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GiftCard_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public GiftCard_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newGiftCardButton = By.xpath("//span[contains(text(),'New Gift Card')]");

    private final By typeField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");
    private final By numberField = By.xpath("//input[@formcontrolname='cardNumber']");

    private final By reasonField = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");
    private final By expiryField = By.xpath("//input[@formcontrolname='expirationDate']");

    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[6]");
    private final By balanceField = By.xpath("//input[@formcontrolname='balance']");

    private final By customerField = By.xpath("//input[@formcontrolname='customerName']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final String giftCardTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(giftCardTable+"/tr");

    public void clickNewGiftCardButton() throws InterruptedException {
        SmallWait(2000);
        click_Element(newGiftCardButton);
    }

    public GiftCard_Page selectGiftCardType(String type) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(typeField).equals(type)){
            click_Element(typeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+type+"', ' ')]")));
        }

        return this;
    }
    public GiftCard_Page enterGiftCardNumber(String number){
        write_Send_Keys(numberField, number);

        return this;
    }

    public GiftCard_Page selectGiftCardReason(String reason) throws InterruptedException {
        SmallWait(500);

        if(!get_Text(reasonField).equals(reason)){
            click_Element(reasonField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+reason+"', ' ')]")));
        }

        return this;
    }
    public GiftCard_Page enterGiftCardExpiryDate(String date){
        write_Send_Keys(expiryField, date);

        return this;
    }

    public GiftCard_Page selectGiftCardStatus(String status) throws InterruptedException {
        SmallWait(500);

        if(!get_Text(statusField).equals(status)){
            click_Element(statusField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+status+"', ' ')]")));
        }

        return this;
    }
    public GiftCard_Page enterGiftCardBalance(String balance){
        write_Send_Keys(balanceField, balance);

        return this;
    }

    public GiftCard_Page enterCustomerName(String name){
        if(!name.isEmpty()){
            write_Send_Keys(customerField, name);
        }

        return this;
    }

    public void enterGiftCardDetails(String type, String number, String reason, String date, String status,
                                     String balance, String name) throws InterruptedException {

        selectGiftCardType(type).enterGiftCardNumber(number).selectGiftCardReason(reason).enterGiftCardExpiryDate(date).
                selectGiftCardStatus(status).enterGiftCardBalance(balance).enterCustomerName(name).clickSaveButton();
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);
    }

    public void verifyGiftCardAddition(String code) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String cardNumber = driver.findElement(By.xpath(giftCardTable+ "/tr["+l+"]/td[3]")).getText();

            if(cardNumber.equalsIgnoreCase(code)){
                logger.info("Gift Card {} found", code);
                break;
            }
        }
    }
}