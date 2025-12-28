package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetUp()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));		
	}
	
	@DataProvider
	public Object[][] getProductsData()
	{
		
		Object obj[][]=new Object[3][2];
		obj[0][0]="macbook";
		obj[0][1]="MacBook Pro";
		
		obj[1][0]="samsung";
		obj[1][1]="Samsung SyncMaster 941BW";
		
		obj[2][0]="canon";
		obj[2][1]="Canon EOS 5D";
		
		return obj;
	
	}
	

	@Test(dataProvider = "getProductsData")
	public void productHeaderTest(String searchValue, String productName)
	{
		
		searchResultsPage=accPage.doSearch(searchValue);
		productInfoPage=searchResultsPage.selectProduct(productName);
		String actualProductHeader=productInfoPage.getProductHeader();
		
		Assert.assertEquals(actualProductHeader, productName);
		
	}
	
	
	@DataProvider
	public Object[][] getProductsImages()
	{
		return new Object[][]
		                    {
			                {"macbook","MacBook Pro",4},
		                    {"samsung","Samsung SyncMaster 941BW",1},
		                     {"canon","Canon EOS 5D",3}
		                     
		                    };

		
	
	}
	
	@Test(dataProvider = "getProductsImages")
	public void productImagesCountTest(String searchValue, String productName, int imageCount )
	{
		searchResultsPage=accPage.doSearch(searchValue);
		productInfoPage=searchResultsPage.selectProduct(productName);
		
		int actProductImages=productInfoPage.getProductImagesCount();
		
		Assert.assertEquals(actProductImages, imageCount);
	}
	
	
	@Test
	public void productInfoTest()
	{
		SoftAssert so=new SoftAssert();
		
		searchResultsPage=accPage.doSearch("macbook");
		
		productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> allProductInfo = productInfoPage.getAllProductInfo();
		
		
		so.assertEquals(allProductInfo.get("Brand"),"Apple1");
		so.assertEquals(allProductInfo.get("Availability"),"Out Of Stock");
		so.assertEquals(allProductInfo.get("Product Price"),"$2,000.00");
		so.assertEquals(allProductInfo.get("Product Code"),"Product 18");
		so.assertEquals(allProductInfo.get("Reward Points"),"800");
		
		so.assertAll();
		
	}
	
	
}
