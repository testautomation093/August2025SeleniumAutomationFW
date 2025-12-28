package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

// POM : Page Object Model :

	// Every page will have three things :

	// 1. private By locators;
	// 2. public page class constructor
	// 3. page class action methods :

// private By locators :

	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.xpath("(//a[text()='Forgotten Password'])[1]");
	private final By header = By.tagName("h2");

	private final By register = By.xpath("(//a[text()='Register'])[2]");
	
	private final By warningErrorMessage=By.xpath("//div[@class='alert alert-danger alert-dismissible']");

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	private WebDriver driver;

	private ElementUtil elUtil;

	// Public page class constructor :

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elUtil = new ElementUtil(driver);

	}

	// Public methods :

	public String getLoginPageTitle() {

		String title = elUtil.waitforexactTitle(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("Login Page title is: " + title);
		log.info("Login Page title is: " + title);

		return title;

	}

	@Step("This is the Login Page URL Method")
	public String getLoginPageUrl() {

		String currentUrl = elUtil.waitforUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL,
				AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("Login Page Current Url is: " + currentUrl);

		log.info("Login Page Current Url is: " + currentUrl);
		return currentUrl;

	}

	public boolean isForgotPwdLinkExist() {
		boolean flag = elUtil.isElementDisplayed(forgotPwdLink);
		return flag;
	}

	public boolean isHeaderExist() {

		// System.out.println("Header of the Login Page is :"
		// +driver.findElement(header).getText());

		log.info("Header of the Login Page is :" + driver.findElement(header).getText());
		boolean flag = elUtil.isElementDisplayed(header);
		return flag;

	}

	public AccountPage doLogin(String uname, String pass) {
		// System.out.println("App Credentials are : "+uname + " :: " +pass);
		
		log.info("App Credentials are : " + uname + " :: " + pass);
		elUtil.waitForElementVisibility(email, AppConstants.DEFAULT_SHORT_WAIT);
		elUtil.doSendKeys(email, uname);
		elUtil.doSendKeys(password, pass);
		elUtil.doClick(loginBtn);

		elUtil.waitforexactTitle("My Account", AppConstants.DEFAULT_SHORT_WAIT);

		return new AccountPage(driver);

	}
	
	public boolean doLoginwithInvalidCreds(String inUname, String inPass)
	{
		
		log.info("Invalid App Credentials are : "+inUname + " :" +inPass);
		
		WebElement el=elUtil.waitForElementPresence(email, AppConstants.DEFAULT_SHORT_WAIT);
		el.clear();
		el.sendKeys(inUname);
		WebElement el2=elUtil.waitForElementVisibility(password, AppConstants.DEFAULT_SHORT_WAIT);
		el2.clear();
		elUtil.doSendKeys(password, inPass);
		elUtil.waitForElementClickable(loginBtn, AppConstants.DEFAULT_SHORT_WAIT);
		
	
		String error=
				elUtil
				  .waitForElementPresence(warningErrorMessage, AppConstants.DEFAULT_SHORT_WAIT).getText();
		
		log.error("Login Error Message is : "+error);
		
		if(error.contains(AppConstants.LOGIN_MAX_ATTEMPT_MESSG))
		{
			return true;
		}
		else if(error.contains(AppConstants.LOGIN_INVALID_ERROR_MESSG))
		{
			return true;
		}
		return false;
	}

	public RegisterPage navigateToRegisterPage() {
		elUtil.waitForElementPresence(register, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);

	}

}
