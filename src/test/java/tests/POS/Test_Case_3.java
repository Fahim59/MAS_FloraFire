/*
    * Product(Enter) - Customer(Select) - Order Type(SO) - Delivery Method(Carry Out) - Discount (Line Item) - Payment(Cash)
 */

package tests.POS;

import base.BaseClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Home_Page;
import pages.OrderEntry_Page;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test_Case_3 extends BaseClass {
    private Home_Page homePage;
    private OrderEntry_Page orderEntryPage;

    private String className;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        orderEntryPage = new OrderEntry_Page(driver);

        className = new Object(){}.getClass().getEnclosingClass().getSimpleName();
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Order Entry page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickOrderEntryMenu();

        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("orderEntry"));

        logger.info("User successfully navigated to the Order Entry page");
    }

    @Test(description = "Verify that the user can perform an Order", priority = 2)
    public void testCaseTwo() throws InterruptedException, IOException {
        List<String> types = Arrays.asList("BP", "HP", "MP");
        List<String> quantities = Arrays.asList("3", "4", "5");
        String discount = "SAVE50";

        SmallWait(1000);

        orderEntryPage.setZoom80Percent();

        orderEntryPage.searchProductEnter("f005");

        orderEntryPage.setPriceType(types);
        orderEntryPage.setProductQuantity(quantities);

        orderEntryPage.addNewRecipient(homePage.getStoreName());

        SmallWait(1000);

        orderEntryPage.selectCustomer("0000000010");

        orderEntryPage.selectOrderType("Sales Order");

        orderEntryPage.selectWholeOrderDiscount(discount);

        orderEntryPage.moneyboxData();

        takeScreenshot(className);

        Scroll(0, 500);

        orderEntryPage.makePayment("Gift Card");
    }
}