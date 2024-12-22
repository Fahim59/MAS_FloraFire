package base;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ConfigLoader;
import utils.ExcelReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataSource {
    int startRow, endRow;

    @DataProvider(name = "clientPortalData")
    public Object[][] getMemberData() throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();

        List<Map<String, String>> testData = reader.getData(
                new ConfigLoader().initializeProperty().getProperty("dataFile"), "Value");

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

    @DataProvider(name = "ValueListData")
    public Object[][] getClientPortalData(Method method) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();
        List<Map<String, String>> testData = reader.getData(new ConfigLoader().initializeProperty().getProperty("dataFile"), "ValueList");

        if (method.getName().contains("AbandonReason")) { startRow = 0; endRow = 1; }
        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }
//        else if (method.getName().contains("testSecondOptionData")) { startRow = 2; endRow = 3; }

        Object[][] data = new Object[endRow - startRow + 1][1];
        for (int i = startRow, j = 0; i <= endRow; i++, j++) {
            data[j][0] = testData.get(i);
        }
        return data;
    }
}
