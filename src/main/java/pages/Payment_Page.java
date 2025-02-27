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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final By recurringPackagePrice = By.xpath("//p[contains(normalize-space(), '/Monthly')]");
    private final By recurringLicensePrice = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[1]");
    private final By recurringSubtotal = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[2]");
    private final By promotionalDiscount = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[3]");
    private final By recurringMonthlyFee = By.xpath("//p[contains(normalize-space(), '/Monthly')]/following-sibling::p[4]");

    private final By nameField = By.xpath("(//div[@class='column']/p)[13]");

    private final By confirmPaidSubscriptionBtn = By.cssSelector("#confirmButton");

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

    public boolean isPaymentFailed() {
        try {
            WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(paymentFailedMessage));
            return message.isDisplayed();
        }
        catch (Exception e) {
            logger.info("Payment failed message not found. Test continues.");
            return false;
        }
    }

    public double fetchRecurringPackagePrice(){
        String text = get_Text(recurringPackagePrice);
        String priceText = text.replaceAll(".*\\$(\\d+\\.\\d+).*", "$1");

        return Double.parseDouble(priceText);
    }
    public double[] fetchRecurringLicenseDetails(){
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

    public String fetchNameValue(){
        return get_Text(nameField);
    }

    public void verifyProratedOrderTable_Manual() {
        logger.info("\nVerifying Prorated Order Table (Manual) in Payment Page - \n");

        String proratedOrderTable = "//table/tbody";

        double getPackageNetDue, getLicenseNetDue, getTotalDue;

        /*
         * Calculations
         */

        perDayPackagePrice = packagePrice / monthTotalDays;
        packageRemainingAmount = new BigDecimal(perDayPackagePrice * monthUsedDays).setScale(2, RoundingMode.HALF_UP).doubleValue();

        perDayLicensePrice = (licenseCount * perUserLicensePrice) / monthTotalDays;
        licenseRemainingAmount = new BigDecimal(perDayLicensePrice * monthUsedDays).setScale(2, RoundingMode.HALF_UP).doubleValue();

        totalDue = new BigDecimal(packageRemainingAmount + licenseRemainingAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        /*
         * validating package price
         */

        getPackageNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[2]/td[3]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(packageRemainingAmount, getPackageNetDue, "Package net due mismatch; should be: " +packageRemainingAmount+ " but displayed: " +getPackageNetDue);

        logger.info("Get Package Net Due: {} and Package Remaining Amount: {}", getPackageNetDue, packageRemainingAmount);

        /*
         * validating license price
         */

        getLicenseNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[3]/td[3]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(licenseRemainingAmount, getLicenseNetDue, "License net due mismatch; should be: " +licenseRemainingAmount+ " but displayed: " +getLicenseNetDue);

        logger.info("Get License Net Due: {} and License Remaining Amount: {}", getLicenseNetDue, licenseRemainingAmount);

        /*
         * validating net due
         */

        getTotalDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[5]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(totalDue, getTotalDue, "Total Due Today mismatch; should be: " +totalDue+ " but displayed: " +getTotalDue);

        logger.info("Get Total Due: {} and Total Due: {}", getTotalDue, totalDue);
    }
    public void verifyRecurringOrderTable_Manual() {
        logger.info("\nVerifying Recurring Order Table (Manual) in Payment Page - \n");

        /*
         * validating package price
        */

        double getPackagePrice = fetchRecurringPackagePrice();
        Assert.assertEquals(packagePrice, getPackagePrice,"Package price mismatch; should be: " +packagePrice+ " but displayed: " +getPackagePrice);

        logger.info("Get Package Price: {} and Package Price: {}", getPackagePrice, packagePrice);

        /*
         * validating license details
        */

        double[] licenseDetails = fetchRecurringLicenseDetails();

        Assert.assertEquals(totalLicensePrice , licenseDetails[0],"Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +licenseDetails[0]);
        Assert.assertEquals(perUserLicensePrice , licenseDetails[1],"Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +licenseDetails[1]);
        Assert.assertEquals(licenseCount , licenseDetails[2],"License count mismatch; should be: " +licenseCount+ " but displayed: " +licenseDetails[2]);

        logger.info("Get Total License Price: {} and Total License Price: {}", licenseDetails[0], totalLicensePrice);
        logger.info("Get Per User License Price: {} and Per User License Price: {}", licenseDetails[1], perUserLicensePrice);
        logger.info("Get License Count: {} and License Count: {}", licenseDetails[2],licenseCount);

        /*
         * validating subtotal amount
        */

        subTotal = packagePrice + totalLicensePrice;
        double getSubTotal = fetchSubtotal();

        Assert.assertEquals(subTotal, getSubTotal,"Subtotal mismatch; should be: " +subTotal+ " but displayed: " +getSubTotal);

        logger.info("Get Sub Total: {} and Sub Total: {}", getSubTotal, subTotal);

        /*
         * validating recurring fee
        */

        if(promoApplied){
            recurringFee = subTotal - promoDiscount;
        }
        else{
            recurringFee = subTotal;
        }

        double getRecurringFee = fetchRecurringFee();
        Assert.assertEquals(recurringFee, getRecurringFee,"Recurring Fee mismatch; should be: " +recurringFee+ " but displayed: " +getRecurringFee);

        logger.info("Get Recurring Fee: {} and Recurring Fee: {}", getRecurringFee, recurringFee);
    }

    public void clickPaidSubscriptionConfirmBtn() {
        click_Element(confirmPaidSubscriptionBtn);
    }

    public void verifyProratedOrderTable() {
        logger.info("\nVerifying Prorated Order Table in Payment Page - \n");

        String proratedOrderTable = "//table/tbody";

        double getPackageNetDue, getLicenseNetDue, getSeasonalNetDue, getTotalDue;

        /*
         * Calculations
        */

        packageAdjustment = Math.max(0, new BigDecimal(packageNeedToPay - packageRemainingAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());

        licenseAdjustment = Math.max(0, new BigDecimal(licenseNeedToPay - licenseRemainingAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());

        seasonalLicenseAdjustment = Math.max(0, new BigDecimal(seasonalLicenseTotalPrice + upgradedTotalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());

        totalDue = new BigDecimal(packageAdjustment + licenseAdjustment + seasonalLicenseAdjustment).setScale(2, RoundingMode.HALF_UP).doubleValue();

        /*
         * validating package price
         */

        getPackageNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[2]/td[4]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertTrue(Math.abs(packageAdjustment - getPackageNetDue) <= tolerance, "Package Net Due Today mismatch; should be: " +packageAdjustment+ " but displayed: " +getPackageNetDue);

        logger.info("Get Package Net Due: {} and Package Adjustment: {}", getPackageNetDue, packageAdjustment);

        /*
         * validating additional license price
         */

        getLicenseNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[3]/td[4]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertTrue(Math.abs(licenseAdjustment - getLicenseNetDue) <= tolerance, "Additional License Net Due Today mismatch; should be: " +licenseAdjustment+ " but displayed: " +getLicenseNetDue);

        logger.info("Get License Net Due: {} and License Adjustment: {}", getLicenseNetDue, licenseAdjustment);

        /*
         * validating seasonal license price
         */

        getSeasonalNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[4]/td[4]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));

        Assert.assertTrue(Math.abs(seasonalLicenseAdjustment - getSeasonalNetDue) <= tolerance, "Seasonal license due mismatch; should be: " +seasonalLicenseAdjustment+ " but displayed: " +getSeasonalNetDue);

        logger.info("Get Seasonal Fee: {} and Seasonal Fee: {}", getSeasonalNetDue, seasonalLicenseAdjustment);

        /*
         * validating total due
         */

        getTotalDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[5]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertTrue(Math.abs(totalDue - getTotalDue) <= tolerance, "Total Due Today mismatch; should be: " +totalDue+ " but displayed: " +getTotalDue);

        logger.info("Get Total Due: {} and Total Due: {}", getTotalDue, totalDue);
    }
    public void verifyRecurringOrderTable(double packageFee, double licenseAmount) {
        logger.info("\nVerifying Recurring Order Table in Payment Page - \n");

        /*
         * validating package price
         */

        double getPackagePrice = fetchRecurringPackagePrice();
        Assert.assertEquals(packageFee, getPackagePrice,"Package price mismatch; should be: " +packageFee+ " but displayed: " +getPackagePrice);

        logger.info("Get Package Price: {} and Package Price: {}", getPackagePrice, packageFee);

        /*
         * validating license details
         */

        double[] licenseDetails = fetchRecurringLicenseDetails();

        Assert.assertEquals(totalLicensePrice , licenseDetails[0],"Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +licenseDetails[0]);
        Assert.assertEquals(perUserLicensePrice , licenseDetails[1],"Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +licenseDetails[1]);
        Assert.assertEquals(licenseAmount , licenseDetails[2],"License count mismatch; should be: " +licenseAmount+ " but displayed: " +licenseDetails[2]);

        logger.info("Get Total License Price: {} and Total License Price: {}", licenseDetails[0], totalLicensePrice);
        logger.info("Get Per User License Price: {} and Per User License Price: {}", licenseDetails[1], perUserLicensePrice);
        logger.info("Get License Count: {} and License Count: {}", licenseDetails[2],licenseAmount);

        /*
         * validating subtotal amount
         */

        subTotal = packageFee + totalLicensePrice;
        double getSubTotal = fetchSubtotal();

        Assert.assertEquals(subTotal, getSubTotal,"Subtotal mismatch; should be: " +subTotal+ " but displayed: " +getSubTotal);

        logger.info("Get Sub Total: {} and Sub Total: {}", getSubTotal, subTotal);

        /*
         * validating recurring fee
         */

        if(promoApplied){
            recurringFee = subTotal - promoDiscount;
        }
        else{
            recurringFee = subTotal;
        }

        double getRecurringFee = fetchRecurringFee();
        Assert.assertEquals(recurringFee, getRecurringFee,"Recurring Fee mismatch; should be: " +recurringFee+ " but displayed: " +getRecurringFee);

        logger.info("Get Recurring Fee: {} and Recurring Fee: {}", getRecurringFee, recurringFee);
    }
}