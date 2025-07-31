package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Configuration_Page extends BaseClass{
    private final WebDriver driver;
    private final JavascriptExecutor js;

    public Configuration_Page(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    private final By addButton = By.xpath("//span[contains(text(),'Add')]");

    private final By codeField = By.xpath("//input[@formcontrolname='code']");
    private final By descriptionField = By.xpath("//input[@formcontrolname='description']");
    private final By netDueDaysField = By.xpath("//input[@formcontrolname='netDueDays']");
    private final By agingBucketField = By.xpath("//mat-select[@formcontrolname='agingBucket']");
    private final By lateChargePercentageField = By.xpath("//input[@formcontrolname='lateChargePercentage']");
    private final By minLateChargeAmountField = By.xpath("//input[@formcontrolname='minimumLateChargeAmount']");
    private final By lateChargeStatementField = By.xpath("//input[@formcontrolname='lateChargeStatementDescription']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public void clickAddButton() {
        click_Element(addButton);
    }

    public Configuration_Page enterCode(String code){
        write_Send_Keys(codeField, code);
        return this;
    }
    public Configuration_Page enterDescription(String description){
        write_Send_Keys(descriptionField, description);
        return this;
    }
    public Configuration_Page enterNetDueDays(String netDueDays){
        write_Send_Keys(netDueDaysField, netDueDays);
        return this;
    }
    public Configuration_Page selectAgingBucket(String agingBucket) throws InterruptedException {
        SmallWait(1500);

        if(!get_Text(agingBucketField).equals(agingBucket)){
            click_Element(agingBucketField);
            SmallWait(500);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+agingBucket+"')]")));
        }
        return this;
    }
    public Configuration_Page enterLateChargePercentage(String lateChargePercentage){
        write_Send_Keys(lateChargePercentageField, lateChargePercentage);
        return this;
    }
    public Configuration_Page enterMinLateCharge(String minLateCharge) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(minLateChargeAmountField, minLateCharge);
        return this;
    }
    public Configuration_Page enterLateChargeStatement(String lateChargeStatement) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(lateChargeStatementField, lateChargeStatement);
        return this;
    }

    public void enterTermCodeDetails(String code, String description, String netDueDays, String agingBucket, String lateChargePercentage, String minLateCharge, String lateChargeStatement) throws InterruptedException {
        enterCode(code).enterDescription(description).enterNetDueDays(netDueDays).selectAgingBucket(agingBucket).enterLateChargePercentage(lateChargePercentage).
                enterMinLateCharge(minLateCharge).enterLateChargeStatement(lateChargeStatement).clickSaveButton();
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element_Js(saveBtn);
    }

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    private final String vehicleTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(vehicleTable+"/tr");

    public void verifyTermCodeAddition(String termCode) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String code = driver.findElement(By.xpath(vehicleTable+ "/tr["+l+"]/td[2]")).getText();

            if(code.equalsIgnoreCase(termCode)){
                logger.info("Term Code {} found", termCode);
                break;
            }
            else{
                logger.info("Term Code {} not found", termCode);
            }
        }
    }
}