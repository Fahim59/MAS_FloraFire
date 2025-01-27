package tests.Settings.Delivery;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.Home_Page;
import pages.Settings.Delivery.Calendar_Page;

import java.util.Map;

public class Calendar extends BaseClass {
    private Home_Page homePage;
    private Calendar_Page calendarPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        calendarPage = new Calendar_Page(driver);
    }

    @Test(description = "Verify that after successful login, the user is successfully navigated to Calendar page", priority = 1)
    public void verifyUserNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickCalendarMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("calendar"));

        logger.info("User successfully navigated to the Calendar page");
    }

    public static String[] modeData(Map<String, String> valueData) {
        /*
         * data[0] = date
         * data[1] = mode
         */

        String date = valueData.get("Date");
        String mode = valueData.get("Mode");

        return new String[] {date, mode};
    }

    @Test(description = "Verify that the user can add Calendar data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("Calendar")
    public void verifyCalendarDataEntry(Map<String, String> data) throws InterruptedException {
        SmallWait(2000);

        String[] modeInfo = modeData(data);

        calendarPage.saveDeliveryMoodOnCalendar(modeInfo[0], modeInfo[1]);

        logger.info("Successfully added Calendar data");
    }
}