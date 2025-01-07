package pages.Delivery;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductMaintenance_Page;

import java.time.Duration;

public class DeliveryZone_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public DeliveryZone_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newZoneButton = By.xpath("//span[contains(text(),'New Zone')]");

    public void clickNewZoneButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(newZoneButton);
    }

    /*
     * Delivery Prices
     */

    private final By zoneNameField = By.xpath("//input[@formcontrolname='name']");

    private final By zoneDescriptionField = By.xpath("//textarea[@formcontrolname='description']");

    private final By zoneTypeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");

    private final By zoneAreaField = By.xpath("(//div[contains(@id,'mat-select-value')])[3]");

    private final By latitudeOneField = By.xpath("(.//*[@formcontrolname='latitude'])[1]");
    private final By latitudeTwoField = By.xpath("(.//*[@formcontrolname='latitude'])[2]");
    private final By latitudeThreeField = By.xpath("(.//*[@formcontrolname='latitude'])[3]");
    private final By latitudeFourField = By.xpath("(.//*[@formcontrolname='latitude'])[4]");

    private final By longitudeOneField = By.xpath("(.//*[@formcontrolname='longitude'])[1]");
    private final By longitudeTwoField = By.xpath("(.//*[@formcontrolname='longitude'])[2]");
    private final By longitudeThreeField = By.xpath("(.//*[@formcontrolname='longitude'])[3]");
    private final By longitudeFourField = By.xpath("(.//*[@formcontrolname='longitude'])[4]");

    private final By addCoordinateButton = By.xpath("//span[normalize-space()='Add Coordinate']");
    private final By generateMapButton = By.xpath("//span[normalize-space()='Generate Map']");

    private final By zipCodeField = By.xpath("//input[@formcontrolname='zipCode']");

    private final By autoTransferField = By.xpath("//input[@id='mat-mdc-checkbox-2-input']");

    private final By storeField = By.xpath("(//div[contains(@id,'mat-select-value')])[4]");

    public DeliveryZone_Page enterZoneName(String zone) {
        write_Send_Keys(zoneNameField, zone);
        return this;
    }

    public DeliveryZone_Page enterZoneDescription(String description) {
        write_Send_Keys(zoneDescriptionField, description);
        return this;
    }

    public DeliveryZone_Page selectZoneType(String type) throws InterruptedException {
        if(!get_Text(zoneTypeField).equals(type)){
            click_Element(zoneTypeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+type+"', ' ')]")));
        }

        return this;
    }

    public DeliveryZone_Page selectZoneAreaSelector(String area) throws InterruptedException {
        if(!get_Text(zoneAreaField).equals(area)){
            click_Element(zoneAreaField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[text()=concat(' ', '"+area+"', ' ')]")));
        }

        return this;
    }

    public DeliveryZone_Page enterLatitudeOne(String latOne) {
        write_Send_Keys(latitudeOneField, latOne);
        return this;
    }
    public DeliveryZone_Page enterLatitudeTwo(String latTwo) {
        write_Send_Keys(latitudeTwoField, latTwo);
        return this;
    }
    public DeliveryZone_Page enterLatitudeThree(String latThree) {
        write_Send_Keys(latitudeThreeField, latThree);
        return this;
    }
    public DeliveryZone_Page enterLatitudeFour(String latFour) {
        write_Send_Keys(latitudeFourField, latFour);
        return this;
    }

    public DeliveryZone_Page enterLongitudeOne(String latOne) {
        write_Send_Keys(longitudeOneField, latOne);
        return this;
    }
    public DeliveryZone_Page enterLongitudeTwo(String latTwo) {
        write_Send_Keys(longitudeTwoField, latTwo);
        return this;
    }
    public DeliveryZone_Page enterLongitudeThree(String latThree) {
        write_Send_Keys(longitudeThreeField, latThree);
        return this;
    }
    public DeliveryZone_Page enterLongitudeFour(String latFour) {
        write_Send_Keys(longitudeFourField, latFour);
        return this;
    }

    public void clickAddCoordinateButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(addCoordinateButton);
    }

    public void clickGenerateMapButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(generateMapButton);
    }

    public DeliveryZone_Page enterZipCode(String zip) {
        write_Send_Keys(zipCodeField, zip);
        return this;
    }

    public DeliveryZone_Page isAutoTransfer(String flag) {
        if(flag.equalsIgnoreCase("Yes")){
            selectCheckBox(autoTransferField);
        }
        return this;
    }

    public DeliveryZone_Page selectStore(String store) throws InterruptedException {
        click_Element(storeField);

        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mat-option/span[contains(text(), '"+store+"')]")));

        return this;
    }

    public void createZoneWithCoordinate(String zone, String description, String type){

    }

    /*
     * Delivery Prices
     */

    private final By deliveryFeeField = By.xpath("//input[@formcontrolname='deliveryFee']");
    private final By deliveryWithinTwoHourField = By.xpath("//input[@formcontrolname='within2HrDeliveryFee']");
    private final By deliveryWithinThreeHourField = By.xpath("//input[@formcontrolname='within3HrDeliveryFee']");
    private final By deliveryWithinFourHourField = By.xpath("//input[@formcontrolname='within4HrDeliveryFee']");
    private final By expressDeliveryField = By.xpath("//input[@formcontrolname='expressDeliveryFee']");
    private final By weddingDeliveryField = By.xpath("//input[@formcontrolname='weddingDayDeliveryFee']");

    private final By futureWithinTwoHourField = By.xpath("//input[@formcontrolname='futureWithin2HrDeliveryFee']");
    private final By futureWithinThreeHourField = By.xpath("//input[@formcontrolname='futureWithin3HrDeliveryFee']");
    private final By futureWithinFourHourField = By.xpath("//input[@formcontrolname='futureWithin4HrDeliveryFee']");
    private final By salesTaxField = By.xpath("//input[@formcontrolname='salesTax']");
    private final By sundayDeliveryField = By.xpath("//input[@formcontrolname='sundayDeliveryFee']");

    /*
     * Button and Message
     */

    private final By saveBtn = By.xpath("(//span[contains(text(),'Save')])[1]");

    private final By successMessage = By.xpath("//p[@class='abp-toast-message']");

    public String getSuccessMessage(){
        return get_Text(successMessage);
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);
    }
}