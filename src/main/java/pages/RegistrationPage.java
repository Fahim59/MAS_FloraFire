package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By firstNameField = By.xpath("//input[@id='FirstName']");
    private final By lastNameField = By.xpath("//input[@id='LastName']");

    private final By emailField = By.xpath("//input[@id='Email']");
    private final By confirmEmailField = By.xpath("//input[@id='ConfirmEmail']");

    private final By secQstnOneField = By.xpath("//select[@id='SecurityQuestionOneId']");
    private final By secQstnOneAnsField = By.xpath("//input[@id='SecurityQuestionOneValue']");
    private final By secQstnTwoField = By.xpath("//select[@id='SecurityQuestionTwoId']");
    private final By secQstnTwoAnsField = By.xpath("//input[@id='SecurityQuestionTwoValue']");

    private final By passwordField = By.xpath("//input[@id='Password']");
    private final By confirmPassField = By.xpath("//input[@id='ConfirmPassword']");

    private final By registerBtn = By.xpath("//button[@id='register-button']");

    private final By regSuccessTextField = By.xpath("//p[@class='content']");
    private final By emailTextField = By.xpath("//div[@class='result']");

    private final By loginBtn = By.xpath("//a[normalize-space()='Go to Login Screen.']");

    private final By activationSuccessTextField = By.xpath("//p[@class='content']");

    public RegistrationPage enterFirstName(String firstName){
        write_Send_Keys(firstNameField, firstName);
        return this;
    }
    public RegistrationPage enterLastName(String lastName){
        write_Send_Keys(lastNameField, lastName);
        return this;
    }

    public RegistrationPage enterEmail(String email){
        write_Send_Keys(emailField, email);
        return this;
    }
    public RegistrationPage enterConfirmEmail(String email){
        write_Send_Keys(confirmEmailField, email);
        return this;
    }

    public RegistrationPage selectSecQstnOne(String question){
        select_Dropdown_Element(secQstnOneField, question);
        return this;
    }
    public RegistrationPage enterSecQstnOneAnswer(String answer){
        write_Send_Keys(secQstnOneAnsField, answer);
        return this;
    }
    public RegistrationPage selectSecQstnTwo(String question){
        select_Dropdown_Element(secQstnTwoField, question);
        return this;
    }
    public RegistrationPage enterSecQstnTwoAnswer(String answer){
        write_Send_Keys(secQstnTwoAnsField, answer);
        return this;
    }

    public RegistrationPage enterPassword(String password){
        write_Send_Keys(passwordField, password);
        return this;
    }
    public RegistrationPage enterConfirmPassword(String password){
        write_Send_Keys(confirmPassField, password);
        return this;
    }

    public RegistrationPage enterRegistrationDetails(String firstName, String lastName, String email, String cEmail, String question1, String answer1,
                                                     String question2, String answer2, String password, String cPassword){
        return enterFirstName(firstName).enterLastName(lastName).enterEmail(email).enterConfirmEmail(cEmail).
                selectSecQstnOne(question1).enterSecQstnOneAnswer(answer1).selectSecQstnTwo(question2).enterSecQstnTwoAnswer(answer2).
                enterPassword(password).enterConfirmPassword(cPassword);
    }

    public void clickRegisterBtn() {
        click_Element(registerBtn);
    }

    public String fetchRegSuccessText(){
        return get_Text(regSuccessTextField);
    }

    public String fetchEmailText(){
        return get_Text(emailTextField);
    }

    public void clickLoginPageBtn() {
        click_Element(loginBtn);
    }

    public String fetchActivationText(){
        return get_Text(activationSuccessTextField);
    }
}
