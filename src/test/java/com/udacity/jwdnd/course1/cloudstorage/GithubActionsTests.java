package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.*;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class GithubActionsTests {

	@LocalServerPort
	public int port;

	@Autowired
	private ApplicationContext context;

    @Container
	public static BrowserWebDriverContainer firefox =
			(BrowserWebDriverContainer) new BrowserWebDriverContainer()
					.withCapabilities(new FirefoxOptions().addArguments("--headless",
																		"--disable-gpu",
																		"--window-size=1920,1200",
																		"--ignore-certificate-errors"))
					.withNetwork(Network.SHARED);

	public static RemoteWebDriver driver;

	public String baseUrl;
	public static String ip = "";

	private String firstName = "Matt";
	private String lastName = "Murdock";
	private String username = "avocodosAtLaw";
	private String password = "hellskitchen";

	@BeforeAll
	public static void beforeAll() {
		// init our remote web driver
		// in order to find host ip address where our app server is running
		// https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		// can't be used with testcontainers/can't figure out how to use it
		// however everything runs fine without it
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() throws SQLException {
		baseUrl = String.format("http://%s:%d", ip, port);
		org.testcontainers.Testcontainers.exposeHostPorts(port);
		firefox.addExposedPort(port);
		driver = firefox.getWebDriver();
	}

	// can't be used with testcontainers/can't figure out how to use it
	// however everything runs fine without it
//	@AfterEach
//	public void afterEach() {
//		if (this.driver != null) {
//			System.out.println("done did it");
//			driver.quit();
//		}
//	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get(baseUrl + "/login");
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
	public void SignUpTest() {
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
		homePage.logout();
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
		homePage.addCredential(false, url, username, password);
		// init result page and click link back to home page
        // driver is automatically taken to result page after adding
		// credentials, etc. so no need to use driver.get(result)
		// just feed driver into the result page
		ResultPage resultPage = new ResultPage(driver);
		resultPage.clickHomeAnchor();

		// After returning to home page, click credential tab
		// and grab text from first credential
		Credential firstCredential = homePage.getFirstCredential();

		// check that hidden td's password is encrypted
		Assertions.assertNotEquals(password, homePage.getPasswordEnc());
		// check that url and username both match what was entered
		Assertions.assertEquals(url, firstCredential.getUrl());
		Assertions.assertEquals(username, firstCredential.getUsername());

		// check that entered password and retrieved (what should be dots) don't match
		Assertions.assertNotEquals(password, firstCredential.getPassword());

		// after clicking show button retrieve credentials again
		homePage.clickShowPassword();
		Credential credPasswordRevealed = homePage.getFirstCredential();
		// password that is now showing on page should match entered
		Assertions.assertNotEquals(password, firstCredential.getPassword());

		// clicking show password again will hide it
		// check that it is hidden
		homePage.clickShowPassword();
		Credential hiddenCredential = homePage.getFirstCredential();
		Assertions.assertNotEquals(password, hiddenCredential.getPassword());

		homePage.addCredential(true, newUrl, newUsername, newPassword);
		resultPage.clickHomeAnchor();

		Credential updatedCredential = homePage.getFirstCredential();
		// check to see that hidden password's text does not match entered password
		Assertions.assertNotEquals(password, homePage.getPasswordEnc());
		Assertions.assertNotEquals(updatedCredential.getPassword(), homePage.getPasswordEnc());
		Assertions.assertEquals(newUrl, updatedCredential.getUrl());
		Assertions.assertEquals(newUsername, updatedCredential.getUsername());
		Assertions.assertNotEquals(newPassword, updatedCredential.getPassword());
		Assertions.assertEquals(newPassword, homePage.getPasswordDecrypted());

		homePage.deleteCredential();
		resultPage.clickHomeAnchor();

		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getFirstCredential());
		homePage.logout();
	}

}
