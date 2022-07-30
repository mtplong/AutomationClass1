package examples;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Demo_05_WorkWithSeleniumApi {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsEx;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		// ép kiểu tường minh
		jsEx = (JavascriptExecutor) driver;

		// time out cho việc tìm kiếm web element
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// chỉnh size của browser lên max size
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

//	@Test
	public void test_01_webBrowser() {
		By myAccountLocator = By.xpath("//div[@class='footer-container']//a[@title='My Account']");
		By btnCreateAccount = By.xpath("//a[@title='Create an Account']");

		// vào trang web bằng url
		driver.get("http://live.techpanda.org/");

		// click vào My Account ở footer
		driver.findElement(myAccountLocator).click();

		// Kiểm tra URL, Title
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		Assert.assertEquals(driver.getTitle(), "Customer Login");

		// click vào Create an account button
		driver.findElement(btnCreateAccount).click();

		// Kiểm tra URL, Title
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}

//	@Test
	public void test_02_textBox() {
		driver.get("http://live.techpanda.org/");
		driver.findElement(By.id("search")).sendKeys("hello everyone");
		Assert.assertEquals(driver.findElement(By.id("search")).getAttribute("maxlength"), "128");
	}

//	@Test
	public void test_03_button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.xpath("//a[text()='Đăng nhập']")).click();
		
		// verify button is Disable
		Assert.assertFalse(driver.findElement(By.xpath("//button[@class='fhs-btn-login']")).isEnabled());

		// input email, pass
//		driver.findElement(By.id("login_username")).sendKeys("0938447156");
//		driver.findElement(By.id("login_password")).sendKeys("123456789");
		
		// sử dụng javascript executor 
		removeAttributeByJSE(By.xpath("//button[@class='fhs-btn-login']"));

		// verify button is Enable
		Assert.assertTrue(driver.findElement(By.xpath("//button[@class='fhs-btn-login']")).isEnabled());
		
		sleepInSecond(3);
	}
	
//	@Test
	public void test_04_dropdownList() {
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		Select selectDate = new Select(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']")));
		Assert.assertEquals(selectDate.getOptions().size(), 32);
		sleepInSecond(3);
		selectDate.selectByValue("23");
		sleepInSecond(3);
	}
	
//	@Test
	public void test_05_checkbox() {
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.findElement(By.xpath("//input[@id='Newsletter']")).click();
		sleepInSecond(3);
	}
	
	@Test
	public void test_06_alert() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement btnDoubleClick = driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
		Actions action = new Actions(driver);
		action.doubleClick(btnDoubleClick).perform();
		sleepInSecond(3);
		
		Alert alert = driver.switchTo().alert();
		System.out.println("Alert text:" + alert.getText());
		alert.accept();
		sleepInSecond(3);
	}
	
	@Test
	public void test_07_dragAndDrop() {
		driver.get("https://demo.guru99.com/test/drag_drop.html");
		WebElement a = driver.findElement(By.xpath("//li[@id='credit2']"));
		WebElement b = driver.findElement(By.xpath("//ol[@id='bank']"));
		Actions action = new Actions(driver);
		action.dragAndDrop(a, b).build().perform();
		sleepInSecond(3);
		
	}

	public void sleepInSecond(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeAttributeByJSE(By by) {
		WebElement element = driver.findElement(by);
		jsEx.executeScript("arguments[0].removeAttribute('disabled')", element);
	}
	

}