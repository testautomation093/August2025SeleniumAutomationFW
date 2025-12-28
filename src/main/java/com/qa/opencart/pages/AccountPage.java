package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage {
	
	private final By headers =By.tagName("h2"); // mutiple headers
	private final By logoutLink=By.linkText("Logout"); // Logout Link
	private final By searchIcon=By.xpath("//div[@id='search']//button");
	private final By searchBar=By.name("search"); 
	
	private WebDriver driver;
	
	private ElementUtil elUtil;

	// Public page class constructor :

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		elUtil=new ElementUtil(driver);

	}

	public List<String> getAccPageHeaders()
	{
		
		return elUtil.getElementsTextList(headers);
	}
	
	public boolean isLogOutLinkExist()
	{
		boolean flag=elUtil.isElementDisplayed(logoutLink);
		
		return flag;
	}
	
	public SearchResultsPage doSearch(String searchvalue)
	{
		System.out.println("Product to be searched is : "+searchvalue);
		WebElement el=elUtil.waitForElementPresence(searchBar, AppConstants.DEFAULT_SHORT_WAIT);
		el.clear();
		el.sendKeys(searchvalue);
		elUtil.doClick(searchIcon);
		
		return new SearchResultsPage(driver);
			
	}
	
	
}
