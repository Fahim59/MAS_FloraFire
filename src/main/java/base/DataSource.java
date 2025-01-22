package base;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.*;
import utils.ConfigLoader;
import utils.ExcelReader;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.*;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataSource {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface SheetName {
        String value();
    }

    @DataProvider(name = "ValueListData")
    public Object[][] getClientPortalData(Method method) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();
        List<Map<String, String>> allData = reader.getData(new ConfigLoader().initializeProperty().getProperty("dataFile"), "ValueList");

        int startRow = 0, endRow = 0;

        if (method.getName().contains("AccountClass")) { startRow = 0; endRow = 2; }
        else if (method.getName().contains("ARStatementInvoice")) { startRow = 3; endRow = 5; }
        else if (method.getName().contains("CustomerStatus")) { startRow = 6; endRow = 9; }
        else if (method.getName().contains("InvoicePaymentSchedule")) { startRow = 10; endRow = 11; }
        else if (method.getName().contains("PaymentTerms")) { startRow = 12; endRow = 14; }
        else if (method.getName().contains("PriceSheetType")) { startRow = 15; endRow = 16; }
        else if (method.getName().contains("ReferredByValue")) { startRow = 17; endRow = 20; }
        else if (method.getName().contains("EmployeeDepartment")) { startRow = 21; endRow = 25; }
        else if (method.getName().contains("EmployeeStatus")) { startRow = 26; endRow = 29; }
        else if (method.getName().contains("EmployeeContactPersonRelation")) { startRow = 30; endRow = 33; }
        else if (method.getName().contains("GiftCardReason")) { startRow = 34; endRow = 35; }
        else if (method.getName().contains("CareCode")) { startRow = 36; endRow = 37; }
        else if (method.getName().contains("ProductDepartment")) { startRow = 38; endRow = 41; }
        else if (method.getName().contains("ProductType")) { startRow = 42; endRow = 45; }
        else if (method.getName().contains("VehicleStatus")) { startRow = 46; endRow = 49; }
        else if (method.getName().contains("TipsCategory")) { startRow = 50; endRow = 53; }
        else if (method.getName().contains("MiscIncomeReason")) { startRow = 54; endRow = 55; }
        else if (method.getName().contains("PaidOutReason")) { startRow = 56; endRow = 58; }

        List<Map<String, String>> filteredData = allData.subList(startRow, endRow + 1);

        Object[][] data = new Object[filteredData.size()][1];
        for (int i = 0; i < filteredData.size(); i++) {
            data[i][0] = filteredData.get(i);
        }

        return data;
    }

    @DataProvider(name = "excelData")
    public Object[][] getExcelData(Method method) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();

        SheetName sheetNameAnnotation = method.getAnnotation(SheetName.class);
        if (sheetNameAnnotation == null) {
            throw new IllegalArgumentException("Missing @SheetName annotation for method: " + method.getName());
        }
        String sheetName = sheetNameAnnotation.value();

        List<Map<String, String>> testData = reader.getData(
                new ConfigLoader().initializeProperty().getProperty("dataFile"), sheetName);

        List<Map<String, String>> filteredData = testData.stream()
                .filter(row -> row != null && !row.isEmpty())
                .filter(row -> row.values().stream().anyMatch(val -> val != null && !val.trim().isEmpty()))
                .collect(Collectors.toList());

        Object[][] data = new Object[filteredData.size()][1];
        for (int i = 0; i < filteredData.size(); i++) {
            data[i][0] = filteredData.get(i);
        }

        return data;
    }
}
