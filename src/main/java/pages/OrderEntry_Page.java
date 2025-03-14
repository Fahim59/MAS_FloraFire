package pages;

import base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderEntry_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public OrderEntry_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final String cartTable = "//table[@role='table']/tbody";
    private final By productRows = By.xpath(cartTable+"/tr");

    /*
     * Product Addition in Cart
     */

    private final By searchProductField = By.xpath("(.//*[@placeholder='Search Keyword (Product Name, Code)'])[1]");
    private final By searchProductIconField = By.xpath("(.//mat-icon[text()='search'])[1]");

    private final By searchProductField2 = By.xpath("(.//*[@placeholder='Search Keyword (Product Name, Code or Description)'])[1]");
    private final By sortProductDropdown = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");

    private final By productCode = By.xpath("(.//div[@class='info text-truncate']/span)[1]");
    private final By productQuantity = By.xpath("(.//div[@class='stock ng-star-inserted'])[1]");
    private final By addToOrderBtn = By.xpath("//span[contains(text(),'Add To Order')]");
    private final By closeBtn = By.xpath("//span[contains(text(),'Close')]");

    public OrderEntry_Page searchProductEnter(String code) throws InterruptedException {
        SmallWait(1000);

        WebElement searchField = wait_for_presence(searchProductField);
        searchField.clear();
        searchField.sendKeys(code);
        searchField.sendKeys(Keys.ENTER);

        SmallWait(2000);

        return this;
    }

    public OrderEntry_Page clickProductSearchIcon() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(searchProductIconField);

        return this;
    }
    public OrderEntry_Page enterProductInfo(String code) throws InterruptedException {
        SmallWait(500);
        write_Send_Keys(searchProductField2, code);

        SmallWait(2000);

        return this;
    }
    public void clickAddToOrderBtn(String code) throws InterruptedException {
        SmallWait(500);
        String proCode = get_Text(productCode);

        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(get_Text(productQuantity));
        int proStock = 0;

        if (matcher.find()) {
            proStock = Integer.parseInt(matcher.group());
        }

        if(proCode.equalsIgnoreCase(code)){
            if(proStock > 0){
                click_Element_Js(addToOrderBtn);
            }
            else{
                logger.info("Insufficient Stock");
            }
        }
        else{
            logger.info("Product Code Mismatch");
        }

        SmallWait(2000);

        click_Element_Js(closeBtn);
    }

    public OrderEntry_Page searchProductIcon(String code) throws InterruptedException {
        clickProductSearchIcon().enterProductInfo(code).clickAddToOrderBtn(code);
        return this;
    }

    public void sortProduct(String type) throws InterruptedException {
        SmallWait(2000);

        if(!get_Text(sortProductDropdown).equals(type)){
            click_Element(sortProductDropdown);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
        }
    }

    /*
     * Price Type, Quantity, Line Item Discount
     */

    public void setPriceType(List<String> types) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement priceTypeField = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[4]"));
            String priceType = priceTypeField.getText();

            System.out.println("Price Type: " +priceType);

            String type = (l <= types.size()) ? types.get(l - 1) : "BP";

            if(!priceType.equals(type)){
                priceTypeField.click();
                SmallWait(200);
                js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
            }
        }
    }

    public void setProductQuantity(List<String> quantities) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement quantityField = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[5]//input"));

            String quantity = (l <= quantities.size()) ? quantities.get(l - 1) : "1";

            quantityField.clear();
            quantityField.sendKeys(quantity);
        }
    }

    public void setProductDiscount(List<String> discounts) throws InterruptedException {
        SmallWait(1000);

        for(int l = 1; l<= get_Size(productRows); l++){
            WebElement discountField = driver.findElement(By.xpath(cartTable+ "/tr["+l+"]/td[6]//input"));

            String discount = (l <= discounts.size()) ? discounts.get(l - 1) : "";

            discountField.click();
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+discount+"']")));
        }
    }

    /*
     * Customer Selection
     */

    private final By searchCustomerIconField = By.xpath("(.//mat-icon[text()='search'])[2]");
    private final By searchCustomerField = By.xpath("(.//*[contains(@placeholder, 'Search Keyword (Customer Name')])[2]");

    private final String customerTable = "//table[@role='table']/tbody";
    private final By rows = By.xpath(customerTable+"/tr");

    public OrderEntry_Page clickCustomerSearchIcon() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(searchCustomerIconField);

        return this;
    }
    public OrderEntry_Page enterCustomerName(String name) throws InterruptedException {
        SmallWait(1000);
        write_Send_Keys(searchCustomerField, name);

        return this;
    }
    public void customerInfo(String id) throws InterruptedException {
        SmallWait(1500);

        for(int l = 1; l<= get_Size(rows); l++){

            String customerId = driver.findElement(By.xpath(customerTable+ "/tr["+l+"]/td[2]")).getText();
            WebElement selectBtn = driver.findElement(By.xpath(customerTable+ "/tr["+l+"]/td[6]"));

            if(customerId.equalsIgnoreCase(id)){
                selectBtn.click();
                SmallWait(2000);

                customerType = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Type')]]/span[2]")).getText();
                discount = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Discount')]]/span[2]")).getText();
                deliveryCharge = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Delivery Charge')]]/span[2]")).getText().replaceAll("[^0-9.]", "");
                taxExempt = driver.findElement(By.xpath("//div[span[1][contains(text(), 'Tax Exempt')]]/span[2]")).getText();

                logger.info("Customer Type: {} \nDiscount Amount: {} \nDelivery Charge: {} \nTax Exempt: {}",
                        customerType, discount, deliveryCharge, taxExempt);

                WebElement addBtn = driver.findElement(By.xpath("(//span[contains(text(),'Add')])[3]"));
                addBtn.click();

                logger.info("Customer {} added", id);

                break;
            }
        }
    }

    public void selectCustomer(String name, String id) throws InterruptedException {
        clickCustomerSearchIcon().enterCustomerName(name).customerInfo(id);
    }

    /*
     * Order Type
     */

    private final By orderTypeField = By.xpath("(//div[contains(@id,'mat-select-value')])[2]");

    public OrderEntry_Page selectOrderType(String type) throws InterruptedException {
        SmallWait(1000);

        if(!get_Text(orderTypeField).equals(type)){
            click_Element(orderTypeField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+type+"']")));
        }

        return this;
    }

    /*
     * Moneybox
     */

    /*
     *  Whole Order Discount or Delivery Discount
     */

    private final By wholeOrderDiscount = By.xpath("(.//mat-label[text()='Discount Code'])[1]");

    public OrderEntry_Page selectWholeOrderDiscount(String discount) throws InterruptedException {
        SmallWait(1000);

        click_Element(wholeOrderDiscount);
        SmallWait(200);
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+discount+"']")));

        return this;
    }

    /*
     * Tip
     */

    private final By addTipBtn = By.xpath("//div[@class='mat-ripple add-tip mb-2 ng-star-inserted']");
    private final By departmentField = By.xpath("(//div[contains(@id,'mat-select-value')])[5]");
    private final By tipAmountField = By.xpath("//input[@formcontrolname='tipAmount']");

    private final By confirmBtn = By.xpath("//span[normalize-space()='Confirm']");

    public OrderEntry_Page clickTipButton() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(addTipBtn);

        return this;
    }

    public OrderEntry_Page selectDepartment(String dept) throws InterruptedException {
        SmallWait(1500);

        if(!get_Text(departmentField).equals(dept)){
            click_Element(departmentField);
            SmallWait(200);
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[normalize-space()='"+dept+"']")));
        }

        return this;
    }

    public OrderEntry_Page enterTipAmount(String amount){
        write_Send_Keys(tipAmountField, amount);

        return this;
    }

    public void clickConfirmButton() throws InterruptedException {
        SmallWait(1000);
        click_Element_Js(confirmBtn);

        tip = true;
    }

    public void enterTip(String dept, String amount) throws InterruptedException {
        clickTipButton().selectDepartment(dept).enterTipAmount(amount).clickConfirmButton();
    }

    /*
     * Payment
     */
}