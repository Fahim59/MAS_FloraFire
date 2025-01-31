package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Users_Page extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public Users_Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    private final By Field = By.xpath("");

    private final String userTable = "//datatable-selection[1]/datatable-scroller[1]";
    private final By rows = By.xpath(userTable+"/datatable-row-wrapper");

    public void verifyRoleAddition(String role) throws InterruptedException {
        SmallWait(2500);
        int flag = 0;

        for(int l = 1; l<= get_Size(rows); l++){

            String userName = driver.findElement(By.xpath(userTable+ "/datatable-row-wrapper["+l+"]/datatable-body-row[1]/div[2]/datatable-body-cell[2]/div[1]/div[1]")).getText();
            System.out.println("User Name: " +userName);

            if(userName.contains(role)){
                logger.info("User {} found", role);
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            Assert.fail("User permission task fail...");
        }
    }
}