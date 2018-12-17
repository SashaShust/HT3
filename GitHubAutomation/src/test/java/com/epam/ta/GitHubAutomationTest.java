package com.epam.ta;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.ta.steps.Steps;
import com.epam.ta.utils.Utils;

public class GitHubAutomationTest {
	private Steps steps;
	private final String USERNAME = "testautomationuser";
	private final String PASSWORD = "Time4Death!";
	private final int REPOSITORY_NAME_POSTFIX_LENGTH = 6;

	private final String EMPTY_USERNAME = "";
	private final String EMPTY_PASSWORD = "";
	private final String TEXT_MESSAGE = "Incorrect username or password.";

	@BeforeMethod(description = "Init browser")
	public void setUp() {
		steps = new Steps();
		steps.openBrowser();

	}

	@Test
	public void oneCanCreateProject() {
		steps.loginGithub(USERNAME, PASSWORD);
		String repositoryName = steps.generateRandomRepositoryNameWithCharLength(REPOSITORY_NAME_POSTFIX_LENGTH);
		steps.createNewRepository(repositoryName, "auto-generated test repo");
		Assert.assertEquals(steps.getCurrentRepositoryName(), repositoryName);
	}

	@Test
	public void oneCanCreateNewEmptyRepository() {
		steps.loginGithub(USERNAME, PASSWORD);
		String repositoryName = Utils.getRandomString(REPOSITORY_NAME_POSTFIX_LENGTH);
		steps.createNewRepository(repositoryName, "auto-generated test repo");
		assertTrue(steps.currentRepositoryIsEmpty());
	}

	@Test
	public void oneCannotLoginGithub() {
		steps.loginGithub(EMPTY_USERNAME, EMPTY_PASSWORD);
		assertEquals(steps.getTextMessage(), TEXT_MESSAGE);
	}

	@Test
	public void oneCannotCreateUntitledRepository() throws InterruptedException {
		steps.loginGithub(USERNAME, PASSWORD);
		assertFalse(steps.createNewUntitledRepository());
	}

	@Test(description = "Login to Github")
	public void oneCanLoginGithub() {
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser() {
		steps.closeBrowser();
	}
}
