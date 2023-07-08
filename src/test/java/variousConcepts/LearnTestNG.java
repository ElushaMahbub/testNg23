package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser = null;
	String url;

	@BeforeSuite
	public void readConfig() {

		// FileReader // BufferedReader // InputStream // Scanner --> these are 4
		// classes offered by Java to read a file

		try {

			InputStream input = new FileInputStream("src\\test\\java\\config\\config.properties");

			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("browser used:  " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// Element List
	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@id='password']");
	By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By dashboardHeaderField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By customerMenueField = By.xpath("//span[contains(text(),'Customers')]");
	By addCustomerMenueField = By.xpath("//a[contains(text(),'Add Customer')]");
	By addCustomerHeaderField = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By companyHeaderField = By.xpath("//*[@id=\"rform\"]/div[1]/div[1]/div[2]/label");
	By fullNameField = By.xpath("//input[@id='account']");
	By CompanyDropdownField = By.xpath("//select[@id='cid']");
	// By companyNamefield = By.xpath("//option[text()='Amazon']");
	By emailIdfield = By.xpath("//input[@id='email']");
	By phoneNumberfield = By.xpath("//input[@id='phone']");
	By addressField = By.xpath("//input[@id='address']");
	By cityField = By.xpath("//input[@id='city']");
	By stateField = By.xpath("//input[@id='state']");
	By zipCodeField = By.xpath("//input[@id='zip']");
	By countryField = By.xpath("//select[@id='country']");
	By tagsDropDownField = By.xpath("//select[@id='tags']");
	By currencyDropDownField = By.xpath("//select[@id='currency']");
	By groupDropdownField = By.xpath("//select[@id='group']");

	//Login data 
	String USER_NAME = "demo@techfios.com";
	String PASSWORD = "abc123";
	// testdata or mockdata
	String DASHBOARD_HEADER_TEXT = "Dashboard";
	String ADDCUSTOMER_HEADER_TEXT = "Company";
	String FULL_NAME = "Selenium Feb2023";// everytime we need to try new data for new test case
	String COMPANY = "Amazon";
	String EMAIL = "sueluri@bd.com";
	String PHONE = "0990007";
	String COUNTRY = "Bangladesh";

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("edge")) {

			System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			System.out.println("Select proper browser");
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

//	@Test
	public void logIn() {

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passwordField).sendKeys(PASSWORD);
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), DASHBOARD_HEADER_TEXT,
				"Dashboard page not found");
	}

	@Test
	public void addCustomer() throws InterruptedException {

		logIn();
		Thread.sleep(2000);
		driver.findElement(customerMenueField).click();
		driver.findElement(addCustomerMenueField).click();
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(companyHeaderField).getText(), ADDCUSTOMER_HEADER_TEXT,
				"Add customer page not found");
		// Assert.assertEquals(driver.findElement(addCustomerHeaderField).getText(),
		// ADDCUSTOMER_HEADER_TEXT, "Add customer page not found");

		driver.findElement(fullNameField).sendKeys(FULL_NAME + randomNumGenerator(999));

		selectFromDropdown(driver.findElement(CompanyDropdownField), COMPANY);

		driver.findElement(emailIdfield).sendKeys(randomNumGenerator(9999) + EMAIL);
		driver.findElement(phoneNumberfield).sendKeys(PHONE + randomNumGenerator(99));

		selectFromDropdown(driver.findElement(countryField), COUNTRY);
	}

	private void selectFromDropdown(WebElement element, String visibleText) {

		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	}

	private int randomNumGenerator(int bound) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(bound);
		return generatedNum;

	}

	// @AfterMethod
	public void teardown() {

		driver.close();
		driver.quit();
	}
}
