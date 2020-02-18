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
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import qa.util.TestUtil;

public class TestBase {
	
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Workbook book;
	public static Sheet sheet1;
	public static String url;
	public static String browser;
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extentreport;
	public static ExtentTest extenttest;
	public static String excelpath = "C:\\Users\\NIKITHA\\eclipse-workspace\\LearnSelenium\\src\\main\\java\\qa\\testdata\\GmailTestData.xlsx";
	
	
	@BeforeTest
	public void beforeTest() {
		
		reporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\reports\\"+"extentreport.html");
		extentreport = new ExtentReports();
		extentreport.attachReporter(reporter);
		System.out.println("before test parent");
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.FAILURE) {
			extenttest.log(Status.FAIL, "Testcase failed is "+result.getName()); //to add name in extent report
			extenttest.log(Status.FAIL, "Test case failed is "+result.getThrowable().getMessage()); //to add error and exception in extent report
			extenttest.fail("Test case failed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshot()).build());
		}
		
		else if(result.getStatus()==ITestResult.SUCCESS) {
			extenttest.log(Status.PASS, "Testcase passed is "+result.getName());
			extenttest.pass("Test case failed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshot()).build());
		}
		
		else if (result.getStatus()==ITestResult.SKIP) {
			extenttest.log(Status.SKIP, "Test case skipped is "+result.getName());
		}
		extentreport.flush();
		System.out.println("after method parent");
		driver.quit();
	}
	
	

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
