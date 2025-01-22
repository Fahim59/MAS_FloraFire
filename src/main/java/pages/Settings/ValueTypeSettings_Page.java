package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ValueTypeSettings_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public ValueTypeSettings_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By customerStatusField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By accountClassField = By.xpath("(.//*[text()='Acct Class'])[1]");
    private final By referredByField = By.xpath("(.//*[text()='Referred By'])[1]");
    private final By accountManagerField = By.xpath("(.//*[text()='Acct Manager'])[1]");
    private final By invoicePaymentScheduleField = By.xpath("(.//*[text()='Invoice Payment Schedule'])[1]");
    private final By termField = By.xpath("(.//*[text()='Term'])[1]");
    private final By statementInvoiceCopyField = By.xpath("(.//*[text()='AR Statement Invoice Type'])[1]");
    private final By priceSheetCodeField = By.xpath("(.//*[text()='Price Sheet Code'])[1]");

    private final By productTypeField = By.xpath("(.//*[text()='Product Type'])[1]");
    private final By productDepartmentField = By.xpath("(.//*[text()='Department'])[1]");
    private final By careCodeField = By.xpath("(.//*[text()='Care Code'])[1]");

    private final By statusField = By.xpath("(.//*[text()='Status'])[2]");
    private final By employeeDepartmentField = By.xpath("(.//*[text()='Department'])[2]");
    private final By roleField = By.xpath("(.//*[text()='Role'])[1]");
    private final By contactPersonRelationField = By.xpath("(.//*[text()='Contact Person Relation'])[1]");

    private final By vehicleStatusField = By.xpath("(.//*[text()='Status'])[3]");

    private final By giftCardReasonField = By.xpath("(.//*[text()='Reason'])[1]");

    private final By tipField = By.xpath("(.//*[text()='Tip'])[1]");

    private final By saveBtn = By.xpath("//span[@class='mdc-button__label']");

    /*
     * setup of customer data
     */

    public ValueTypeSettings_Page selectCustomerStatus(String value) throws InterruptedException {
        click_Element(customerStatusField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectAccountClass(String value) throws InterruptedException {
        click_Element(accountClassField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectReferredBy(String value) throws InterruptedException {
        click_Element(referredByField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectAccountManager(String value) throws InterruptedException {
        click_Element(accountManagerField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectInvoicePaymentSchedule(String value) throws InterruptedException {
        click_Element(invoicePaymentScheduleField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectTerm(String value) throws InterruptedException {
        click_Element(termField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectStatementInvoiceCopy(String value) throws InterruptedException {
        click_Element(statementInvoiceCopyField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public void selectPriceSheetCode(String value) throws InterruptedException {
        click_Element(priceSheetCodeField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    public void setCustomerFields(String status, String accountClass, String referredBy, String manager, String invoicePayment,
                                  String term, String statementInvoice, String priceSheet) throws InterruptedException {

        selectCustomerStatus(status).selectAccountClass(accountClass).selectReferredBy(referredBy).selectAccountManager(manager).
                selectInvoicePaymentSchedule(invoicePayment).selectTerm(term).selectStatementInvoiceCopy(statementInvoice).selectPriceSheetCode(priceSheet);
    }

    /*
     * setup of product data
     */

    public ValueTypeSettings_Page selectProductType(String value) throws InterruptedException {
        click_Element(productTypeField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectProductDepartment(String value) throws InterruptedException {
        click_Element(productDepartmentField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public void selectCareCode(String value) throws InterruptedException {
        click_Element(careCodeField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    public void setProductFields(String type, String department, String careCode) throws InterruptedException {
        selectProductType(type).selectProductDepartment(department).selectCareCode(careCode);
    }

    /*
     * setup of employee data
     */

    public ValueTypeSettings_Page selectEmployeeStatus(String value) throws InterruptedException {
        click_Element(statusField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectEmployeeDepartment(String value) throws InterruptedException {
        click_Element(employeeDepartmentField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public ValueTypeSettings_Page selectRole(String value) throws InterruptedException {
        click_Element(roleField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));

        return this;
    }
    public void selectContactPersonRelation(String value) throws InterruptedException {
        click_Element(contactPersonRelationField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    public void setEmployeeFields(String status, String department, String role, String relation) throws InterruptedException {
        selectEmployeeStatus(status).selectEmployeeDepartment(department).selectRole(role).selectContactPersonRelation(relation);
    }

    /*
     * setup of vehicle data
     */

    public void selectVehicleStatus(String value) throws InterruptedException {
        click_Element(vehicleStatusField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    /*
     * setup of gift card data
     */

    public void selectGiftCardReason(String value) throws InterruptedException {
        click_Element(giftCardReasonField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    /*
     * setup of order data
     */

    public void selectTipCategory(String value) throws InterruptedException {
        click_Element(tipField);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+value+"']")));
    }

    public void clickSaveBtn() {
        click_Element(saveBtn);
    }
}