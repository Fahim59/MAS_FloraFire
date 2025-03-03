package pages;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LocationAndUser_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public LocationAndUser_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By locationAndUserTab = By.xpath("//strong[normalize-space()='Location & User']");

    private final By sameAsBillingField = By.cssSelector("#copy-location-checkbox");

    private final By primaryStoreField = By.cssSelector("#StoreModels_0__StoreName");
    private final By contactNameField = By.cssSelector("#StoreModels_0__ContactName");
    private final By timeZoneField = By.cssSelector("#StoreModels_0__TimeZoneId");
    private final By faxField = By.cssSelector("#StoreModels_0__FaxNumber");

    private final By includeMASField = By.cssSelector("#IncludeStoreOnMasDirectory");

    private final By addNewStoreBtn = By.cssSelector("button[onclick='addNewStore()']");

    private final By additionalStoreField = By.cssSelector("#StoreModels_1__StoreName");

    private final By additionalStoreContactNameField = By.cssSelector("#StoreModels_1__ContactName");
    private final By emailField = By.cssSelector("#StoreModels_1__Email");

    private final By addressField = By.cssSelector("#StoreModels_1__AddressLine1");
    private final By addressContField = By.cssSelector("#StoreModels_1__AddressLine2");

    private final By countryField = By.cssSelector("#StoreModels_1__CountryId");
    private final By stateField = By.cssSelector("#StoreModels_1__StateId");

    private final By cityField = By.cssSelector("#StoreModels_1__City");
    private final By zipField = By.cssSelector("#StoreModels_1__ZipCode");

    private final By additionalStoreTimeZoneField = By.cssSelector("#StoreModels_1__TimeZoneId");
    private final By additionalPhoneField = By.cssSelector("#StoreModels_1__PhoneNumber");
    private final By additionalStoreFaxField = By.cssSelector("#StoreModels_1__FaxNumber");

    private final By saveBtn = By.xpath("//button[normalize-space()='Save And Next']");

    private final By validationMessage = By.xpath("//div[@class='toast-message']");

    public LocationAndUser_Page clickSameAsBillingBtn(){
        click_CheckBox(sameAsBillingField);
        return this;
    }

    public LocationAndUser_Page enterPrimaryStoreName(String storeName){
        write_Send_Keys(primaryStoreField, storeName);
        return this;
    }
    public LocationAndUser_Page enterContactPersonName(String name){
        write_Send_Keys(contactNameField, name);
        return this;
    }
    public LocationAndUser_Page selectTimeZone(String timeZone){
        select_Dropdown_Element(timeZoneField, timeZone);
        return this;
    }
    public LocationAndUser_Page enterFaxNumber(String fax){
        write_Send_Keys(faxField, fax);
        return this;
    }

    public void clickIncludeMASDirectBtn(){
        click_CheckBox(includeMASField);
    }

    public void enterPrimaryStoreInfoDetails(String storeName, String name, String timeZone, String fax) {

        clickSameAsBillingBtn().enterPrimaryStoreName(storeName).enterContactPersonName(name).selectTimeZone(timeZone).
                enterFaxNumber(fax).clickIncludeMASDirectBtn();
    }

    public void clickAddNewStoreBtn() { click_Element(addNewStoreBtn); }

    public LocationAndUser_Page enterAdditionalStoreName(String storeName){
        write_Send_Keys(additionalStoreField, storeName);
        return this;
    }

    public LocationAndUser_Page enterAdditionalStoreContactPersonName(String name){
        write_Send_Keys(additionalStoreContactNameField, name);
        return this;
    }
    public LocationAndUser_Page enterEmail(String email){
        write_Send_Keys(emailField, email);
        return this;
    }

    public LocationAndUser_Page enterAddress(String address){
        write_Send_Keys(addressField, address);
        return this;
    }
    public LocationAndUser_Page enterContAddress(String address){
        write_Send_Keys(addressContField, address);
        return this;
    }

    public LocationAndUser_Page selectCountry(String country){
        select_Dropdown_Element(countryField, country);
        return this;
    }
    public LocationAndUser_Page selectState(String state) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(stateField, state));
        select_Dropdown_Element(stateField, state);
        return this;
    }

    public LocationAndUser_Page enterCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public LocationAndUser_Page enterZip(String zip){
        write_Send_Keys(zipField, zip);
        return this;
    }

    public LocationAndUser_Page selectAdditionalStoreTimeZone(String timeZone){
        select_Dropdown_Element(additionalStoreTimeZoneField, timeZone);
        return this;
    }
    public LocationAndUser_Page enterAdditionalStorePhone(String phone){
        write_Send_Keys(additionalPhoneField, phone);
        return this;
    }
    public LocationAndUser_Page enterAdditionalStoreFaxNumber(String fax){
        write_Send_Keys(additionalStoreFaxField, fax);
        return this;
    }

    public LocationAndUser_Page enterAdditionalStoreInfoDetails(String storeName, String name, String email, String address, String addressCont,
                                                             String country, String state, String city, String zip,
                                                             String timeZone, String phone, String fax) {

        return enterAdditionalStoreName(storeName).enterAdditionalStoreContactPersonName(name).enterEmail(email).enterAddress(address).
                enterContAddress(addressCont).selectCountry(country).selectState(state).enterCity(city).enterZip(zip).
                selectAdditionalStoreTimeZone(timeZone).enterAdditionalStorePhone(phone).enterAdditionalStoreFaxNumber(fax);
    }

    public void clickSuggestionText(){
        List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@id='suggestionsList']/li/span"));

        for (WebElement suggestion : suggestions) {

            String text = suggestion.getText();
            String[] parts = text.split(", ");

            if (parts.length == 3) {
                suggestion.click();
                break;
            }
        }
    }

    public void validateAddress(int store) throws InterruptedException {
        SmallWait(500);

        if(store == 0){
            WebElement validateBtn = driver.findElement(By.xpath("//button[@id='StoreModels[" +store+ "].ZipCodeButton']"));
            validateBtn.click();
        }

        SmallWait(500);

        try {
            //WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#suggestionModalMessage")));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='ui-dialog-title'])[1]")));

            while(element.isDisplayed()){
                logger.info("Validation Failed. Retrying...");

                SmallWait(500);
                clickSuggestionText();

                SmallWait(200);
                Assert.assertEquals("Address validate successfully.", get_Text(validationMessage));
            }
        }
        catch (Exception e) {
            logger.info("Validation Message not found - {}", e.getMessage());
        }
    }

    //-------------------------------------------------------------------------------------------------------------------//
    //--------------------------------------------- Additional License Info ---------------------------------------------//
    //-------------------------------------------------------------------------------------------------------------------//

    private final By licenseTab = By.cssSelector("#LicenseCountTab");

    private final By additionalLicenseField = By.cssSelector("#AdditionalLicenseCount");

    private final String licensePriceTable = "//*[@id='licenseCount']/div/div/div[2]/table/tbody";
    private final By priceTr = By.xpath(licensePriceTable+"/tr");

    private final By seasonalLicenseField = By.xpath("//input[@id='SeasonalLicenseUsers']");
    private final By seasonalLicenseMonthField = By.xpath("//input[@class='form-check-input']");
    private final By seasonalLicensePriceField = By.xpath("//strong[contains(text(),'Charge for Seasonal License')]");

    public int getLicensePriceTrSize() {
        List<WebElement> elements = driver.findElements(priceTr);
        return elements.size();
    }

    public Object[] priceTable(int licenseCount) {
        double totalLicenseFee = 0.0;
        double licenseFee = 0.0;

        for(int l = 1; l<= getLicensePriceTrSize(); l++){

            String range = driver.findElement(By.xpath(licensePriceTable+ "/tr["+l+"]/td[1]")).getText();
            String price = driver.findElement(By.xpath(licensePriceTable+ "/tr["+l+"]/td[2]")).getText();

            if(range.contains("less than")){
                Pattern pattern = Pattern.compile("less than (\\d+)");
                Matcher matcher = pattern.matcher(range);

                licenseFee = Double.parseDouble(price.replaceAll("[^\\d.]", ""));

                if (matcher.find()) {
                    int upperLimit = Integer.parseInt(matcher.group(1));
                    if (licenseCount < upperLimit) {
                        //System.out.println("Match found on row: " +l);

                        totalLicenseFee = calculateTotalFee(price, licenseCount);

                        break;
                    }
                }
            }
            else{
                Pattern pattern = Pattern.compile("(\\d+)-(\\d+)");
                Matcher matcher = pattern.matcher(range);

                licenseFee = Double.parseDouble(price.replaceAll("[^\\d.]", ""));

                if (matcher.find()) {
                    int lowerLimit = Integer.parseInt(matcher.group(1));
                    int upperLimit = Integer.parseInt(matcher.group(2));
                    if (licenseCount >= lowerLimit && licenseCount <= upperLimit) {
                        //System.out.println("Match found on row: " +l);

                        totalLicenseFee = calculateTotalFee(price, licenseCount);

                        break;
                    }
                }
            }
        }
        if (totalLicenseFee == 0.0) {
            throw new IllegalArgumentException("No matching range found for the given license count: " + licenseCount);
        }

        return new Object[] {totalLicenseFee, licenseFee};
    }

    private double calculateTotalFee(String price, int licenseCount) {
        double licenseFee = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
        return licenseFee * licenseCount;
    }

    public void enterAdditionalLicense(int count){
        wait.until(ExpectedConditions.visibilityOfElementLocated(additionalLicenseField));
        write_Send_Keys(additionalLicenseField, String.valueOf(count));
    }

    public void clickSaveBtn() { click_Element(saveBtn); }

    public void clickLocationAndUserTab() { click_Element(locationAndUserTab); }

    public void clickLocationTab() throws InterruptedException {
        clickLocationAndUserTab();
        SmallWait(1500);
        click_Element(licenseTab);
    }

    public void enterSeasonalLicenseAndMonth(int count, int month){
        wait.until(ExpectedConditions.visibilityOfElementLocated(seasonalLicenseField));
        write_Send_Keys(seasonalLicenseField, String.valueOf(count));
        click_Radio_Element(seasonalLicenseMonthField, String.valueOf(month));
    }

    public double calculateSeasonalLicenseTotalFee_Prior(){
        perDaySeasonalLicensePrice = (double) (seasonalLicenseCount * perUserSeasonalLicensePrice) / seasonalMonthTotalDays;
        seasonalLicenseTotalPrice = perDaySeasonalLicensePrice * seasonalMonthUsedDays;

        logger.info("Seasonal License Price: {}", seasonalLicenseTotalPrice);

        return seasonalLicenseTotalPrice;
    }
    public double calculateSeasonalLicenseTotalFee_Today(){
        return upgradedTotalAmount = upgradedSeasonalLicenseCount * upgradedPerUserSeasonalLicensePrice * upgradedSeasonalMonth;
    }

    public Object[] calculatePriorPackagePrepaid(){
        logger.info("\nCalculating Prior Package Prepaid Data - \n");

        Object[] priceTable = priceTable(Integer.parseInt(String.valueOf(licenseCount)));
        perUserLicensePrice = (double) priceTable[1];

        perDayPackagePrice = packagePrice / monthTotalDays;
        packageRemainingAmount = new BigDecimal(perDayPackagePrice * monthUsedDays).setScale(2, RoundingMode.HALF_UP).doubleValue();

        perDayLicensePrice = (licenseCount * perUserLicensePrice) / monthTotalDays;
        licenseRemainingAmount = new BigDecimal(perDayLicensePrice * monthUsedDays).setScale(2, RoundingMode.HALF_UP).doubleValue();

        logger.info("Per Day Package Price: {}", perDayPackagePrice);
        logger.info("Package Remaining Price: {}", packageRemainingAmount);

        logger.info("Per User License Price: {}", perUserLicensePrice);
        logger.info("Per Day License Price: {}", perDayLicensePrice);
        logger.info("License Remaining Price: {}", licenseRemainingAmount);

        return new Object[] {packageRemainingAmount, licenseRemainingAmount};
    }
    public Object[] calculateTodayPackageChange(){
        logger.info("\nCalculating Today's Package Change - \n");

        Object[] priceTable = priceTable(Integer.parseInt(String.valueOf(upgradedLicenseCount)));
        upgradedPerUserLicensePrice = (double) priceTable[1];

        upgradedPerDayPackagePrice = upgradedPackagePrice / monthTotalDays;
        packageNeedToPay = new BigDecimal(upgradedPerDayPackagePrice * monthUsedDays).setScale(2, RoundingMode.HALF_UP).doubleValue();

        upgradedPerDayLicensePrice = (upgradedLicenseCount * upgradedPerUserLicensePrice) / monthTotalDays;
        licenseNeedToPay = new BigDecimal(upgradedPerDayLicensePrice * monthUsedDays).setScale(2, RoundingMode.HALF_UP).doubleValue();

        logger.info("Upgraded Per Day Package Price: {}", upgradedPerDayPackagePrice);
        logger.info("Package Need to Pay: {}", packageNeedToPay);

        logger.info("Upgraded Per User License Price: {}", upgradedPerUserLicensePrice);
        logger.info("Upgraded Per Day License Price: {}", upgradedPerDayLicensePrice);
        logger.info("License Need to Pay: {}", licenseNeedToPay);

        return new Object[] {packageNeedToPay, licenseNeedToPay};
    }
}