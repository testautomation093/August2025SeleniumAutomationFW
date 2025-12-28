package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CsvUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

public class RegisterPageTest extends BaseTest {
	

	@BeforeClass
	public void goToRegisterPage()
	{
		registerPage=loginPage.navigateToRegisterPage();
		
		
	}
	
	@DataProvider
	public Object[][] getUserDataFromSheet()
	{
		Object obj [][]=ExcelUtil.getTestData("registration");
		
		return obj;
	}
	
	@DataProvider
	public Object[][] getUserDataFromCSVFile()
	{
		Object obj [][]=CsvUtil.csvData("registration");
		
		return obj;
	}
	
	
	
	@Test(dataProvider = "getUserDataFromCSVFile")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password, String subscribe)
	{
		boolean flag=registerPage.userRegistration(firstName,lastName,StringUtil.generateEmailId(),telephone, password,subscribe);
		
		Assert.assertTrue(flag);
	}
	
	

}
