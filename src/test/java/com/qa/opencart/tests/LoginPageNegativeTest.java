package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest {
	
	
	@DataProvider
	public Object[][] getNegativeLoginData()
	{
		return new Object[][]
				{
			    {"","test"},
			    {"test@gmail.com",""},
			    {"test@gmail.com","test123"}     
			
				};
		
		
	}
	
	
	@Test(dataProvider = "getNegativeLoginData")
	public void negativeLoginTest(String uname, String pass) {
		
		boolean flag=loginPage.doLoginwithInvalidCreds(uname,pass);
		
		Assert.assertTrue(flag);
		
	}

}
