/*
    * Product(Enter) - Customer(Select) - Order Type(SO) - Delivery Method(Carry Out) - Discount (Line Item) - Payment(Cash)
 */

package tests.POS;

import base.BaseClass;
import base.DataSource;
import org.testng.annotations.*;
import pages.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test_Case_1 extends BaseClass {
    private Home_Page homePage;
    private OrderEntry_Page orderEntryPage;

    @BeforeMethod
    public void initializePageObjects() {
        homePage = new Home_Page(driver);
        orderEntryPage = new OrderEntry_Page(driver);
    }

    @Test(description = "Verify that after successful login, the customer is successfully navigated to Order Entry page", priority = 1)
    public void verifyCustomerNavigationAfterLogin() throws InterruptedException {
        SmallWait(1000);

        homePage.clickOrderEntryMenu();

        SmallWait(2000);
        verifyCurrentUrl(jsonData.getJSONObject("tabURL").getString("orderEntry"));

        logger.info("User successfully navigated to the Order Entry page");
    }

    @Test(description = "Verify that the user can add  data successfully", priority = 2)
    public void verifyXDataEntry() throws InterruptedException {
        List<String> types = Arrays.asList("MP", "BP", "HP");
        List<String> quantities = Arrays.asList("2", "3", "5");
        List<String> discounts = Arrays.asList("FLOWER10", "SUMMER30", "");

        //orderEntryPage.selectCustomer("Linda Brown", "0000000004");

        //orderEntryPage.selectOrderType("IVCR");

        //orderEntryPage.enterTip("Sales", "100");

        //orderEntryPage.searchProductEnter("f001");
        //orderEntryPage.searchProductEnter("f002");

        //orderEntryPage.productInfo();
        //orderEntryPage.setProductQuantity(quantities);
        //orderEntryPage.setPriceType(types);
        //orderEntryPage.setProductDiscount(discounts);
        //orderEntryPage.selectWholeOrderDiscount("FLASH5");

        orderEntryPage.searchProductIcon("f003");

        //orderEntryPage.clickProductSearchIcon();
        //orderEntryPage.sortProduct("Product Code");

        orderEntryPage.moneyboxData();

        //orderEntryPage.carryOutDelivery("true","Holiday", "Mustafizur Rahman", "AOL - All Our Love");
        //orderEntryPage.willCallDelivery(homePage.getStoreName());
        orderEntryPage.recipientDelivery("old","system", "Kristi");
    }
}