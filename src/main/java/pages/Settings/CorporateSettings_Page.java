package pages.Settings;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CorporateSettings_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public CorporateSettings_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    /* * General */

    private final By geocodingPreferenceField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");
    private final By passwordResetDaysField = By.xpath("//input[@formcontrolname='passwordResetDays']");
    private final By auditLogEmailField = By.xpath("//input[@formcontrolname='auditLogEmails']");

    public CorporateSettings_Page selectGeocodingPreference(String preference) throws InterruptedException {
        click_Element(geocodingPreferenceField);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+preference+"')]")));

        return this;
    }

    public CorporateSettings_Page enterPasswordResetDays(String day){
        write_Send_Keys(passwordResetDaysField, day);
        return this;
    }

    public void enterAuditLogEmail(String email){
        write_Send_Keys(auditLogEmailField, email);
    }

    public void enterGeneralData(String preference, String day, String email) throws InterruptedException {
        selectGeocodingPreference(preference).enterPasswordResetDays(day).enterAuditLogEmail(email);
    }

    /* * Transaction Charges */

    private final By relayFeeField = By.xpath("//input[@formcontrolname='relayFee']");
    private final By overSeasRelayFeeField = By.xpath("//input[@formcontrolname='overseasRelayFee']");
    private final By wireOutDeliveryFeeField = By.xpath("//input[@formcontrolname='wireOutDeliveryFee']");

    public CorporateSettings_Page enterRelayFee(String relayFee){
        write_Send_Keys(relayFeeField, relayFee);
        return this;
    }

    public CorporateSettings_Page enterOverSeasRelayFee(String overSeasRelayFee){
        write_Send_Keys(overSeasRelayFeeField, overSeasRelayFee);
        return this;
    }

    public void enterWireOutDeliveryFee(String wireOutDeliveryFee){
        write_Send_Keys(wireOutDeliveryFeeField, wireOutDeliveryFee);
    }

    public void enterTransactionChargeData(String relayFee, String overSeasRelayFee, String wireOutDeliveryFee) {
        enterRelayFee(relayFee).enterOverSeasRelayFee(overSeasRelayFee).enterWireOutDeliveryFee(wireOutDeliveryFee);
    }

    /* * Tax */

    private final By taxOnDeliveryField = By.xpath("//mat-radio-group[@formcontrolname='taxOnDelivery']//input[contains(@name, 'mat-radio-group')]");
    private final By taxOnRelayField = By.xpath("//mat-radio-group[@formcontrolname='taxOnRelay']//input[contains(@name, 'mat-radio-group')]");
    private final By salesTaxField = By.xpath("//input[@formcontrolname='salesTaxPercentage']");

    public CorporateSettings_Page clickTaxOnDelivery(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            click_Radio_Element(taxOnDeliveryField, "true");
        }
        else {
            click_Radio_Element(taxOnDeliveryField, "false");
        }
        return this;
    }

    public CorporateSettings_Page clickTaxOnRelay(String relayFlag) {
        if(relayFlag.equalsIgnoreCase("Yes")){
            click_Radio_Element(taxOnRelayField, "true");
        }
        else {
            click_Radio_Element(taxOnRelayField, "false");
        }
        return this;
    }

    public void enterSalesTaxPercentage(String salesTax){
        write_Send_Keys(salesTaxField, salesTax);
    }

    public void enterTaxSettings(String flag, String relayFlag, String salesTax) {
        clickTaxOnDelivery(flag).clickTaxOnRelay(relayFlag).enterSalesTaxPercentage(salesTax);
    }

    /* * Recipe Inventory Manage Type */

    private final By recipeTypeField = By.xpath("//mat-radio-group[@formcontrolname='recipeInventoryManageType']//input[contains(@name, 'mat-radio-group')]");

    public void clickRecipeType(String type) {
        if(type.equalsIgnoreCase("Single")){
            click_Radio_Element(recipeTypeField, "1");
        }
        else {
            click_Radio_Element(recipeTypeField, "2");
        }
    }

    /* * POS */

    private final By enableDiscountOnOrderField = By.xpath("//mat-checkbox[@formcontrolname='enableDiscountOnOrderItemSubtotal']/div/div/input");
    private final By allowPartialPaymentField = By.xpath("//mat-checkbox[@formcontrolname='allowPartialPayment']/div/div/input");
    private final By creditCardFeeField = By.xpath("//input[@formcontrolname='creditCardFee']");
    private final By enableCCFeeOnOrderField = By.xpath("//mat-checkbox[@formcontrolname='enabledCreditCardFeeOnPosOrder']/div/div/input");

    public CorporateSettings_Page enableDiscountOnOrder(String discountFlag) {
        WebElement element = wait_for_presence(enableDiscountOnOrderField);
        String isChecked = element.getAttribute("class");

        boolean shouldEnable = discountFlag.equalsIgnoreCase("Yes");
        boolean isCurrentlySelected = isChecked.contains("selected");

        if (shouldEnable && !isCurrentlySelected) {
            element.click();
        }
        else if (!shouldEnable && isCurrentlySelected) {
            element.click();
        }
        return this;
    }

    public CorporateSettings_Page allowPartialPayment(String paymentFlag) {
        WebElement element = wait_for_presence(allowPartialPaymentField);
        String isChecked = element.getAttribute("class");

        boolean shouldEnable = paymentFlag.equalsIgnoreCase("Yes");
        boolean isCurrentlySelected = isChecked.contains("selected");

        if (shouldEnable && !isCurrentlySelected) {
            element.click();
        }
        else if (!shouldEnable && isCurrentlySelected) {
            element.click();
        }
        return this;
    }

    public CorporateSettings_Page enterCreditCardFee(String ccFee){
        write_Send_Keys(creditCardFeeField, ccFee);
        return this;
    }

    public void enableCCFeeOnOrder(String ccFlag) {
        WebElement element = wait_for_presence(enableCCFeeOnOrderField);
        String isChecked = element.getAttribute("class");

        boolean shouldEnable = ccFlag.equalsIgnoreCase("Yes");
        boolean isCurrentlySelected = isChecked.contains("selected");

        if (shouldEnable && !isCurrentlySelected) {
            element.click();
        }
        else if (!shouldEnable && isCurrentlySelected) {
            element.click();
        }
    }

    public void enterPOSSettings(String discountFlag, String paymentFlag, String ccFee, String ccFlag) {
        enableDiscountOnOrder(discountFlag).allowPartialPayment(paymentFlag).enterCreditCardFee(ccFee).enableCCFeeOnOrder(ccFlag);
    }

    /* * Gift Card */

    private final By enableCarryForwardField = By.xpath("//mat-checkbox[@formcontrolname='enableCarryForwardNormalGiftCardBalance']//label[normalize-space()='Enable Carry Forward Normal GiftCard Balance']");

    public void enableCarryForward(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(enableCarryForwardField);
        }
    }

    /* * Corporate Customers */

    private final By customerField = By.xpath("(//div[contains(@id,'mat-select-value')])[7]");

    public CorporateSettings_Page selectCustomerField() throws InterruptedException {
        click_Element(customerField);
        SmallWait(200);

        return this;
    }

    public CorporateSettings_Page selectCustomer(String customer) {
        WebElement customerName = driver.findElement(By.xpath("//mat-option[@role='option']/span[text()='"+customer+"']"));
        customerName.click();

        return this;
    }

    public void selectCorporateCustomers(String cusOne, String cusTwo, String cusThree) throws InterruptedException {
        selectCustomerField().selectCustomer(cusOne).selectCustomer(cusTwo).selectCustomer(cusThree).clickEscButton();
    }

    /*
      * Buttons
    */

    private final By saveBtn = By.xpath("//span[contains(text(),'Save')]");

    public void clickSaveButton() throws InterruptedException {
        SmallWait(500);
        click_Element_Js(saveBtn);
    }
}