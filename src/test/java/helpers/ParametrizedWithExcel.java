package helpers;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

public class ParametrizedWithExcel {
    public WebDriver driver;
    XSSFWorkbook excelWorkbook = null;
    XSSFSheet excelSheet = null;
    XSSFRow row = null;
    XSSFCell cell = null;

    @Test(dataProvider = "ExcelDataProvider")
    public void Test(String user, String pass) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("imapuser")));
        WebElement userName = driver.findElement(By.id("imapuser"));
        userName.clear();
        userName.sendKeys(user + "@mail.bg");

        WebElement password = driver.findElement(By.id("pass"));
        password.clear();
        password.sendKeys(pass);

        WebElement signInBtn = driver.findElement(By.linkText(""));
//        signInBtn.click();
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#widget_info h2")));
//
//        Assert.assertEquals(", " + user, driver.findElement(By.cssSelector("div#widget_info h2")).getText());

//		WebElement signOutBtn = driver.findElement(By.linkText(""));
//		signOutBtn.click();

//        Thread.sleep(5000);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.navigate().to("http://mail.bg/");
    }

    @BeforeTest
    public void beforeTest() {
        System.setProperty("webdriver.gecko.driver", "browsers/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://mail.bg/");
        driver.manage().window().maximize();
    }

    @DataProvider(name = "ExcelDataProvider")
    public Object[][] getData() throws IOException {

        FileInputStream f = new FileInputStream(
                "src\\test\\resources\\DataProvider.xlsx");

        excelWorkbook = new XSSFWorkbook(f);
        excelSheet = excelWorkbook.getSheet("Data");

        int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum();
        int colCount = excelSheet.getRow(0).getLastCellNum();

        Object data[][] = new Object[rowCount][colCount];

        for (int rNum = 1; rNum <= rowCount; rNum++) {
            for (int cNum = 0; cNum < colCount; cNum++) {
                System.out.print(getCellData("Data", cNum, rNum) + " ");
                data[rNum - 1][cNum] = getCellData("Data", cNum, rNum);
            }
            System.out.println();
        }
        return data;
    }

    public String getCellData(String sheetName, int colNum, int rowNum) {

        try {
            if (rowNum <= 0)
                return "";

            int index = excelWorkbook.getSheetIndex(sheetName);
            if (index == -1)
                return "";

            excelSheet = excelWorkbook.getSheetAt(index);
            row = excelSheet.getRow(rowNum);
            if (row == null)
                return "";

            cell = row.getCell(colNum);
            if (cell == null)
                return "";

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    return "";
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA:
                    return String.valueOf(cell.getNumericCellValue());
                default:
                    return String.valueOf(cell.getBooleanCellValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist in xls";
        }
    }

}
