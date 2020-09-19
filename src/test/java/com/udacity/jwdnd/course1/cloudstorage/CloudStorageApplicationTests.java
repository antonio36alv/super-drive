package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	private String baseUrl;
	// user credentials to be used
	private String firstName = "Matt";
	private String lastName = "Murdock";
	private String username = "avocodosAtLaw";
	private String password = "hellskitchen";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
		baseUrl = baseUrl = "http://localhost:" + port;
//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

//	@AfterAll
//	public static void affterAll() {
//
//	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void checkUnauthorizedAccessTest() {
        String loginUrl = baseUrl + "/login";
		// driver gets home page
		driver.get(baseUrl + "/home");
		// ensure it redirects to login page
		Assertions.assertEquals(loginUrl, driver.getCurrentUrl());
        // driver get result page
		driver.get(baseUrl + "/result");
		// ensure it redirects to login page
		Assertions.assertEquals(loginUrl, driver.getCurrentUrl());
	}

	@Test
	public void SignUpTest() {
//		Assertions.assertEquals();
		String loginUrl = baseUrl + "/login";
		String homeUrl = baseUrl + "/home";

        driver.get(baseUrl + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password);

        driver.get(baseUrl + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
		// check to see that home page is current page after login
        Assertions.assertEquals(homeUrl, driver.getCurrentUrl());

        HomePage homePage = new HomePage(driver);
		// logout
        homePage.logout();
        // ensure the website redirected to login page after logout
		Assertions.assertEquals(loginUrl, driver.getCurrentUrl());
	}

	@Test
	public void noteTest() throws InterruptedException {

		String noteTitle = "Get groceries";
		String noteDescription = "Need milk and eggs";

//		driver.get(baseUrl + "/signup");

//		SignupPage signupPage = new SignupPage(driver);
//		signupPage.signup(firstName, lastName, username, password);

		driver.get(baseUrl + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		System.out.println(driver.getCurrentUrl());
		driver.get(baseUrl + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.addNote(noteTitle, noteDescription);

		driver.get(baseUrl + "/result");

		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickHomeAnchor();

		driver.get(baseUrl + "/home");

		Note note = homePage.getFirstNote();
		// check to see that the note was posted
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
	}

}
