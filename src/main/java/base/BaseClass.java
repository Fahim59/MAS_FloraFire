package base;

import com.github.javafaker.Faker;
import factory.DriverFactory;

import org.apache.logging.log4j.*;
import org.json.*;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.*;
import java.time.*;
import java.util.*;
import java.util.List;

public class BaseClass {
    public static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    private static final Logger baseLogger = LogManager.getLogger(BaseClass.class);
    protected final Logger logger = LogManager.getLogger(getClass());

    protected JSONObject jsonData;

    private final Faker faker;
    private final String fullName, firstName, lastName, address, addressCont, city, state, company;

    //---------------------------------------------------------------------------------------------//
    public BaseClass() {
        faker = new Faker(new Locale("en-US"));

        this.fullName = faker.name().fullName().replaceAll("\\.", "");
        this.firstName = fullName.split(" ")[0];
        this.lastName = fullName.split(" ")[fullName.split(" ").length - 1];

        this.address = faker.address().streetAddress();
        this.addressCont = faker.address().secondaryAddress();
        this.city = faker.address().city();
        this.state = faker.address().state();

        this.company = faker.company().name();
    }

    public String getFullName() { return fullName; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public String getAddress() { return address; }
    public String getAddressCont() { return addressCont; }
    public String getCity() { return city; }
    public String getState() { return state; }

    public String getEmail() { return firstName.toLowerCase() + "@" + "qca6z4pm.mailosaur.net"; }

    public String getPhone() {
        String areaCode = String.format("%03d", faker.number().numberBetween(100, 999));
        String centralOfficeCode = String.format("%03d", faker.number().numberBetween(100, 999));
        String lineNumber = String.format("%04d", faker.number().numberBetween(1000, 9999));

        return String.format("(%s) %s-%s", areaCode, centralOfficeCode, lineNumber);
    }

    public String getCompany() { return company; }
    //---------------------------------------------------------------------------------------------//

    @BeforeSuite
    public static void launch_browser(){
        driver = DriverFactory.initializeDriver(System.getProperty("browser",
                new ConfigLoader().initializeProperty().getProperty("browser")));

        baseLogger.info("Browser launched successfully");
    }

    @BeforeClass
    public void readJsonData() throws Exception {
        FileReader data = null;
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            jsonData = new JSONObject(tokener);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (data != null) {
                data.close();
            }
        }
    }

    //---------------------------------------------------------------------------------------------//
    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void Open_Website(String endPoint){
        DriverFactory.getDriver().get(new ConfigLoader().initializeProperty().getProperty("baseUrl") +endPoint);
    }

    public static void SmallWait(int second) throws InterruptedException {Thread.sleep(second);}

    public static void Scroll(int xOffset, int yOffset) throws InterruptedException {
        SmallWait(1000);
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", xOffset, yOffset);
    }

    public void verifyCurrentUrl(String expectedText) throws InterruptedException {
        SmallWait(2000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedText), "The current URL does not contain the expected text: " + expectedText);
    }

    public void verifyElementVisibility(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            if (element.isDisplayed()) {
                baseLogger.info("The 'Home' menu is visible.");
            }
            else {
                Assert.fail("Login failed, the 'Home' menu is not visible.");
            }
        }
        catch (Exception e) {
            baseLogger.info("Test Failed: An exception occurred - {}", e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------//
    public WebElement wait_for_visibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public WebElement wait_for_presence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public List<WebElement> wait_for_presence_list(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void click_Element(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    public void click_Element_Js(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        js.executeScript("arguments[0].click();", element);
    }

    public void select_Dropdown_Element(By locator, String text) {
        WebElement element = wait_for_visibility(locator);

        Select select = new Select(element);
        select.selectByVisibleText(text);
    }
    public void click_Radio_Element(By locator, String text) {
        List<WebElement> options = wait_for_presence_list(locator);

        for (WebElement option : options) {
            if (option.getAttribute("value").equalsIgnoreCase(text)) {
                if (!option.isSelected()) {
                    js.executeScript("arguments[0].click();", option);
                }
            }
        }
    }

    public void click_CheckBox(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!element.isSelected()) {
            js.executeScript("arguments[0].click();", element);
        }
    }
    public void selectCheckBox(By locator){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void hover_And_Click(By locator_hover, By locator_click ){
        Actions actions = new Actions(driver);

        WebElement element = wait_for_visibility(locator_hover);
        actions.moveToElement(element).perform();

        click_Element(locator_click);
    }

    public void drag_And_Drop(By dragable, By dropable ){
        Actions actions = new Actions(driver);

        WebElement dragElement = wait_for_visibility(dragable);
        WebElement dropElement = wait_for_visibility(dropable);

        actions.dragAndDrop(dragElement, dropElement).build().perform();
    }

    public void write_Send_Keys(By locator, String txt) {
        WebElement element = wait_for_presence(locator);
        String text = element.getAttribute("value");

        if (text.isEmpty()) {
            element.sendKeys(txt);
        } else {
            element.clear();
            element.sendKeys(txt);
        }
    }
    public void write_JS_Executor(By locator, String txt) {
        WebElement element = wait_for_presence(locator);
        String text = element.getAttribute("value");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        if (text.isEmpty()) {
            js.executeScript("arguments[0].value = arguments[1];", element, txt);
        }
        else {
            js.executeScript("arguments[0].value = '';", element);
            js.executeScript("arguments[0].value = arguments[1];", element, txt);
        }
    }
    public void write_ActionClass(By locator, String txt) {
        WebElement element = wait_for_presence(locator);
        String text = element.getAttribute("value");

        Actions actions = new Actions(driver);

        if (text.isEmpty()) {
            actions.moveToElement(element).click().sendKeys(txt).build().perform();
            //actions.click(element).sendKeys(txt).perform();
        }
        else {
            element.clear();
            actions.moveToElement(element).click().sendKeys(txt).build().perform();
            //actions.click(element).sendKeys(txt).perform();
        }
    }

    public String get_Text(By locator) {
        WebElement element = wait_for_presence(locator);
        return element.getText();
    }
    public int get_Size(By locator) {
        List<WebElement> elements = wait_for_presence_list(locator);
        return elements.size();
    }

    public void upload_file(By locator, String filename) throws InterruptedException {
        Actions action = new Actions(driver);

        WebElement element = wait_for_presence(locator);
        action.moveToElement(element).click().build().perform();

        SmallWait(1000);

        try {
            StringSelection fileSelection = new StringSelection(System.getProperty("user.dir") + new ConfigLoader().initializeProperty().getProperty("uploadPath") + filename);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            SmallWait(1000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void refreshPage(){
        driver.navigate().refresh();
    }

    //---------------------------------------------------------------------------------------------//
    @AfterTest
    public static void SaveLogFile(){
        try {
            File logFile = new File("Log Result/test.log");
        }
        catch (Exception e) {
            System.out.println("Log save failed" +e);
        }
    }

    @AfterSuite
    public static void QuitBrowser() {
        //driver.quit();

        baseLogger.info("Browser quit successfully");
    }
}