package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CustomerMaintenance_Page;

import java.time.Duration;

public class EmployeeSettings_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public EmployeeSettings_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By Field = By.xpath("");

    private final By autoCheckoutField = By.xpath("//input[@formcontrolname='autoCheckOut']");

    private final By autoLogoffField = By.xpath("//input[@formcontrolname='autoLogOff']");

    private final By dailyOverTimeField = By.xpath("//input[@formcontrolname='dailyOvertimeThreshold']");

    private final By weeklyOverTimeField = By.xpath("//input[@formcontrolname='weeklyOvertimeThreshold']");

    private final By clockTypeField = By.xpath("//input[@type='radio']");

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    private final By payrollTab = By.xpath("(//span[contains(text(),'Payroll')])[2]");

    private final By firstPayrollField = By.xpath("//input[@formcontrolname='firstPayrollOfYear']");

    private final By payrollFrequencyField = By.xpath("(//span[contains(@class,'mat-mdc-select-value')])[2]");

    public EmployeeSettings_Page enterAutoCheckoutHour(String hour){
        write_Send_Keys(autoCheckoutField, hour);
        return this;
    }

    public EmployeeSettings_Page enterAutoLogoffHour(String minute){
        write_Send_Keys(autoLogoffField, minute);
        return this;
    }

    public EmployeeSettings_Page selectClockType(String type) {
        if(type.equalsIgnoreCase("12H")){
            click_Radio_Element(clockTypeField, "0");
        }
        else{
            click_Radio_Element(clockTypeField, "1");
        }

        return this;
    }

    public EmployeeSettings_Page enterDailyOvertimeThreshold(String hours){
        write_Send_Keys(dailyOverTimeField, hours);
        return this;
    }

    public EmployeeSettings_Page enterWeeklyOvertimeThreshold(String hours){
        write_Send_Keys(weeklyOverTimeField, hours);
        return this;
    }

    public void enterTimeClockData(String hour, String minute, String type, String dailyHour, String weeklyHour){
        enterAutoCheckoutHour(hour).enterAutoLogoffHour(minute).selectClockType(type).
                enterDailyOvertimeThreshold(dailyHour).enterWeeklyOvertimeThreshold(weeklyHour).clickSaveButton();
    }

    public void clickSaveButton() {
        click_Element_Js(saveBtn);
    }

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public void clickPayrollTab() throws InterruptedException {
        click_Element_Js(payrollTab);
        SmallWait(2000);
    }

    public EmployeeSettings_Page enterFirstPayrollDate(String date) throws InterruptedException {
        SmallWait(200);

        WebElement element = wait_for_presence(firstPayrollField);
        js.executeScript("arguments[0].value = '';", element);
        element.sendKeys(date);

        return this;
    }

    public EmployeeSettings_Page selectPayrollFrequency(String frequency) throws InterruptedException {
        SmallWait(500);
        click_Element(payrollFrequencyField);

        SmallWait(500);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(text(),'"+frequency+"')]")));
        return this;
    }

    public void enterPayrollData(String date, String frequency) throws InterruptedException {
        enterFirstPayrollDate(date).selectPayrollFrequency(frequency).clickSaveButton();
    }
}