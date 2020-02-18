package qa.testcases;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import qa.base.TestBase;
import qa.pages.LoginPage;
import qa.util.TestUtil;

public class LoginPageTest extends TestBase{
	
	
	 public LoginPageTest() { 
		 // TODO Auto-generated constructor stub 
		 super(); 
	 }
	 
	public LoginPage loginpage;
	@BeforeMethod
	public void setUp() throws EncryptedDocumentException, IOException {
		initialisation();
		loginpage = new LoginPage();
		
	}
	
	@Test(priority=1)
	
	public void titleTest() throws IOException {
		String title = loginpage.validateTitle();
		Assert.assertEquals(title, "Sign in – Google accounts");
		TestUtil.takeScreenshot();
		
	}
	@DataProvider
	public Object[][] GmailTestData() throws EncryptedDocumentException, IOException{
		Object[][] data = TestUtil.getTestData("TestData");
		return data;
		
	
	}
	
	@Test(priority=2,dataProvider="GmailTestData")
	public void loginTest(String Username, String Password) throws IOException{
		
		loginpage.validateLoginPage(Username, Password);
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
