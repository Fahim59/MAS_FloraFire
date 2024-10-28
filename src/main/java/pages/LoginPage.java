package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By emailField = By.xpath("//input[@id='Email']");

    private final By passwordField = By.xpath("//input[@id='Password']");

    private final By rememberMeField = By.xpath("//input[@id='RememberMe']");

    private final By loginBtn = By.xpath("//button[normalize-space()='Log in']");

    public LoginPage enterEmail(String email){
        write_Send_Keys(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password){
        write_Send_Keys(passwordField, password);
        return this;
    }

    public LoginPage clickRememberBtn(){
        click_CheckBox(rememberMeField);
        return this;
    }

    public LoginPage enterLoginDetails(String email, String password){
        return enterEmail(email).enterPassword(password).clickRememberBtn();
    }

    public void clickLoginBtn() {
        click_Element(loginBtn);
    }
}
