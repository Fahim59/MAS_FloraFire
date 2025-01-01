package tests.Settings;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.EmployeeSettings_Page;

public class Employee_Settings extends BaseClass {
    private Home_Page homePage;
    private EmployeeSettings_Page employeeSettingsPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        employeeSettingsPage = new EmployeeSettings_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Employee Settings menu", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickEmployeeSettingsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("employeeSettings"));

        logger.info("User successfully navigated to the Employee Settings page");
    }

    public static String[] empSettingData() {
        String autoCheckout = "8";
        String autoLogOff = "30";
        String clockType = "12H";
        String dailyOverTime = "8";
        String weeklyOverTime = "40";

        String firstPayRoll = "01/01/2025";
        String payrollFrequency = "Monthly";

        return new String[] {autoCheckout, autoLogOff, clockType, dailyOverTime, weeklyOverTime, firstPayRoll, payrollFrequency};
    }

    @Test(description = "Verify that the user can add Employee settings data successfully", priority = 2)
    public void verifyEmployeeSettingsDataEntry() throws InterruptedException {
        String[] empSettingInfo = empSettingData();

        String message = jsonData.getJSONObject("successMessage").getString("employeeSettings");

        employeeSettingsPage.enterTimeClockData(empSettingInfo[0], empSettingInfo[1], empSettingInfo[2],
                empSettingInfo[3], empSettingInfo[4]);

        SmallWait(1000);

        Assert.assertEquals(message, employeeSettingsPage.getSuccessMessage());

        employeeSettingsPage.clickPayrollTab();

        employeeSettingsPage.enterPayrollData(empSettingInfo[5], empSettingInfo[6]);

        SmallWait(1000);

        Assert.assertEquals(message, employeeSettingsPage.getSuccessMessage());

        logger.info("Successfully added Employee Settings data");
    }
}