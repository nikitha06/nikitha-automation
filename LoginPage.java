package qa.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qa.base.TestBase;
import qa.util.TestUtil;

public class LoginPage extends TestBase{
	
	@FindBy(xpath="//input[@id='identifierId']")
	WebElement username;
	
	@FindBy(xpath="//span[text()='Next']")
	WebElement nextbutton;
	
	@FindBy(xpath="//input[@name='password']")
	WebElement password;
	
	@FindBy(xpath="//a[text()='Sign out']")
	WebElement signout;
	
	@FindBy(xpath="//a[@tabindex='0']//ancestor::div[3]//following-sibling::div//descendant::a")
	WebElement account;
	
	
	public LoginPage() {

		PageFactory.initElements(driver, this);

		
	}
	
	public String validateTitle() {
		String title = driver.getTitle();
		System.out.println("Title of the page is "+title);
		
		return title;
		
	}
	
	public void validateLoginPage(String uname, String pwd) throws IOException {
		
		System.out.println(uname);
		System.out.println(pwd);
		username.sendKeys(uname);
		
		nextbutton.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		password.sendKeys(pwd);
		nextbutton.click();

		account.click();
		TestUtil.takeScreenshot();
		signout.click();
		
	}

}
