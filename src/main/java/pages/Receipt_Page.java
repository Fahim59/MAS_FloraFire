package pages;

import base.BaseClass;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;
import java.util.regex.*;

public class Receipt_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Receipt_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By trialText = By.cssSelector("div[class='page-top'] div");

    private final By totalPurchaseAmount = By.xpath("//h3[contains(normalize-space(), 'Total Purchase')]");

    private final By cardTypeAndEnding = By.xpath("//h3[contains(normalize-space(), 'Charge to')]");

    private final By downloadReceiptBtn = By.xpath("//button[@id='downloadButton']");

    public void verifyTrialRecurringOrderTable(int totalUser) {

        String recurringOrderTable = "//table[@class='order-table']/tbody";

        /*
         * validating package price
        */

        String packagePriceText = driver.findElement(By.xpath(recurringOrderTable + "/tr[1]/td[2]")).getText();
        double packageAmount = Double.parseDouble(packagePriceText.replaceAll("[^\\d.]", ""));
        Assert.assertEquals(packagePrice, packageAmount, "Package price mismatch; should be: " +packagePrice+ " but displayed: " +packageAmount);

        /*
         * validating license details
        */

        String licenseDetailsText = driver.findElement(By.xpath(recurringOrderTable + "/tr[2]/td[2]")).getText();
        Matcher matcher = Pattern.compile("\\$(\\d+\\.\\d+) \\(\\$(\\d+\\.\\d+) x (\\d+)\\)").matcher(licenseDetailsText);

        double totalPrice = 0.0, perUserPrice = 0.0;
        int userCount = 0;

        if (matcher.find()) {
            totalPrice = Double.parseDouble(matcher.group(1));
            perUserPrice = Double.parseDouble(matcher.group(2));
            userCount = Integer.parseInt(matcher.group(3));
        }

        Assert.assertEquals(perUserLicensePrice, perUserPrice, "Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +perUserPrice);
        Assert.assertEquals(totalLicensePrice, totalPrice, "Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +totalPrice);
        Assert.assertEquals(totalUser, userCount, "License count mismatch; should be: " +totalUser+ " but displayed: " +userCount);

        /*
         * validating subtotal amount
        */

        String subTotalText = driver.findElement(By.xpath(recurringOrderTable + "/tr[3]/td[2]")).getText();
        double subTotalAmount = Double.parseDouble(subTotalText.replaceAll("[^\\d.]", ""));
        Assert.assertEquals(subTotal, subTotalAmount, "Subtotal mismatch; should be: " +subTotal+ " but displayed: " +subTotalAmount);

        /*
         * validating promo discount
        */

        if(promoApplied){
            String promoDiscountText = driver.findElement(By.xpath(recurringOrderTable + "/tr[4]/td[2]")).getText();
            double promoAmount = Double.parseDouble(promoDiscountText.replaceAll("[^\\d.]", ""));
            Assert.assertEquals(promoDiscount, promoAmount, "Promo Discount mismatch; should be: " +promoDiscount+ " but displayed: " +promoAmount);
        }

        /*
         * validating recurring fee
        */

        String recurringFeeText = driver.findElement(By.xpath("//table[@class='order-table']/tfoot/tr/th[2]")).getText();
        double recurringFeeAmount = Double.parseDouble(recurringFeeText.replaceAll("[^\\d.]", ""));
        Assert.assertEquals(recurringFee, recurringFeeAmount, "Recurring Fee mismatch; should be: " +recurringFee+ " but displayed: " +recurringFeeAmount);
    }

    public void verifyTrialText(){
        try {
            WebElement text = wait.until(ExpectedConditions.presenceOfElementLocated(trialText));

            if (text.isDisplayed()) {
                logger.info("Trial Text is visible");
            }
        }
        catch (Exception e) {
            logger.info("Trial Text is not visible");
        }
    }

    public void verifyTotalPurchaseAmount(){
        String purchaseAmountText = get_Text(totalPurchaseAmount);
        double purchaseAmount = Double.parseDouble(purchaseAmountText.replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));

        if(!(purchaseAmount == 1)){
            Assert.fail("Purchase amount did not match. Test Failed");
        }
    }

    public void verifyCardTypeAndEnding(String cardNumber){
        String cardType, cardEnding;
        String cardText = get_Text(cardTypeAndEnding);

        int cardLength = cardNumber.replaceAll("\\D", "").length();
        String lastFourDigit = cardNumber.replaceAll("\\D", "").replaceAll("^.*(\\d{4})$", "$1");

        Matcher matcher = Pattern.compile("Charge to (.+?) Ending in (\\d+)").matcher(cardText);

        if (matcher.find()) {
            cardType = matcher.group(1);    //Card Type
            cardEnding = matcher.group(2); //Ending Digit

            if (cardLength == 15) {
                Assert.assertEquals(cardType, "Amex");
                Assert.assertEquals(cardEnding, lastFourDigit);

                logger.info("{} type card details matched", cardType);
            }
            else if (cardLength == 16) {
                Assert.assertEquals(cardType, "Visa");
                Assert.assertEquals(cardEnding, lastFourDigit);

                logger.info("{} type card details matched", cardType);
            }
            else {
                System.out.println("Card type does NOT match the expected type.");
            }
        }
    }

    public void clickDownloadBtn() {
        click_Element(downloadReceiptBtn);
    }

    public File getLatestFileFromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            return files[0];
        }
        return null;
    }

    public String openFileInNewTabAndVerifyText(WebDriver driver, File file) throws InterruptedException {
        String text = "";
        String currentHandle = driver.getWindowHandle();  // Store the current tab's handle
        String newTabHandle = null;                      // Variable to store the new tab's handle

        try {
            String fileUrl = "file:///" + file.getAbsolutePath().replace("\\", "/");
            logger.info("Opening file URL: {}", fileUrl);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open();");

            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(currentHandle)) {
                    newTabHandle = handle;
                    driver.switchTo().window(newTabHandle);  // Switch to the new tab
                    break;
                }
            }

            driver.get(fileUrl);

            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper pdfStripper = new PDFTextStripper();

                text = pdfStripper.getText(document);
            }
        }
        catch (Exception e) {
            logger.info("Failed to open the file in a new tab: {}", e.getMessage());
        }

        SmallWait(3000);

        if (newTabHandle != null) {
            driver.close();                            // Close the new tab
            driver.switchTo().window(currentHandle);  // Switch back to the original tab
        }

        return text;
    }

    public void verifyProratedOrderTable_Manual() {

        logger.info("Verifying Prorated Order Table in Receipt Page - ");

        String proratedOrderTable = "(//table[@class='order-table']/tbody)[1]";

        double getPackageNetDue, getLicenseNetDue, getSeasonalNetDue, getTotalDue;

        /*
         * validating package price
        */

        getPackageNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[1]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(packageRemainingAmount, getPackageNetDue, "Package net due mismatch; should be: " +packageRemainingAmount+ " but displayed: " +getPackageNetDue);

        logger.info("Get Package Net Due: {} and Package Remaining Amount: {}", getPackageNetDue, packageRemainingAmount);

        /*
         * validating license price
        */

        getLicenseNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[2]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(licenseRemainingAmount, getLicenseNetDue, "License net due mismatch; should be: " +licenseRemainingAmount+ " but displayed: " +getLicenseNetDue);

        logger.info("Get License Net Due: {} and License Remaining Amount: {}", getLicenseNetDue, licenseRemainingAmount);

        /*
         * validating seasonal license price
         */

        getSeasonalNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[4]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(seasonalLicenseTotalPrice, getSeasonalNetDue, "License net due mismatch; should be: " +seasonalLicenseTotalPrice+ " but displayed: " +getSeasonalNetDue);

        logger.info("Get Seasonal License Net Due: {} and Seasonal License Total Amount: {}", getSeasonalNetDue, seasonalLicenseTotalPrice);

        /*
         * validating net due
        */

        getTotalDue = Double.parseDouble(driver.findElement(By.xpath("(//table[@class='order-table']/tfoot)[1]/tr/th[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(totalDue, getTotalDue, "Total Due Today mismatch; should be: " +totalDue+ " but displayed: " +getTotalDue);

        logger.info("Get Total Due: {} and Total Due: {}", getTotalDue, totalDue);
    }

    public void verifyRecurringOrderTable_Manual() {

        String recurringOrderTable = "(//table[@class='order-table']/tbody)[2]";

        double getPackageFee, getSubTotalFee, getPromoDiscount, getRecurringFee;

        /*
         * validating package price
         */

        getPackageFee = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[1]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(packagePrice, getPackageFee, "Package price mismatch; should be: " +packagePrice+ " but displayed: " +getPackageFee);

        logger.info("Get Package Price: {} and Package Price: {}", getPackageFee, packagePrice);

        /*
         * validating license details
         */

        String licenseDetailsText = driver.findElement(By.xpath(recurringOrderTable + "/tr[2]/td[2]")).getText();
        Matcher matcher = Pattern.compile("\\$(\\d+\\.\\d+) \\(\\$(\\d+\\.\\d+) x (\\d+)\\)").matcher(licenseDetailsText);

        double totalPrice = 0.0, perUserPrice = 0.0;
        int userCount = 0;

        if (matcher.find()) {
            totalPrice = Double.parseDouble(matcher.group(1));
            perUserPrice = Double.parseDouble(matcher.group(2));
            userCount = Integer.parseInt(matcher.group(3));
        }

        Assert.assertEquals(perUserLicensePrice, perUserPrice, "Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +perUserPrice);
        Assert.assertEquals(totalLicensePrice, totalPrice, "Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +totalPrice);
        Assert.assertEquals(licenseCount, userCount, "License count mismatch; should be: " +licenseCount+ " but displayed: " +userCount);

        logger.info("Get Total License Price: {} and Total License Price: {}", totalPrice, totalLicensePrice);
        logger.info("Get Per User License Price: {} and Per User License Price: {}", perUserPrice, perUserLicensePrice);
        logger.info("Get License Count: {} and License Count: {}", userCount, licenseCount);

        /*
         * validating subtotal price
         */

        getSubTotalFee = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[3]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(subTotal, getSubTotalFee, "License net due mismatch; should be: " +subTotal+ " but displayed: " +getSubTotalFee);

        logger.info("Get Sub Total: {} and Sub Total: {}", getSubTotalFee, subTotal);

        /*
         * validating promo discount
         */

        if(promoApplied){
            getPromoDiscount = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[4]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
            Assert.assertEquals(promoDiscount, getPromoDiscount, "License net due mismatch; should be: " +promoDiscount+ " but displayed: " +getPromoDiscount);

            logger.info("Get Promo: {} and Promo: {}", getPromoDiscount, promoDiscount);
        }

        /*
         * validating recurring fee
         */

        getRecurringFee = Double.parseDouble(driver.findElement(By.xpath("(//table[@class='order-table']/tfoot)[2]/tr/th[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(recurringFee, getRecurringFee, "Total Due Today mismatch; should be: " +recurringFee+ " but displayed: " +getRecurringFee);

        logger.info("Get Recurring Fee: {} and Recurring Fee: {}", getRecurringFee, recurringFee);
    }

    public void verifyProratedOrderTable() {

        logger.info("Verifying Prorated Order Table in Receipt Page - ");

        String proratedOrderTable = "(//table[@class='order-table']/tbody)[1]";

        double getPackageNetDue, getLicenseNetDue, getSeasonalNetDue, getTotalDue;

        /*
         * validating package price
         */

//        getPackageNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[1]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
//        Assert.assertEquals(packageRemainingAmount, getPackageNetDue, "Package net due mismatch; should be: " +packageRemainingAmount+ " but displayed: " +getPackageNetDue);
//
//        logger.info("Get Package Net Due: {} and Package Remaining Amount: {}", getPackageNetDue, packageRemainingAmount);

        /*
         * validating license price
         */

        getLicenseNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[2]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertTrue(Math.abs(licenseAdjustment - getLicenseNetDue) <= tolerance, "Additional License Net Due Today mismatch; should be: " +licenseAdjustment+ " but displayed: " +getLicenseNetDue);

        logger.info("Get License Net Due: {} and License Remaining Amount: {}", getLicenseNetDue, licenseAdjustment);

        /*
         * validating seasonal license price
         */

        getSeasonalNetDue = Double.parseDouble(driver.findElement(By.xpath(proratedOrderTable + "/tr[4]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertTrue(Math.abs(seasonalLicenseAdjustment - getSeasonalNetDue) <= tolerance, "License net due mismatch; should be: " +seasonalLicenseAdjustment+ " but displayed: " +getSeasonalNetDue);

        logger.info("Get Seasonal License Net Due: {} and Seasonal License Total Amount: {}", getSeasonalNetDue, seasonalLicenseAdjustment);

        /*
         * validating net due
         */

        getTotalDue = Double.parseDouble(driver.findElement(By.xpath("(//table[@class='order-table']/tfoot)[1]/tr/th[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertTrue(Math.abs(totalDue - getTotalDue) <= tolerance, "Total Due Today mismatch; should be: " +totalDue+ " but displayed: " +getTotalDue);

        logger.info("Get Total Due: {} and Total Due: {}", getTotalDue, totalDue);
    }

    public void verifyRecurringOrderTable(double packageFee, double licenseAmount) {

        String recurringOrderTable = "(//table[@class='order-table']/tbody)";

        double getPackageFee, getSubTotalFee, getPromoDiscount, getRecurringFee;

        /*
         * validating package price
         */

        getPackageFee = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[1]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(packageFee, getPackageFee, "Package price mismatch; should be: " +packageFee+ " but displayed: " +getPackageFee);

        logger.info("Get Package Price: {} and Package Price: {}", getPackageFee, packageFee);

        /*
         * validating license details
         */

        String licenseDetailsText = driver.findElement(By.xpath(recurringOrderTable + "/tr[2]/td[2]")).getText();
        Matcher matcher = Pattern.compile("\\$(\\d+\\.\\d+) \\(\\$(\\d+\\.\\d+) x (\\d+)\\)").matcher(licenseDetailsText);

        double totalPrice = 0.0, perUserPrice = 0.0;
        int userCount = 0;

        if (matcher.find()) {
            totalPrice = Double.parseDouble(matcher.group(1));
            perUserPrice = Double.parseDouble(matcher.group(2));
            userCount = Integer.parseInt(matcher.group(3));
        }

        Assert.assertEquals(perUserLicensePrice, perUserPrice, "Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +perUserPrice);
        Assert.assertEquals(totalLicensePrice, totalPrice, "Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +totalPrice);
        Assert.assertEquals(licenseAmount, userCount, "License count mismatch; should be: " +licenseAmount+ " but displayed: " +userCount);

        logger.info("Get Total License Price: {} and Total License Price: {}", totalPrice, totalLicensePrice);
        logger.info("Get Per User License Price: {} and Per User License Price: {}", perUserPrice, perUserLicensePrice);
        logger.info("Get License Count: {} and License Count: {}", userCount, licenseAmount);

        /*
         * validating subtotal price
         */

        getSubTotalFee = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[3]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(subTotal, getSubTotalFee, "License net due mismatch; should be: " +subTotal+ " but displayed: " +getSubTotalFee);

        logger.info("Get Sub Total: {} and Sub Total: {}", getSubTotalFee, subTotal);

        /*
         * validating promo discount
         */

        if(promoApplied){
            getPromoDiscount = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[4]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
            Assert.assertEquals(promoDiscount, getPromoDiscount, "License net due mismatch; should be: " +promoDiscount+ " but displayed: " +getPromoDiscount);

            logger.info("Get Promo: {} and Promo: {}", getPromoDiscount, promoDiscount);
        }

        /*
         * validating recurring fee
         */

        getRecurringFee = Double.parseDouble(driver.findElement(By.xpath("(//table[@class='order-table']/tfoot)/tr/th[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(recurringFee, getRecurringFee, "Total Due Today mismatch; should be: " +recurringFee+ " but displayed: " +getRecurringFee);

        logger.info("Get Recurring Fee: {} and Recurring Fee: {}", getRecurringFee, recurringFee);
    }

    public void verifyRecurringOrderTable_(double packageFee, double licenseAmount) {

        String recurringOrderTable = "(//table[@class='order-table']/tbody)[2]";

        double getPackageFee, getSubTotalFee, getPromoDiscount, getRecurringFee;

        /*
         * validating package price
         */

        getPackageFee = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[1]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(packageFee, getPackageFee, "Package price mismatch; should be: " +packageFee+ " but displayed: " +getPackageFee);

        /*
         * validating license details
         */

        String licenseDetailsText = driver.findElement(By.xpath(recurringOrderTable + "/tr[2]/td[2]")).getText();
        Matcher matcher = Pattern.compile("\\$(\\d+\\.\\d+) \\(\\$(\\d+\\.\\d+) x (\\d+)\\)").matcher(licenseDetailsText);

        double totalPrice = 0.0, perUserPrice = 0.0;
        int userCount = 0;

        if (matcher.find()) {
            totalPrice = Double.parseDouble(matcher.group(1));
            perUserPrice = Double.parseDouble(matcher.group(2));
            userCount = Integer.parseInt(matcher.group(3));
        }

        Assert.assertEquals(perUserLicensePrice, perUserPrice, "Per User License Price mismatch; should be: " +perUserLicensePrice+ " but displayed: " +perUserPrice);
        Assert.assertEquals(totalLicensePrice, totalPrice, "Total License Price mismatch; should be: " +totalLicensePrice+ " but displayed: " +totalPrice);
        Assert.assertEquals(licenseAmount, userCount, "License count mismatch; should be: " +licenseAmount+ " but displayed: " +userCount);

        /*
         * validating subtotal price
         */

        getSubTotalFee = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[3]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(subTotal, getSubTotalFee, "License net due mismatch; should be: " +subTotal+ " but displayed: " +getSubTotalFee);

        /*
         * validating promo discount
         */

        if(promoApplied){
            getPromoDiscount = Double.parseDouble(driver.findElement(By.xpath(recurringOrderTable + "/tr[4]/td[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
            Assert.assertEquals(promoDiscount, getPromoDiscount, "License net due mismatch; should be: " +promoDiscount+ " but displayed: " +getPromoDiscount);
        }

        /*
         * validating recurring fee
         */

        getRecurringFee = Double.parseDouble(driver.findElement(By.xpath("(//table[@class='order-table']/tfoot)[2]/tr/th[2]")).getText().replaceAll(".*\\$(\\d+\\.\\d+).*", "$1"));
        Assert.assertEquals(recurringFee, getRecurringFee, "Total Due Today mismatch; should be: " +recurringFee+ " but displayed: " +getRecurringFee);
    }
}
