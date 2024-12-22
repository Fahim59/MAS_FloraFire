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

    private final By saveButton = By.xpath("//span[contains(text(),'Save')]");

    private final By message = By.xpath("(.//*[text()='value type added successfully'])[1]");

    private final String valueTable = "//mat-table[@role='table']";
    private final By valueTr = By.xpath(valueTable+"/mat-row");

    private final By detailsButton = By.xpath("//span[normalize-space()='Details']");

    private final By searchField = By.xpath("//div[@id='mat-select-value-3']");

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
    public void clickSaveButton(){
        click_Element(saveButton);
    }

    public void enterValueDetails(String name, String order){
        enterName(name).enterDisplayOrder(order).selectStatus().clickSaveButton();
    }

    public String getSuccessMessage(){
        return get_Text(message);
    }

    public void clickDetailsButton(String valueName) throws InterruptedException {
        System.out.println(get_Size(valueTr));

        for(int l = 1; l<= get_Size(valueTr); l++){

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

    public void selectValue(String value) throws InterruptedException {
        click_Element(searchField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }
}