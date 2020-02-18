package qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import qa.util.TestUtil;

public class TestBase {
	
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Workbook book;
	public static Sheet sheet1;
	public static String url;
	public static String browser;
	public static String excelpath = "C:\\Users\\NIKITHA\\eclipse-workspace\\LearnSelenium\\src\\main\\java\\qa\\testdata\\GmailTestData.xlsx";
	public static ExtentReports report;
	public static ExtentTest logger1;
	

	public void initialisation() throws EncryptedDocumentException, IOException {
		fis = new FileInputStream(excelpath);
		book = WorkbookFactory.create(fis);
		sheet1 = book.getSheet("Sheet1");
		
		int rowSize = sheet1.getPhysicalNumberOfRows();
		System.out.println("row size="+rowSize);
		for(int i=1;i<rowSize;i++) {
			
			url = sheet1.getRow(i).getCell(0).toString();
			browser = sheet1.getRow(i).getCell(1).toString();
			System.out.println("url= "+url);
			System.out.println("browser= "+browser);
			
			
			ExtentHtmlReporter extent = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\Reports\\"+"extentreport.html");
			report = new ExtentReports();
			report.attachReporter(extent);
			extent.flush();
			
		}
		
		if(browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\NIKITHA\\eclipse-workspace\\LearnSelenium\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\NIKITHA\\eclipse-workspace\\LearnSelenium\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(url);
	}
	
		
}
