import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class DataProvider {
    ChromeDriver driver;

    @BeforeClass
    void setUp()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }
    @Test(dataProvider = "Provider")
    public void TC_A(String username, String password) throws InterruptedException {
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("submit-button")).click();

        boolean x = driver.findElement(By.className("shopping_cart_link")).isDisplayed();
        if (x) {
            System.out.println("Test case 1 passed");
        } else {
            System.out.println("Test case 1 failed");
        }
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(3000L);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(3000L);
    }
    @org.testng.annotations.DataProvider(name = "Provider")
    public Object[][] giveData() throws IOException{
        //path to your Excel file
        String excelFilePath = "C:\\Users\\Mostafa\\Downloads\\Final_login_data.xlsx";

        //load the Excel file
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // Determine number of rows and columns
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        // Read rows and columns from the Excel file
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = row.getCell(j).toString();
            }
        }

        // Close workbook and file stream
        workbook.close();
        fileInputStream.close();

        return data;
    }
    @AfterClass
    void close() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}