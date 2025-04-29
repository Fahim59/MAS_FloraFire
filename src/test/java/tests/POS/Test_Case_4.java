/*
    * Product(Enter) - Customer(Select) - Order Type(SW) - Delivery Method(Existing Recipient) - Discount (Delivery) - Tip - Payment(Credit Card)
 */

package tests.POS;

import base.BaseClass;
import org.testng.annotations.*;
import pages.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test_Case_4 extends BaseClass {
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
    public void testCaseFour() throws InterruptedException, IOException {
        List<String> types = Arrays.asList("MP", "HP", "BP");
        List<String> quantities = Arrays.asList("5", "4", "3");
        String discount = jsonData.getJSONObject("pos_tc4").getString("discount");

        String productCode = jsonData.getJSONObject("pos_tc4").getString("product");
        String recipientName = jsonData.getJSONObject("pos_tc4").getString("recipient");
        String customerId = jsonData.getJSONObject("pos_tc4").getString("customer");
        String orderType = jsonData.getJSONObject("pos_tc4").getString("orderType");
        String tipDept = jsonData.getJSONObject("pos_tc4").getString("tipDepartment");
        String tipAmount = jsonData.getJSONObject("pos_tc4").getString("tipAmount");
        String paymentMethod = jsonData.getJSONObject("pos_tc4").getString("payment_method");

        SmallWait(1000);

        orderEntryPage.setZoom80Percent();

        orderEntryPage.searchProductEnter(productCode);

        orderEntryPage.setPriceType(types);
        orderEntryPage.setProductQuantity(quantities);

        orderEntryPage.addExistingRecipient(recipientName, homePage.getStoreName());

        SmallWait(1000);

        orderEntryPage.selectCustomer(customerId);

        orderEntryPage.selectOrderType(orderType);

        orderEntryPage.selectWholeOrderDiscount(discount);

        orderEntryPage.enterTip(tipDept, tipAmount);

        SmallWait(1500);

        orderEntryPage.moneyboxData();

        takeScreenshot(className);

        Scroll(0, 500);

        orderEntryPage.makePayment(paymentMethod);
    }
}