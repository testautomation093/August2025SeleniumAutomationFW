package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private final By searchResults = By.cssSelector("div.product-thumb");
	private final By resulltsHeader = By.tagName("h1");

	private WebDriver driver;

	private ElementUtil elUtil;

	// Public page class constructor :

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		elUtil = new ElementUtil(driver);

	}

	public int getSearchResultsCount() {
		int count = elUtil.waitforElementsPresence(searchResults, AppConstants.DEFAULT_SHORT_WAIT).size();

		System.out.println("Total searched products are : " + count);

		return count;

	}
	
	public String getResultsHeaderValue()
	{
		String headerValue=elUtil.doElementGetText(resulltsHeader);
		
		System.out.println("Search Results header is : "+headerValue);
		return headerValue;
		
	}
	
	public ProductInfoPage selectProduct(String productName)
	{
		System.out.println("Product to be selected is : "+productName);
		
		elUtil.doClick(By.linkText(productName));
		
		return new ProductInfoPage(driver);
		
		
	}

}
