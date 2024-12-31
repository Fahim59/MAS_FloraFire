package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Home_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Home_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By storeDropdown = By.xpath("//mat-select[@panelclass='store-select-panel']/div/div/span/span");

    private final By homeMenu = By.xpath("//span[text()='Home']");

    private final By valueMenu = By.xpath("(.//span[text()='Value'])[1]");

    private final By customerMaintenanceMenu = By.xpath("(.//span[text()='Customer Maintenance'])[1]");

    private final By messageShortcutsMenu = By.xpath("(.//span[text()='Message ShortCuts'])[1]");

    private final By settingsMenu = By.xpath("(.//span[text()='Settings'])[1]");
    private final By corporateSettingsMenu = By.xpath("(.//span[text()='Corporate Settings'])[1]");
    private final By valueTypeSettingsMenu = By.xpath("(.//span[text()='Value Type Settings'])[1]");
    private final By vehiclesMenu = By.xpath("(.//span[text()='Vehicles'])[1]");
    private final By employeeSettingsMenu = By.xpath("(.//span[text()='Employee Settings'])[1]");

    private final By deliveryMenu = By.xpath("(.//span[text()='Deliveries'])[1]");
    private final By deliveryZonesMenu = By.xpath("(.//span[text()='Delivery Zones'])[1]");
    private final By deliverySlotMenu = By.xpath("(.//span[text()='Delivery Slot'])[1]");
    private final By deliveryModesMenu = By.xpath("(.//span[text()='Delivery Modes'])[1]");
    private final By deliveryCodeMenu = By.xpath("(.//span[text()='Delivery Code'])[1]");
    private final By shortCodeMenu = By.xpath("(.//span[text()='Short Codes'])[1]");

    private final By giftCardsMenu = By.xpath("(.//span[text()='Gift Cards'])[1]");

    private final By productMaintenanceMenu = By.xpath("(.//span[text()='Product Maintenance'])[1]");

    private final By discountMaintenanceMenu = By.xpath("(.//span[text()='Discount Maintenance'])[1]");

    private final By employeesMenu = By.xpath("(.//span[text()='Employees'])[1]");

    private final By POSMenu = By.xpath("(.//span[text()='POS'])[1]");

    public String getStoreName(){
        return get_Text(storeDropdown);
    }

    public void verifyHomeMenuVisibility(){
        verifyElementVisibility(homeMenu);
    }

    public void clickValueMenu(){
        click_Element(valueMenu);
    }

    public void clickCustomerMaintenanceMenu() {
        click_Element(customerMaintenanceMenu);
    }

    public void clickMessageShortcutsMenu() {
        click_Element(messageShortcutsMenu);
    }

    public void clickCorporateSettingsMenu() {
        click_Element(settingsMenu);
        click_Element(corporateSettingsMenu);
    }
    public void clickValueTypeSettingsMenu() {
        click_Element(settingsMenu);
        click_Element(valueTypeSettingsMenu);
    }
    public void clickVehiclesMenu() {
        click_Element(settingsMenu);
        click_Element(vehiclesMenu);
    }
    public void clickEmployeeSettingsMenu() {
        click_Element(settingsMenu);
        click_Element(employeeSettingsMenu);
    }

    public void clickDeliveryZonesMenu() {
        click_Element(deliveryMenu);
        click_Element(deliveryZonesMenu);
    }
    public void clickDeliverySlotMenu() {
        click_Element(deliveryMenu);
        click_Element(deliverySlotMenu);
    }
    public void clickDeliveryModesMenu() {
        click_Element(deliveryMenu);
        click_Element(deliveryModesMenu);
    }
    public void clickDeliveryCodeMenu() {
        click_Element(deliveryMenu);
        click_Element(deliveryCodeMenu);
    }
    public void clickShortCodeMenu() {
        click_Element(deliveryMenu);
        click_Element(shortCodeMenu);
    }

    public void clickGiftCardsMenu() {
        click_Element(giftCardsMenu);
    }

    public void clickProductMaintenanceMenu() {
        click_Element(productMaintenanceMenu);
    }

    public void clickDiscountMaintenanceMenu() {
        click_Element(discountMaintenanceMenu);
    }

    public void clickEmployeesMenu() {
        click_Element(employeesMenu);
    }

    public void clickPOSMenu() {
        click_Element(POSMenu);
    }
}