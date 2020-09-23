package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
		baseUrl = baseUrl = "http://localhost:" + port;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
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
	@Order(3)
	public void SignUpTest() throws InterruptedException {
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
	@Order(4)
	public void noteTest() throws InterruptedException {
		// strings for original note
		String noteTitle = "Get groceries";
		String noteDescription = "Need milk and eggs";
		// strings for updated note
		String newTitle = "Fight crime";
		String newDescription = "Karate is useful";
		// get to login
		driver.get(baseUrl + "/login");
		// init login page and login
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		// should be redirected to home page
		// webdriver gets page
		driver.get(baseUrl + "/home");
		// init home page and add note
		HomePage homePage = new HomePage(driver);
		homePage.addNote(false, noteTitle, noteDescription);
		// init result page, click link to go back home
		ResultPage resultPage = new ResultPage(driver);
		// click link back to home
		resultPage.clickHomeAnchor();
		// get first note/newly inserted note
		Note insertedNote = homePage.getFirstNote();
		// check to see that the note was posted
		Assertions.assertEquals(noteTitle, insertedNote.getNoteTitle());
		Assertions.assertEquals(noteDescription, insertedNote.getNoteDescription());
		// edit the note
		System.out.println(driver.getCurrentUrl());
		homePage.addNote(true, newTitle, newDescription);
		// click link back to home
		resultPage.clickHomeAnchor();
		// get first note/newly updated note
		Note updatedNote = homePage.getFirstNote();
		Assertions.assertEquals(newTitle, updatedNote.getNoteTitle());
		Assertions.assertEquals(newDescription, updatedNote.getNoteDescription());
		// delete the note
		homePage.deleteNote();
		// click link back to home
		resultPage.clickHomeAnchor();
		// check to see if the note was deleted
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getFirstNote());
	}

	@Test
	@Order(5)
	public void credentialsTest() {
		// credentials to be inserted and tested
		String url = "http://twitter.com/";
		String username = "meepz";
		String password = "spooki520boi";
		// credentials to be updated
		String newUrl = "clubpenguin.com";
		String newUsername = "fightrOfDaNightMan";
		String newPassword = "milkSteak";
		// signup
		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);
		// get login page, init login page, login
	    driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseUrl + "/home");
        // init home page, insert new credentials
		HomePage homePage = new HomePage(driver);
//		System.out.println(driver.getCurrentUrl());
		homePage.addCredential(false, url, username, password);
		// init result page and click link back to home page
//        driver.get(baseUrl + "/result");
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickHomeAnchor();
		// need to check to see that it was posted
		Credential insertedCredential = homePage.getFirstCredential();
		// make sure password was encrypted in the case that decrypt wasn't called
        Assertions.assertNotEquals(insertedCredential.getPassword(), homePage.getPasswordEnc());
        Assertions.assertEquals(url, insertedCredential.getUrl());
		Assertions.assertEquals(username, insertedCredential.getUsername());
		Assertions.assertNotEquals(password, insertedCredential.getPassword());
		Assertions.assertEquals(password, homePage.getPasswordDecrypted());

		homePage.addCredential(true, newUrl, newUsername, newPassword);
		resultPage.clickHomeAnchor();

		Credential updatedCredential = homePage.getFirstCredential();
		Assertions.assertNotEquals(updatedCredential.getPassword(), homePage.getPasswordEnc());
		Assertions.assertEquals(newUrl, updatedCredential.getUrl());
		Assertions.assertEquals(newUsername, updatedCredential.getUsername());
		Assertions.assertNotEquals(newPassword, updatedCredential.getPassword());
		Assertions.assertEquals(newPassword, homePage.getPasswordDecrypted());

		homePage.deleteCredential();
		resultPage.clickHomeAnchor();

        Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getFirstCredential());
	}

}
