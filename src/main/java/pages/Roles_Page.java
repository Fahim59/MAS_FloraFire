package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Roles_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Roles_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By newRoleButton = By.xpath("//button[normalize-space()='New role']");

    private final By roleNameField = By.xpath("//input[@formcontrolname='name']");

    private final By defaultCheckboxField = By.xpath("//input[@formcontrolname='isDefault']");
    private final By publicCheckboxField = By.xpath("//input[@formcontrolname='isPublic']");

    private final By saveBtn = By.xpath("(//button[contains(text(),'Save')])[1]");

    public void clickNewRoleButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(newRoleButton);
    }

    public Roles_Page enterRoleName(String name){
        write_Send_Keys(roleNameField, name);
        return this;
    }

    public Roles_Page selectRoleType(){
        click_Element(defaultCheckboxField);
        click_Element(publicCheckboxField);
        return this;
    }

    public void clickSaveButton() throws InterruptedException {
        SmallWait(1000);
        click_Element(saveBtn);
    }

    public void createNewRole(String name) throws InterruptedException {
        enterRoleName(name).selectRoleType().clickSaveButton();
    }

    private final String roleTable = "//datatable-selection[1]/datatable-scroller[1]";
    private final By rows = By.xpath(roleTable+"/datatable-row-wrapper");

    public void verifyRoleAddition(String role) throws InterruptedException {
        SmallWait(2500);
        int flag = 0;

        for(int l = 1; l<= get_Size(rows); l++){

            String roleName = driver.findElement(By.xpath(roleTable+ "/datatable-row-wrapper["+l+"]/datatable-body-row[1]/div[2]/datatable-body-cell[2]/div[1]/div[1]")).getText();
            System.out.println("Role Name: " +roleName);

            if(roleName.contains(role)){
                logger.info("Role {} found", role);
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            Assert.fail("Role addition fail...");
        }
    }
}