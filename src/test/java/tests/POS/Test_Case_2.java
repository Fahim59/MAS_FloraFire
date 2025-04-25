/*
    * Product(Icon) - Customer(Add) - Order Type(SO) - Delivery Method(Will Call) - Discount (Order) - Payment(Check)
 */

package tests.POS;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test_Case_2 extends BaseClass {
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
        List<String> types = Arrays.asList("HP", "BP", "MP");
        List<String> quantities = Arrays.asList("3", "4", "5");
        String discount = "FIRSTORDER";

        SmallWait(1000);

        orderEntryPage.setZoom80Percent();

        orderEntryPage.searchProductIcon("f005");

        orderEntryPage.setPriceType(types);
        orderEntryPage.setProductQuantity(quantities);
        orderEntryPage.selectWholeOrderDiscount(discount);

        orderEntryPage.addNewCustomer(getFullName(), getAddress(), getState(), getCity(), "54789", getPhone(), getEmail(), "Yes");

        orderEntryPage.selectOrderType("Sales Order");

        orderEntryPage.willCallDelivery("false", homePage.getStoreName());

        orderEntryPage.moneyboxData();

        takeScreenshot(className);

        Scroll(0, 500);

        orderEntryPage.makePayment("Check");
    }
}