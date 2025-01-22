package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Value_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Value_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newValueButton = By.xpath("//span[contains(text(),'New Value')]");

    private final By nameField = By.xpath("//*[contains(@formcontrolname,'name')]");
    private final By displayOrderField = By.xpath("//*[contains(@formcontrolname,'displayOrder')]");
    private final By statusField = By.xpath("//label[normalize-space()='Active']");

    private final By selectedField = By.xpath("//label[normalize-space()='Preselected']");

    private final By saveButton = By.xpath("//span[contains(text(),'Save')]");

    private final By valueTypeMessage = By.xpath("(.//*[text()='value type added successfully'])[1]");
    private final By valueMessage = By.xpath("(.//*[text()='value added successfully'])[1]");

    private final String valueTable = "//mat-table[@role='table']";
    private final By valueTr = By.xpath(valueTable+"/mat-row");

    private final By searchField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By detailsButton = By.xpath("//span[normalize-space()='Details']");
    private final By backButton = By.xpath("(//span[@class='mat-mdc-button-touch-target'])[1]");

    public void clickNewValueButton(){
        click_Element(newValueButton);
    }

    public Value_Page enterName(String name){
        write_Send_Keys(nameField, name);
        return this;
    }
    public Value_Page enterDisplayOrder(String order){
        write_Send_Keys(displayOrderField, order);
        return this;
    }
    public Value_Page selectStatus(){
        click_CheckBox(statusField);
        return this;
    }
    public Value_Page selectPreSelected(String flag){
        if(flag.equalsIgnoreCase("Yes")){
            click_CheckBox(selectedField);
        }
        return this;
    }
    public void clickSaveButton() throws InterruptedException {
        click_Element(saveButton);
        SmallWait(500);
    }

    public void enterValueDetails(String name, String order) throws InterruptedException {
        enterName(name).enterDisplayOrder(order).selectStatus().clickSaveButton();
    }

    public void enterValueListDetails(String name, String order, String preselected) throws InterruptedException {
        enterName(name).enterDisplayOrder(order).selectPreSelected(preselected).clickSaveButton();
    }

    public String getValueTypeSuccessMessage(){
        return get_Text(valueTypeMessage);
    }
    public String getValueSuccessMessage(){
        return get_Text(valueMessage);
    }

    public int getValueTableTrSize() {
        return get_Size(valueTr);
    }

    public void selectValue(String value) throws InterruptedException {
        click_Element(searchField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    public void clickDetailsButton(String valueName) throws InterruptedException {
        for(int l = 1; l<= getValueTableTrSize(); l++){

            String name = driver.findElement(By.xpath(valueTable+ "/mat-row["+l+"]/mat-cell[2]")).getText();
            WebElement actionButton = driver.findElement(By.xpath(valueTable+ "/mat-row["+l+"]/mat-cell[1]"));

            if(name.equalsIgnoreCase(valueName)){
                SmallWait(1000);

                wait.until(ExpectedConditions.elementToBeClickable(actionButton)).click();
                click_Element(detailsButton);

                break;
            }
            else{
                logger.info("Value does not exist");
            }
        }
    }

    public void clickBackButton() throws InterruptedException {
        click_Element(backButton);
        SmallWait(1000);
    }
}