package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CustomerMaintenance_Page;

import java.time.Duration;

public class Vehicle_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Vehicle_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newVehicleButton = By.xpath("//span[contains(text(),'NewVehicle')]");

    private final By licensePlateField = By.xpath("//input[@formcontrolname='licensePlate']");
    private final By vinField = By.xpath("//input[@formcontrolname='vin']");
    private final By modelField = By.xpath("//input[@formcontrolname='model']");
    private final By statusField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By expireDateField = By.xpath("//input[@formcontrolname='expirationDate']");
    private final By maintenanceDueField = By.xpath("//input[@formcontrolname='maintenanceDue']");

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public void clickNewVehicleButton() {
        click_Element(newVehicleButton);
    }

    public Vehicle_Page enterLicensePlate(String license){
        write_Send_Keys(licensePlateField, license);
        return this;
    }
    public Vehicle_Page enterVIN(String vin) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(vinField, vin);
        return this;
    }
    public Vehicle_Page enterModel(String model) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(modelField, model);
        return this;
    }
    public Vehicle_Page selectStatus(String status) throws InterruptedException {
        SmallWait(1500);

        if(!get_Text(statusField).equals(status)){
            click_Element(statusField);
            SmallWait(500);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+status+"')]")));
        }
        return this;
    }
    public Vehicle_Page enterExpirationDate(String date) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(expireDateField, date);
        return this;
    }
    public Vehicle_Page enterMaintenanceDate(String date) throws InterruptedException {
        SmallWait(200);
        write_Send_Keys(maintenanceDueField, date);
        return this;
    }

    public void enterVehicleInformation(String license, String vin, String model, String status, String exDate, String mainDate) throws InterruptedException {
        enterLicensePlate(license).enterVIN(vin).enterModel(model).selectStatus(status).
                enterExpirationDate(exDate).enterMaintenanceDate(mainDate).clickSaveButton();
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

    public void verifyVehicleAddition(String shortcut) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String code = driver.findElement(By.xpath(vehicleTable+ "/tr["+l+"]/td[2]")).getText();

            if(code.equalsIgnoreCase(shortcut)){
                logger.info("Vehicle {} found", shortcut);
                break;
            }
            else{
                logger.info("Vehicle {} not found", shortcut);
            }
        }
    }
}