package tests;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.*;

import java.util.Map;

public class GiftCards extends BaseClass {
    private Home_Page homePage;
    private GiftCard_Page giftCardPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        giftCardPage = new GiftCard_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Gift Card List page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickGiftCardsMenu();

        SmallWait(1000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("giftCardList"));

        logger.info("User successfully navigated to the Gift Card List page");
    }

    public static String[] giftCardData(Map<String, String> valueData) {
        /*
         * data[0] = type
         * data[1] = number
         * data[2] = reason
         * data[3] = expiry
         * data[4] = status
         * data[5] = balance
         * data[6] = customer
         */

        String type = valueData.get("Type");
        String number = valueData.get("Number");
        String reason = valueData.get("Reason");
        String expiry = valueData.get("Expiry");
        String status = valueData.get("Status");
        String balance = valueData.get("Balance");
        String customer = valueData.get("Customer");

        return new String[] {type, number, reason, expiry, status, balance, customer};
    }

    @Test(description = "Verify that the user can add Gift Card data successfully", dataProvider = "excelData", dataProviderClass = DataSource.class, priority = 2)
    @DataSource.SheetName("GiftCard")
    public void verifyGift_CardDataEntry(Map<String, String> data) throws InterruptedException {
        String[] giftCardInfo = giftCardData(data);

        giftCardPage.clickNewGiftCardButton();

        giftCardPage.enterGiftCardDetails(giftCardInfo[0], giftCardInfo[1], giftCardInfo[2], giftCardInfo[3],
                giftCardInfo[4], giftCardInfo[5], giftCardInfo[6]);

        SmallWait(1000);

        giftCardPage.verifyGiftCardAddition(giftCardInfo[1]);

        logger.info("Successfully added data - {}", giftCardInfo[1]);
    }
}