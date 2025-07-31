package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TransactionType_Page extends BaseClass{
    private final WebDriver driver;
    private final JavascriptExecutor js;

    public TransactionType_Page(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    private final By addButton = By.xpath("//span[contains(text(),'New Transaction Type')]");

    private final By codeField = By.xpath("//input[@formcontrolname='transactionCode']");
    private final By titleField = By.xpath("//input[@formcontrolname='title']");
    private final By isActiveField = By.xpath("//mat-checkbox[@formcontrolname='isActive']/div/div/input");
    private final By transactionTypeField = By.xpath("//mat-label[text()='Transaction Type']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public void clickAddButton() {
        click_Element(addButton);
    }

    public TransactionType_Page enterCode(String code){
        write_Send_Keys(codeField, code);
        return this;
    }
    public TransactionType_Page enterTitle(String title){
        write_Send_Keys(titleField, title);
        return this;
    }
    public TransactionType_Page isActive(String flag){
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(isActiveField);
        }
        return this;
    }
    public TransactionType_Page transactionTypeField(String type) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(transactionTypeField).equals(type)){
            click_Element(transactionTypeField);
            SmallWait(500);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+type+"')]")));
        }
        return this;
    }

    public void enterTransactionCodeDetails(String code, String title, String flag, String type) throws InterruptedException {
        enterCode(code).enterTitle(title).isActive(flag).transactionTypeField(type).clickSaveButton();
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element_Js(saveBtn);
    }

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    private final String transactionTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(transactionTable+"/tr");

    public void verifyTransactionCodeAddition(String termCode) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String code = driver.findElement(By.xpath(transactionTable+ "/tr["+l+"]/td[2]")).getText();

            if(code.equalsIgnoreCase(termCode)){
                logger.info("Transaction Code {} found", termCode);
                break;
            }
            else{
                logger.info("Transaction Code {} not found", termCode);
            }
        }
    }
}