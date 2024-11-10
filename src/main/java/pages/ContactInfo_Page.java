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

import java.time.Duration;
import java.util.List;

public class ContactInfo_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public ContactInfo_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By contactInfoTab = By.xpath("//strong[normalize-space()='Contact Info']");

    private final By companyField = By.cssSelector("#CompanyName");

    private final By addressField = By.cssSelector("#Address");
    private final By addressContField = By.cssSelector("#AddressCont");

    private final By countryField = By.cssSelector("#CountryId");
    private final By stateField = By.cssSelector("#StateProvinceId");

    private final By cityField = By.cssSelector("#City");
    private final By zipField = By.cssSelector("#ZipCode");

    private final By businessPhoneField = By.cssSelector("#BusinessPhone");
    private final By mobileField = By.cssSelector("#Mobile");

    private final By additionalPhoneField = By.cssSelector("#AdditionalPhone");

    private final By aboutUsField = By.cssSelector("#ValueListId");
    private final By answerField = By.cssSelector("#ValueListValue");

    private final By saveBtn = By.xpath("//button[normalize-space()='Save And Next']");

    public ContactInfo_Page enterCompany(String company){
        write_Send_Keys(companyField, company);
        return this;
    }

    public ContactInfo_Page enterAddress(String address){
        write_Send_Keys(addressField, address);
        return this;
    }
    public ContactInfo_Page enterContAddress(String address){
        write_Send_Keys(addressContField, address);
        return this;
    }

    public ContactInfo_Page selectCountry(String country){
        select_Dropdown_Element(countryField, country);
        return this;
    }
    public ContactInfo_Page selectState(String state) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(stateField, state));
        select_Dropdown_Element(stateField, state);
        return this;
    }

    public ContactInfo_Page enterCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public ContactInfo_Page enterZip(String zip){
        write_Send_Keys(zipField, zip);
        return this;
    }

    public ContactInfo_Page enterBusinessPhone(String businessPhone){
        write_Send_Keys(businessPhoneField, businessPhone);
        return this;
    }
    public ContactInfo_Page enterMobile(String mobile){
        write_Send_Keys(mobileField, mobile);
        return this;
    }

    public ContactInfo_Page enterAdditionalPhone(String phone){
        write_Send_Keys(additionalPhoneField, phone);
        return this;
    }

    public ContactInfo_Page selectAboutUs(String aboutUs){
        select_Dropdown_Element(aboutUsField, aboutUs);
        return this;
    }
    public ContactInfo_Page enterAnswer(String answer){
        write_Send_Keys(answerField, answer);
        return this;
    }

    public ContactInfo_Page enterContactInfoDetails(String company, String address, String addressCont, String country, String state,
                                                    String city, String zip, String businessPhone, String mobile, String phone,
                                                    String aboutUs, String answer) throws InterruptedException {

        return enterCompany(company).enterAddress(address).enterContAddress(addressCont).selectCountry(country).selectState(state).
                enterCity(city).enterZip(zip).enterBusinessPhone(businessPhone).enterMobile(mobile).enterAdditionalPhone(phone).
                selectAboutUs(aboutUs).enterAnswer(answer);
    }

    public void clickSaveBtn() { click_Element(saveBtn); }

    public void clickContactInfoTab() { click_Element(contactInfoTab); }
}
