package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import constants.KeyConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import utils.ReadConfig;
import static constants.IBrowserConstant.*;
import java.time.Duration;

public class TestBase {

	WebDriver driver;
	ReadConfig config;
	
	// Object Pages
	protected HomePage homePage;
	
	public WebDriver getDriver() {
		return driver;
	}
	
	@BeforeClass
	public void beforeClassSetUp() {
		config = new ReadConfig();
	}
	
	// browser parameter will come from testng suite or runtime
	// @Optional("chrome") will take care in case we miss browser param value
	@Parameters(BROWSER)
	@BeforeMethod
	public void setUpDriver(@Optional(FIREFOX) String browserName) {
		driver = initializingBrowser(browserName);
		driver.manage().window().maximize();
		int pageLoadTime = Integer.parseInt(config.getValue(KeyConfig.pageLoadTime));
		int implicitWaitTime = Integer.parseInt(config.getValue(KeyConfig.impliciteWaitLoad));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
		driver.get(config.getValue(KeyConfig.url));
		initObjectClass();
	}
	
	public WebDriver initializingBrowser(String browserName) {
		switch (browserName) {
		case CHROME:
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver();
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver();
		case EDGE:
			WebDriverManager.edgedriver().setup();
			return new EdgeDriver();
		case SAFARI:
			WebDriverManager.safaridriver().setup();
			return new SafariDriver();
		default:
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver();
		}
	}
	
	public void initObjectClass() {
		homePage = new HomePage(driver);
	}
	
	@AfterMethod
	public void tearUp() {
		//driver.quit();
	}
	
}
