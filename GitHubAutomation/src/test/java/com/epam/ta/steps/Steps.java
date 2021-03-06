package com.epam.ta.steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.CreateNewRepositoryPage;
import com.epam.ta.pages.LoginPage;
import com.epam.ta.pages.MainPage;
import com.epam.ta.utils.Utils;

public class Steps {
	private WebDriver driver;

	private final Logger logger = LogManager.getRootLogger();

	public void openBrowser() {
		driver = DriverSingleton.getDriver();
	}

	public void closeBrowser() {
		DriverSingleton.closeDriver();
	}

	public void loginGithub(String username, String password) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage();
		loginPage.login(username, password);
	}

	public String getLoggedInUserName() {
		LoginPage loginPage = new LoginPage(driver);
		return loginPage.getLoggedInUserName().trim().toLowerCase();
	}

	public void createNewRepository(String repositoryName, String repositoryDescription) {
		MainPage mainPage = new MainPage(driver);
		mainPage.clickOnCreateNewRepositoryButton();
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription);
	}

	public boolean currentRepositoryIsEmpty() {
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		return createNewRepositoryPage.isCurrentRepositoryEmpty();
	}

	public String getCurrentRepositoryName() {
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		return createNewRepositoryPage.getCurrentRepositoryName();
	}

	public String generateRandomRepositoryNameWithCharLength(int howManyChars) {
		String repositoryNamePrefix = "testRepo_";
		return repositoryNamePrefix.concat(Utils.getRandomString(howManyChars));
	}

	public String getTextMessage() {
		LoginPage loginPage = new LoginPage(driver);
		return loginPage.getTextMessage().trim();
	}

	public boolean createNewUntitledRepository() throws InterruptedException {
		MainPage mainPage = new MainPage(driver);
		mainPage.clickOnCreateNewRepositoryButton();
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		createNewRepositoryPage.getinputRepositoryName().clear();
		Thread.sleep(2000);
		return createNewRepositoryPage.getButttonCreate().isEnabled();
	}
}
