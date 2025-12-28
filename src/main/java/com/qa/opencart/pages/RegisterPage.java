package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private final By firstName = By.id("input-firstname");
	private final By lastName = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telephone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmpassword = By.id("input-confirm");

	private final By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private final By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");

	private final By agreeCheckBox = By.name("agree");
	private final By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private final By successMessg = By.cssSelector("div#content h1");
	
	private final By logoutLink = By.linkText("Logout");
	private final By registerLink = By.linkText("Register");

	private WebDriver driver;

	private ElementUtil elUtil;

	// Public page class constructor :

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elUtil = new ElementUtil(driver);

	}

	public boolean userRegistration(String fName, String lName, String emailId, String phone, String pass,
			String subscribe) {

		elUtil.waitForElementVisibility(firstName, AppConstants.DEFAULT_SHORT_WAIT).sendKeys(fName);
		elUtil.doSendKeys(lastName, lName);

		elUtil.doSendKeys(email, emailId);
		elUtil.doSendKeys(telephone, phone);
		elUtil.doSendKeys(password, pass);
		elUtil.doSendKeys(confirmpassword, pass);

		if (subscribe.equalsIgnoreCase("yes")) {
			elUtil.doClick(subscribeYes);
		} else {
			elUtil.doClick(subscribeNo);
		}

		elUtil.doClick(agreeCheckBox);
		elUtil.doClick(continueButton);

		String succMessag = elUtil.waitForElementPresence(successMessg, AppConstants.DEFAULT_SHORT_WAIT).getText();

		System.out.println("Success message is : " + succMessag);
		
		elUtil.doClick(logoutLink);
		elUtil.doClick(registerLink);

		if (succMessag.equals(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			return true;
		} else {
			return false;
		}

	}

}